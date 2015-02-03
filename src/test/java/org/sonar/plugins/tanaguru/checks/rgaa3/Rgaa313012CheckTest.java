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


import org.junit.Rule;
import org.junit.Test;
import org.sonar.plugins.tanaguru.checks.CheckMessagesVerifierRule;
import org.sonar.plugins.tanaguru.checks.TestHelper;
import org.sonar.plugins.web.visitor.WebSourceCode;

import java.io.File;

public class Rgaa313012CheckTest {

  @Rule
  public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

  @Test
  public void detected() throws Exception {
    WebSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/rgaa3/Rgaa313012Check/Rgaa313012Check.html"), new Rgaa313012Check());

    checkMessagesVerifier.verify(sourceCode.getViolations())
            .next()
            .atLine(2)
            .withMessage(Rgaa313012Check.CHANGE_NOT_IMMEDIATE_REDIRECT)
            .noMore();
  }

}
