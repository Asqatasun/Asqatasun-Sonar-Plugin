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
package org.sonar.plugins.tanaguru.checks.rgaa3;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.tanaguru.checks.TanaguruRuleTags;
import org.sonar.plugins.web.checks.AbstractPageCheck;
import org.sonar.plugins.web.checks.WebRule;
import org.sonar.plugins.web.node.TagNode;

@Rule(
  key = "Rgaa313012Check",
  priority = Priority.MAJOR)
@WebRule(activeByDefault = true)
@TanaguruRuleTags({
  TanaguruRuleTags.ACCESSIBILITY,
  TanaguruRuleTags.RGAA3
})
public class Rgaa313012Check extends AbstractPageCheck {

  private static final String SEPERATOR = ";";

  @Override
  public void startElement(TagNode node) {
    if (isMetaRefreshTag(node)) {
      createViolation(node.getStartLinePosition(), "13.1.2 : Pour chaque page Web, chaque procédé de redirection effectué via une balise meta est-il immédiat (hors cas particuliers) ?");
    }
  }

  private static boolean isMetaRefreshTag(TagNode node) {
    String httpEquiv = node.getAttribute("HTTP-EQUIV");
    String content = node.getAttribute("content");

    return "META".equalsIgnoreCase(node.getNodeName()) && httpEquiv != null && "REFRESH".equalsIgnoreCase(httpEquiv) && isTall(content);

  }

  private static boolean isTall(String content) {

    if (content != null) {
      String[] tabContent = content.split(SEPERATOR);

      if (tabContent.length == 1) {
        return false;
      } else if (tabContent.length > 1 && !tabContent[0].equals("0")) {
        return true;
      }

      return false;
    }
    return false;
  }

}