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

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.plugins.tanaguru.checks.CheckMessagesVerifierRule;
import org.sonar.plugins.tanaguru.checks.TestHelper;
import org.sonar.plugins.web.visitor.WebSourceCode;

public class Rgaa301011CheckTest {

  @Rule
  public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

  @Test
  public void detected() throws Exception {
    WebSourceCode sourceCode = TestHelper.scan(new File(
            "src/test/resources/checks/rgaa3/Rgaa301011Check/Rgaa301011Check_Failed_01.html"),
            new Rgaa301011Check());

    checkMessagesVerifier.verify(sourceCode.getViolations()).next()
            .atLine(1)
            .withMessage(Rgaa301011Check.ADD_ALT_MSG)
            .next().atLine(4)
            .withMessage(Rgaa301011Check.ADD_ALT_MSG);
  }
}
