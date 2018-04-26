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
package org.sonar.plugins.asqatasun.checks.rgaa3;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.asqatasun.checks.AsqatasunRuleTags;
import org.sonar.plugins.web.checks.AbstractPageCheck;
import org.sonar.plugins.web.checks.WebRule;
import org.sonar.plugins.web.node.TagNode;

@Rule(key = "Rgaa301011Check", priority = Priority.MAJOR)
@WebRule(activeByDefault = true)
@AsqatasunRuleTags({AsqatasunRuleTags.ACCESSIBILITY, AsqatasunRuleTags.RGAA3})
public class Rgaa301011Check extends AbstractPageCheck {

  public static String ADD_ALT_MSG = "Ajouter un attribut \"alt\" à cette image.";

  @Override
  public void startElement(TagNode node) {
    if (isImgTag(node) && !hasAltAttribute(node)) {
      createViolation(node.getStartLinePosition(),ADD_ALT_MSG);
    }
  }

  private static boolean isImgTag(TagNode node) {
    return "IMG".equalsIgnoreCase(node.getNodeName());
  }

  private static boolean hasAltAttribute(TagNode node) {
    return node.getAttribute("ALT") != null;
  }

}