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
package org.sonar.plugins.asqatasun.rules;

import java.util.Arrays;
import java.util.List;
import org.sonar.plugins.asqatasun.checks.rgaa3.Rgaa301011Check;
import org.sonar.plugins.asqatasun.checks.rgaa3.Rgaa308031Check;
import org.sonar.plugins.asqatasun.checks.rgaa3.Rgaa311061Check;
import org.sonar.plugins.asqatasun.checks.rgaa3.Rgaa313012Check;

public final class AsqatasunCheckClasses {

  @SuppressWarnings("rawtypes")
  private static final Class[] CLASSES = new Class[] {
    //RGAA3
    Rgaa301011Check.class,
    Rgaa308031Check.class,
    Rgaa311061Check.class,
    Rgaa313012Check.class,
  };

  private AsqatasunCheckClasses() {
  }

  /**
   * Gets the list of XML checks.
   * @return 
   */
  @SuppressWarnings("rawtypes")
  public static List<Class> getCheckClasses() {
    return Arrays.asList(CLASSES);
  }

}
