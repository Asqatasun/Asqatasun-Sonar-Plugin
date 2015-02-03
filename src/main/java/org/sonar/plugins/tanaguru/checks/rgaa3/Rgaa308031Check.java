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
import org.sonar.plugins.web.checks.AbstractPageCheck;
import org.sonar.plugins.web.node.DirectiveNode;
import org.sonar.plugins.web.node.Node;
import org.sonar.plugins.web.node.NodeType;
import org.sonar.plugins.web.node.TagNode;

import java.util.List;
import org.sonar.plugins.tanaguru.checks.TanaguruRuleTags;
import org.sonar.plugins.web.checks.WebRule;

@Rule(key = "Rgaa308031Check", priority = Priority.MAJOR)
@WebRule(activeByDefault = true)
@TanaguruRuleTags({
  TanaguruRuleTags.ACCESSIBILITY, TanaguruRuleTags.RGAA3
})
public class Rgaa308031Check extends AbstractPageCheck {

  public static String WRONG_DOCTYPE_POSITION = "Insérer une déclaration de type <!DOCTYPE> avant la balise html";
//  public static String WRONG_DOCTYPE_POSITION = "Insert a <!DOCTYPE> declaration before html tag.";
  private boolean foundDoctype;
  private boolean reported;

  @Override
  public void startDocument(List<Node> nodes) {
    foundDoctype = true;
    reported = false;
    for (Node node : nodes) {
      if (node.getNodeType().equals(NodeType.DIRECTIVE)) {
        foundDoctype = false;
      }
    }
  }

  @Override
  public void directive(DirectiveNode node) {
    if (isDoctype(node)) {
      foundDoctype = true;
    }
  }

  private static boolean isDoctype(DirectiveNode node) {
    return "DOCTYPE".equalsIgnoreCase(node.getNodeName());
  }

  @Override
  public void startElement(TagNode node) {
    if (isHtml(node) && !foundDoctype && !reported) {
      createViolation(node.getStartLinePosition(), WRONG_DOCTYPE_POSITION);
      reported = true;
    }
  }

  private static boolean isHtml(TagNode node) {
    return "HTML".equalsIgnoreCase(node.getNodeName());
  }

}
