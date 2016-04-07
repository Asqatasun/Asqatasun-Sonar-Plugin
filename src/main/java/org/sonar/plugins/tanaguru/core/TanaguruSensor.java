/*
 * SonarQube Asqatasun Plugin
 * Copyright (C) 2016 Asqatasun.org
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.tanaguru.core;

import com.google.common.collect.ImmutableList;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.checks.AnnotationCheckFactory;
import org.sonar.api.checks.NoSonarFilter;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.PersistenceMode;
import org.sonar.api.measures.RangeDistributionBuilder;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.File;
import org.sonar.api.resources.Project;
import org.sonar.api.rules.Violation;
import org.sonar.api.scan.filesystem.FileQuery;
import org.sonar.api.scan.filesystem.ModuleFileSystem;
import org.sonar.plugins.web.analyzers.ComplexityVisitor;
import org.sonar.plugins.web.analyzers.PageCountLines;
import org.sonar.plugins.tanaguru.api.TanaguruConstants;
import org.sonar.plugins.tanaguru.rules.TanaguruCheckClasses;
import org.sonar.plugins.tanaguru.rules.TanaguruRulesRepository;
import org.sonar.plugins.web.checks.AbstractPageCheck;
import org.sonar.plugins.web.lex.PageLexer;
import org.sonar.plugins.web.node.Node;
import org.sonar.plugins.web.visitor.HtmlAstScanner;
import org.sonar.plugins.web.visitor.NoSonarScanner;
import org.sonar.plugins.web.visitor.WebSourceCode;

public final class TanaguruSensor implements Sensor {
  
private static final Number[] FILES_DISTRIB_BOTTOM_LIMITS = {0, 5, 10, 20, 30, 60, 90};
  private static final Logger LOG = LoggerFactory.getLogger(TanaguruSensor.class);

  private final Tanaguru tanaguru;
  private final NoSonarFilter noSonarFilter;
  private final AnnotationCheckFactory annotationCheckFactory;
  private final ModuleFileSystem fileSystem;
  private final FileLinesContextFactory fileLinesContextFactory;

  public TanaguruSensor(
          Tanaguru tanaguru, 
          RulesProfile profile, 
          NoSonarFilter noSonarFilter, 
          ModuleFileSystem fileSystem, 
          FileLinesContextFactory fileLinesContextFactory) {
    this.tanaguru = tanaguru;
    this.noSonarFilter = noSonarFilter;
    this.annotationCheckFactory = AnnotationCheckFactory.create(profile, TanaguruRulesRepository.REPOSITORY_KEY, TanaguruCheckClasses.getCheckClasses());
    this.fileSystem = fileSystem;
    this.fileLinesContextFactory = fileLinesContextFactory;

  }

  @Override
  public void analyse(Project project, SensorContext sensorContext) {
    // configure the lexer
    final PageLexer lexer = new PageLexer();

    // configure page scanner and the visitors
    final HtmlAstScanner scanner = setupScanner();

    for (java.io.File file : fileSystem.files(FileQuery.onSource().onLanguage(TanaguruConstants.LANGUAGE_KEY))) {
      File resource = File.fromIOFile(file, project);
      WebSourceCode sourceCode = new WebSourceCode(file, resource);
      FileReader reader = null;
      try {
        reader = new FileReader(file);
        List<Node> nodeList = lexer.parse(reader);
        scanner.scan(nodeList, sourceCode, fileSystem.sourceCharset());
        saveMetrics(sensorContext, sourceCode);
        saveLineLevelMeasures(resource, sourceCode);
      } catch (Exception e) {
        LOG.error("Can not analyze file " + file.getAbsolutePath(), e);
      } finally {
        IOUtils.closeQuietly(reader);
      }
    }
  }

  private void saveMetrics(SensorContext sensorContext, WebSourceCode sourceCode) {
    saveComplexityDistribution(sensorContext, sourceCode);
    List<Measure> measures = sourceCode.getMeasures();
    for (Measure measure : measures) {
      sensorContext.saveMeasure(sourceCode.getResource(), measure);
    }

    List<Violation> violations = sourceCode.getViolations();
    for (Violation violation : violations) {
      sensorContext.saveViolation(violation);
    }
  }

  private void saveComplexityDistribution(SensorContext sensorContext, WebSourceCode sourceCode) {
    if (sourceCode.getMeasure(CoreMetrics.COMPLEXITY) != null) {
      RangeDistributionBuilder complexityFileDistribution = new RangeDistributionBuilder(CoreMetrics.FILE_COMPLEXITY_DISTRIBUTION,
        FILES_DISTRIB_BOTTOM_LIMITS);
      complexityFileDistribution.add(sourceCode.getMeasure(CoreMetrics.COMPLEXITY).getValue());
      sensorContext.saveMeasure(sourceCode.getResource(), complexityFileDistribution.build().setPersistenceMode(PersistenceMode.MEMORY));
    }
  }

  private void saveLineLevelMeasures(File sonarFile, WebSourceCode webSourceCode) {
    FileLinesContext fileLinesContext = fileLinesContextFactory.createFor(sonarFile);
    Set<Integer> linesOfCode = webSourceCode.getDetailedLinesOfCode();
    Set<Integer> linesOfComments = webSourceCode.getDetailedLinesOfComments();

    for (int line = 1; line <= webSourceCode.getMeasure(CoreMetrics.LINES).getIntValue(); line++) {
      fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, linesOfCode.contains(line) ? 1 : 0);
      fileLinesContext.setIntValue(CoreMetrics.COMMENT_LINES_DATA_KEY, line, linesOfComments.contains(line) ? 1 : 0);
    }
    fileLinesContext.save();
  }

  /**
   * Create PageScanner with Visitors.
   */
  private HtmlAstScanner setupScanner() {
    HtmlAstScanner scanner = new HtmlAstScanner(ImmutableList.of(new PageCountLines(), new ComplexityVisitor(), new NoSonarScanner(noSonarFilter)));
    for (AbstractPageCheck check : (Collection<AbstractPageCheck>) annotationCheckFactory.getChecks()) {
      scanner.addVisitor(check);
      check.setRule(annotationCheckFactory.getActiveRule(check).getRule());
    }
    return scanner;
  }

  /**
   * This sensor only executes on Web projects.
   * @param project
   */
  @Override
  public boolean shouldExecuteOnProject(Project project) {
    return TanaguruConstants.LANGUAGE_KEY.equals(project.getLanguageKey()) ||
      (StringUtils.isBlank(project.getLanguageKey()) &&
      !fileSystem.files(FileQuery.onSource().onLanguage(TanaguruConstants.LANGUAGE_KEY)).isEmpty());
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
  

}
