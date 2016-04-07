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
package org.sonar.plugins.tanaguru.rules;

import com.google.common.collect.Lists;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;
import org.sonar.api.utils.AnnotationUtils;
import java.util.List;
import org.sonar.plugins.tanaguru.api.TanaguruConstants;
import org.sonar.plugins.tanaguru.checks.TanaguruRuleTags;
import org.sonar.plugins.web.checks.WebRule;

public final class TanaguruRulesRepository extends RuleRepository {

  public static final String REPOSITORY_KEY = TanaguruConstants.LANGUAGE_NAME;

  public TanaguruRulesRepository() {
    super(REPOSITORY_KEY, TanaguruConstants.LANGUAGE_KEY);
    setName("SonarQube");
  }

  @Override
  public List<Rule> createRules() {
    List<Rule> result = Lists.newArrayList();
    for (Class ruleClass : TanaguruCheckClasses.getCheckClasses()) {
      if (AnnotationUtils.getAnnotation(ruleClass, WebRule.class) != null) {
        result.add(RuleRepositoryHelper.createRule(
          REPOSITORY_KEY,
          ruleClass,
          AnnotationUtils.getAnnotation(ruleClass, org.sonar.check.Rule.class),
          AnnotationUtils.getAnnotation(ruleClass, TanaguruRuleTags.class)
        ));
      }
    }
    return result;
  }

}
