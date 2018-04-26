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
package org.sonar.plugins.asqatasun.checks;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import org.sonar.plugins.web.analyzers.ComplexityVisitor;
import org.sonar.plugins.web.analyzers.PageCountLines;
import org.sonar.plugins.web.lex.PageLexer;
import org.sonar.plugins.web.node.Node;
import org.sonar.plugins.web.visitor.DefaultNodeVisitor;
import org.sonar.plugins.web.visitor.HtmlAstScanner;
import org.sonar.plugins.web.visitor.WebSourceCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class TestHelper {

  private TestHelper() {
  }

  public static WebSourceCode scan(File file, DefaultNodeVisitor visitor) {
    FileReader fileReader;
    try {
      fileReader = new FileReader(file);
    } catch (FileNotFoundException e) {
      throw Throwables.propagate(e);
    }
    PageLexer lexer = new PageLexer();
    List<Node> nodes = lexer.parse(fileReader);
    WebSourceCode result = new WebSourceCode(file, new org.sonar.api.resources.File("test"));

    HtmlAstScanner walker = new HtmlAstScanner(ImmutableList.of(new PageCountLines(), new ComplexityVisitor()));
    walker.addVisitor(visitor);
    walker.scan(nodes, result, Charsets.UTF_8);

    return result;
  }

}
