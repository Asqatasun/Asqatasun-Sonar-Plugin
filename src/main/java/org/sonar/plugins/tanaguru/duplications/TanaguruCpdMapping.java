/*
 * SonarQube Tanaguru Plugin
 * Copyright (C) 2015 Tanaguru.org
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
package org.sonar.plugins.tanaguru.duplications;

import net.sourceforge.pmd.cpd.Tokenizer;
import org.sonar.api.batch.CpdMapping;
import org.sonar.api.resources.File;
import org.sonar.api.resources.Language;
import org.sonar.api.resources.Resource;

import java.util.List;
import org.sonar.plugins.tanaguru.core.Tanaguru;
import org.sonar.plugins.web.duplications.WebCpdTokenizer;

public class TanaguruCpdMapping implements CpdMapping {

  private final Tanaguru tanaguru;

  public TanaguruCpdMapping(Tanaguru tanaguru) {
    this.tanaguru = tanaguru;
  }

  @Override
  public Tokenizer getTokenizer() {
    return new WebCpdTokenizer();
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Resource createResource(java.io.File file, List<java.io.File> sourceDirs) {
    return File.fromIOFile(file, sourceDirs);
  }

  @Override
  public Language getLanguage() {
    return tanaguru;
  }

}
