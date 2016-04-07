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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.PropertyType;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleParam;
import org.sonar.api.rules.RulePriority;
import org.sonar.api.utils.SonarException;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.sonar.plugins.tanaguru.checks.TanaguruRuleTags;

public final class RuleRepositoryHelper {

  private static final Method METHOD_SET_RULE_TAGS;

  private RuleRepositoryHelper() {
  }

  static {
    Method method;
    try {
      method = Rule.class.getMethod("setTags", String[].class);
    } catch (NoSuchMethodException e) {
      // Compatibility with SQ version lower than 4.2
      method = null;
    }
    METHOD_SET_RULE_TAGS = method;
  }

  public static Rule createRule(String repositoryKey, Class clazz, org.sonar.check.Rule ruleAnnotation, @Nullable TanaguruRuleTags ruleTagsAnnotation) {
    String ruleKey = StringUtils.defaultIfEmpty(ruleAnnotation.key(), clazz.getCanonicalName());
    String ruleName = StringUtils.defaultIfEmpty(ruleAnnotation.name(), null);
    String description = StringUtils.defaultIfEmpty(ruleAnnotation.description(), null);
    Rule rule = Rule.create(repositoryKey, ruleKey, ruleName);
    rule.setDescription(description);
    rule.setSeverity(RulePriority.fromCheckPriority(ruleAnnotation.priority()));
    rule.setCardinality(ruleAnnotation.cardinality());
    setTags(rule, ruleTagsAnnotation);

    Field[] fields = clazz.getDeclaredFields();
    if (fields != null) {
      for (Field field : fields) {
        addRuleProperty(rule, field);
      }
    }

    return rule;
  }

  private static void setTags(Rule rule, @Nullable TanaguruRuleTags ruleTagsAnnotation) {
    if (METHOD_SET_RULE_TAGS != null && ruleTagsAnnotation != null) {
      try {
        METHOD_SET_RULE_TAGS.invoke(rule, (Object) ruleTagsAnnotation.value());
      } catch (InvocationTargetException e) {
        throw Throwables.propagate(e);
      } catch (IllegalAccessException e) {
        throw Throwables.propagate(e);
      }
    }
  }

  private static void addRuleProperty(Rule rule, Field field) {
    org.sonar.check.RuleProperty propertyAnnotation = field.getAnnotation(org.sonar.check.RuleProperty.class);
    if (propertyAnnotation != null) {
      String fieldKey = StringUtils.defaultIfEmpty(propertyAnnotation.key(), field.getName());
      RuleParam param = rule.createParameter(fieldKey);
      param.setDescription(propertyAnnotation.description());
      param.setDefaultValue(propertyAnnotation.defaultValue());
      if (!StringUtils.isBlank(propertyAnnotation.type())) {
        try {
          param.setType(PropertyType.valueOf(propertyAnnotation.type().trim()).name());
        } catch (IllegalArgumentException e) {
          throw new SonarException("Invalid property type [" + propertyAnnotation.type() + "]", e);
        }
      } else {
        param.setType(guessType(field.getType()).name());
      }
    }
  }

  private static final Function<Class<?>, PropertyType> TYPE_FOR_CLASS = Functions.forMap(
    ImmutableMap.<Class<?>, PropertyType> builder()
      .put(Integer.class, PropertyType.INTEGER)
      .put(int.class, PropertyType.INTEGER)
      .put(Float.class, PropertyType.FLOAT)
      .put(float.class, PropertyType.FLOAT)
      .put(Boolean.class, PropertyType.BOOLEAN)
      .put(boolean.class, PropertyType.BOOLEAN)
      .build(),
    PropertyType.STRING);

  @VisibleForTesting
  static PropertyType guessType(Class<?> type) {
    return TYPE_FOR_CLASS.apply(type);
  }

}
