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


import org.sonar.api.batch.AbstractSourceImporter;
import org.sonar.api.batch.Phase;

/**
 * Import of source files to sonar database.
 */
@Phase(name = Phase.Name.PRE)
public final class TanaguruSourceImporter extends AbstractSourceImporter {

  /**
   * Instantiates a new tanaguru source importer.
   * @param tanaguru
   */
  public TanaguruSourceImporter(Tanaguru tanaguru) {
    super(tanaguru);
  }

  /**
   * {@inheritDoc}
   * @return 
   */
  @Override
  public String toString() {
    return "Tanaguru Source Importer";
  }

}
