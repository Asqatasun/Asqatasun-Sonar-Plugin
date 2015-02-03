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
package org.sonar.plugins.tanaguru.core;

import org.sonar.api.web.CodeColorizerFormat;
import org.sonar.colorizer.MultilinesDocTokenizer;
import org.sonar.colorizer.RegexpTokenizer;
import org.sonar.colorizer.StringTokenizer;
import org.sonar.colorizer.Tokenizer;
import java.util.ArrayList;
import java.util.List;
import org.sonar.plugins.tanaguru.api.TanaguruConstants;

public class TanaguruCodeColorizerFormat extends CodeColorizerFormat {

  private final List<Tokenizer> tokenizers = new ArrayList<Tokenizer>();

  public TanaguruCodeColorizerFormat() {
    super(TanaguruConstants.LANGUAGE_KEY);
    String tagAfter = "</span>";

    // == tags ==
    tokenizers.add(new RegexpTokenizer("<span class=\"k\">", tagAfter, "</?[:\\w]+>?"));
    tokenizers.add(new RegexpTokenizer("<span class=\"k\">", tagAfter, ">"));

    // == doctype ==
    tokenizers.add(new RegexpTokenizer("<span class=\"j\">", tagAfter, "<!DOCTYPE.*>"));

    // == comments ==
    tokenizers.add(new MultilinesDocTokenizer("<!--", "-->", "<span class=\"j\">", tagAfter));
    tokenizers.add(new MultilinesDocTokenizer("<%--", "--%>", "<span class=\"j\">", tagAfter));

    // == expressions ==
    tokenizers.add(new MultilinesDocTokenizer("<%@", "%>", "<span class=\"a\">", tagAfter));
    tokenizers.add(new MultilinesDocTokenizer("<%", "%>", "<span class=\"a\">", tagAfter));

    // == tag properties ==
    tokenizers.add(new StringTokenizer("<span class=\"s\">", tagAfter));
  }

  @Override
  public List<Tokenizer> getTokenizers() {
    return tokenizers;
  }

}
