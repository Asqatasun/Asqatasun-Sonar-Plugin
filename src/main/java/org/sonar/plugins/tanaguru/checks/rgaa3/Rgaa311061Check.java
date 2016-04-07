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
package org.sonar.plugins.tanaguru.checks.rgaa3;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.tanaguru.checks.TanaguruRuleTags;
import org.sonar.plugins.web.checks.AbstractPageCheck;
import org.sonar.plugins.web.checks.WebRule;
import org.sonar.plugins.web.node.TagNode;

@Rule(
  key = "Rgaa311061Check",
  priority = Priority.MAJOR)
@WebRule(activeByDefault = true)
@TanaguruRuleTags({
  TanaguruRuleTags.ACCESSIBILITY, TanaguruRuleTags.RGAA3
})
public class Rgaa311061Check extends AbstractPageCheck {

  public static String ADD_LEGEND_TO_FIELDSET_MSG = "\"Ajouter une balise <legend> sur cette balise <fieldset>";
//  public static String ADD_LEGEND_TO_FIELDSET_MSG = "Add a <legend> tag to this fieldset.";
  
  private int fieldsetLine = 0;
  private boolean foundLegend;

  @Override
  public void startElement(TagNode node) {
    if (isFieldSet(node)) {
      foundLegend = false;
      fieldsetLine = node.getStartLinePosition();
    } else if (isLegend(node)) {
      foundLegend = true;
    }
  }

  @Override
  public void endElement(TagNode node) {
    if (isFieldSet(node)) {
      if (!foundLegend && fieldsetLine != 0) {
        createViolation(fieldsetLine, ADD_LEGEND_TO_FIELDSET_MSG);
      }

      foundLegend = false;
      fieldsetLine = 0;
    }
  }

  private static boolean isFieldSet(TagNode node) {
    return "FIELDSET".equalsIgnoreCase(node.getNodeName());
  }

  private static boolean isLegend(TagNode node) {
    return "LEGEND".equalsIgnoreCase(node.getNodeName());
  }

}
