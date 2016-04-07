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

import java.util.Arrays;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.AnnotationUtils;
import org.sonar.plugins.tanaguru.checks.TanaguruRuleTags;

/**
 * Rgaa3 profile for the Accessibility language
 */
public final class Rgaa3Profile extends BaseProfileDefinition {

  public Rgaa3Profile(RuleFinder ruleFinder) {
    super(ruleFinder);
  }

  @Override
  protected String getProfileName() {
    return TanaguruRuleTags.RGAA3;
  }

  @Override
  protected boolean isTagIncluded(Class ruleClass) {
    TanaguruRuleTags ruleAnnotation = AnnotationUtils.getAnnotation(ruleClass, TanaguruRuleTags.class);
    return Arrays.asList(ruleAnnotation.value()).contains(TanaguruRuleTags.RGAA3);
  }

}