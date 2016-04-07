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

import org.sonar.api.config.Settings;
import org.sonar.plugins.tanaguru.api.TanaguruConstants;

public class TanaguruConfiguration {

  private final Settings settings;

  public TanaguruConfiguration(Settings settings) {
    this.settings = settings;
  }
  
  public String[] fileSuffixes() {
    return settings.getStringArray(TanaguruConstants.FILE_EXTENSIONS_PROP_KEY);
  }

}