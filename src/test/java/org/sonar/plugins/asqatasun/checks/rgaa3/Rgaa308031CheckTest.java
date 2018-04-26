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

import org.junit.Ignore;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.plugins.asqatasun.checks.CheckMessagesVerifierRule;
import org.sonar.plugins.asqatasun.checks.TestHelper;
import org.sonar.plugins.web.visitor.WebSourceCode;
import java.io.File;

public class Rgaa308031CheckTest {

  @Rule
  public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

  @Test
  public void doctype_before_html() throws Exception {
    WebSourceCode sourceCode = TestHelper
            .scan(new File(
                            "src/test/resources/checks/rgaa3/Rgaa308031Check/DoctypeBeforeHtml.html"),
                    new Rgaa308031Check());

    checkMessagesVerifier.verify(sourceCode.getViolations()).noMore();
  }

  @Test
  public void full_doctype_before_html() throws Exception {
    WebSourceCode sourceCode = TestHelper
            .scan(new File(
                            "src/test/resources/checks/rgaa3/Rgaa308031Check/FullDoctypeBeforeHtml.html"),
                    new Rgaa308031Check());

    checkMessagesVerifier.verify(sourceCode.getViolations()).noMore();
  }

  @Test
  public void no_doctype_before_foo() throws Exception {
    WebSourceCode sourceCode = TestHelper
            .scan(new File(
                            "src/test/resources/checks/rgaa3/Rgaa308031Check/NoDoctypeBeforeFoo.html"),
                    new Rgaa308031Check());

    checkMessagesVerifier.verify(sourceCode.getViolations()).noMore();
  }

  @Test
  public void no_doctype_before_html() throws Exception {
    WebSourceCode sourceCode = TestHelper
            .scan(new File(
                            "src/test/resources/checks/rgaa3/Rgaa308031Check/NoDoctypeBeforeHtml.html"),
                    new Rgaa308031Check());

    checkMessagesVerifier.verify(sourceCode.getViolations()).noMore();
  }

  @Ignore
  public void multiple_html_tags() throws Exception {
    WebSourceCode sourceCode = TestHelper
            .scan(new File(
                            "src/test/resources/checks/rgaa3/Rgaa308031Check/MultipleHtmlTags.html"),
                    new Rgaa308031Check());

    checkMessagesVerifier.verify(sourceCode.getViolations()).next()
            .atLine(1)
            .withMessage(Rgaa308031Check.WRONG_DOCTYPE_POSITION)
            .noMore();
  }

  @Test
  public void doctype_after_html() throws Exception {
    WebSourceCode sourceCode = TestHelper
            .scan(new File(
                            "src/test/resources/checks/rgaa3/Rgaa308031Check/DoctypeAfterHtml.html"),
                    new Rgaa308031Check());

    checkMessagesVerifier.verify(sourceCode.getViolations()).next()
            .atLine(1)
            .withMessage(Rgaa308031Check.WRONG_DOCTYPE_POSITION)
            .noMore();
  }

  @Test
  public void no_doctype_after_html() throws Exception {
    WebSourceCode sourceCode = TestHelper
            .scan(new File(
                            "src/test/resources/checks/rgaa3/Rgaa308031Check/DoctypeAfterHtml.html"),
                    new Rgaa308031Check());

    checkMessagesVerifier.verify(sourceCode.getViolations()).next()
            .atLine(1)
            .withMessage(Rgaa308031Check.WRONG_DOCTYPE_POSITION)
            .noMore();
  }

}
