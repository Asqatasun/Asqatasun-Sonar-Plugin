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
package org.sonar.plugins.asqatasun;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;
import org.sonar.plugins.asqatasun.api.AsqatasunConstants;
import org.sonar.plugins.asqatasun.core.Asqatasun;
import org.sonar.plugins.asqatasun.core.AsqatasunCodeColorizerFormat;
import org.sonar.plugins.asqatasun.core.AsqatasunSensor;
import org.sonar.plugins.asqatasun.core.AsqatasunSourceImporter;
import org.sonar.plugins.asqatasun.duplications.AsqatasunCpdMapping;
import org.sonar.plugins.asqatasun.rules.Rgaa3Profile;
import org.sonar.plugins.asqatasun.rules.AsqatasunRulesRepository;

/**
 *
 * @author jkowalczyk
 */
@Properties({
  // Global JavaScript settings
  @Property(
          key = AsqatasunPlugin.FILE_SUFFIXES_KEY,
          defaultValue = AsqatasunPlugin.FILE_SUFFIXES_DEFVALUE,
          name = "File suffixes",
          description = "Comma-separated list of suffixes for files to analyze.",
          global = true,
          project = true),})
public final class AsqatasunPlugin extends SonarPlugin {

  private static final String CATEGORY = "Asqatasun";
  
  @Override
  public List getExtensions() {
    ImmutableList.Builder<Object> builder = ImmutableList.builder();

    // web language
    builder.add(Asqatasun.class);

//  web files importer
    builder.add(AsqatasunSourceImporter.class);

//  web rules repository
    builder.add(AsqatasunRulesRepository.class);

//  profiles
    builder.add(Rgaa3Profile.class);

//  web sensor
    builder.add(AsqatasunSensor.class);
//
//  Code Colorizer
    builder.add(AsqatasunCodeColorizerFormat.class);
//  Copy/Paste detection mechanism
    builder.add(AsqatasunCpdMapping.class);
//
    builder.addAll(pluginProperties());

    return builder.build();
  }

  // Global JavaScript constants
  public static final String FALSE = "false";
  public static final String FILE_SUFFIXES_KEY = AsqatasunConstants.FILE_EXTENSIONS_PROP_KEY;
  public static final String FILE_SUFFIXES_DEFVALUE = AsqatasunConstants.FILE_EXTENSIONS_DEF_VALUE;
  public static final String PROPERTY_PREFIX = "sonar.asqatasun";

  private static ImmutableList<PropertyDefinition> pluginProperties() {
    return ImmutableList.of(

      PropertyDefinition.builder(FILE_SUFFIXES_KEY)
        .name("File suffixes")
        .description("List of file suffixes that will be scanned.")
        .category(CATEGORY)
        .defaultValue(FILE_SUFFIXES_DEFVALUE)
        .onQualifiers(Qualifiers.PROJECT)
        .build()
    );
  }
}
