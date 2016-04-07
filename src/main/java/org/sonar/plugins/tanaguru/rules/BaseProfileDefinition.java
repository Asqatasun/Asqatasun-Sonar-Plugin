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

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleAnnotationUtils;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.AnnotationUtils;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.plugins.tanaguru.api.TanaguruConstants;
import org.sonar.plugins.web.checks.WebRule;

public abstract class BaseProfileDefinition extends ProfileDefinition {

  private final RuleFinder ruleFinder;

  public BaseProfileDefinition(RuleFinder ruleFinder) {
    this.ruleFinder = ruleFinder;
  }

  @Override
  public final RulesProfile createProfile(ValidationMessages validation) {
    RulesProfile profile = RulesProfile.create(getProfileName(), getLanguageKey());
    for (Class ruleClass : TanaguruCheckClasses.getCheckClasses()) {
      String ruleKey = RuleAnnotationUtils.getRuleKey(ruleClass);
      if (isActive(ruleClass)) {
        Rule rule = ruleFinder.findByKey(getRepositoryKey(), ruleKey);
        profile.activateRule(rule, null);
      }
    }
    return profile;
  }

  protected boolean isActive(Class ruleClass) {
    WebRule ruleAnnotation = AnnotationUtils.getAnnotation(ruleClass, WebRule.class);
    return ruleAnnotation != null && ruleAnnotation.activeByDefault() && isTagIncluded(ruleClass);
  }

  /**
   * 
   * @return 
   */
  protected String getLanguageKey() {
    return TanaguruConstants.LANGUAGE_KEY;
  }

  /**
   * 
   * @return 
   */
  protected String getRepositoryKey() {
    return TanaguruConstants.LANGUAGE_NAME;
  }
  
  protected abstract String getProfileName();
  
  protected abstract boolean isTagIncluded(Class ruleClass);

}