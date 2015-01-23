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
package org.sonar.plugins.tanaguru;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

/**
 *
 * @author jkowalczyk
 */
@Properties({
  // Global JavaScript settings
  @Property(
          key = TanaguruPlugin.FILE_SUFFIXES_KEY,
          defaultValue = TanaguruPlugin.FILE_SUFFIXES_DEFVALUE,
          name = "File suffixes",
          description = "Comma-separated list of suffixes for files to analyze.",
          global = true,
          project = true),})
public final class TanaguruPlugin extends SonarPlugin {

  @Override
  public List getExtensions() {
    return ImmutableList.of(
            );
  }
  // Global JavaScript constants
  public static final String FALSE = "false";
  public static final String FILE_SUFFIXES_KEY = "sonar.tanaguru.file.suffixes";
  public static final String FILE_SUFFIXES_DEFVALUE = ".html,.xhtml,.php,.jsp,.jsf,.html";
  public static final String PROPERTY_PREFIX = "sonar.tanaguru";

}
