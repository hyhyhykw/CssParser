/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */

package com.osbcp.cssparser;

import com.osbcp.cssparser.IncorrectFormatException.ErrorCode;

import org.junit.Assert;
import org.junit.Test;

public final class CSSParserFailingTests {

	@Test
	public void testCommaFirstAsSelector() throws Exception {

		try {
			CSSParser.parse("alpha { width: 100px; } \n , beta { height: 200px; } ");
			Assert.fail();
		} catch (IncorrectFormatException e) {
			Assert.assertEquals(ErrorCode.FOUND_COLON_WHEN_READING_SELECTOR_NAME, e.getErrorCode());
			Assert.assertTrue(e.getMessage().endsWith("Line number 2."));
		}

	}

	@Test
	public void testValueShouldEndWithSemiColon() throws Exception {
			Assert.assertEquals("[width: 100px]",CSSParser.parse("alpha { width: 100px }").get(0).getPropertyValues().toString());
	}

	@Test
	public void testMissingColon() throws Exception {

		try {
			CSSParser.parse("\n\n\n\n\n alpha { color red; }");
			Assert.fail();
		} catch (IncorrectFormatException e) {
			Assert.assertEquals(ErrorCode.FOUND_SEMICOLON_WHEN_READING_PROPERTY_NAME, e.getErrorCode());
			Assert.assertTrue(e.getMessage().endsWith("Line number 6."));
		}

	}


	@Test
	public void testMissingSemiColon() throws Exception {
		Assert.assertEquals(
				"[border: 1px solid green background-color:white, left: 0px]",
				CSSParser
						.parse("alpha { border: 1px solid green background-color:white; left: 0px; }")
						.get(0).getPropertyValues().toString());
	}

	@Test
	public void testFilterWithSemicolon() throws Exception {
		Assert.assertEquals(
				"[filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1), transform: rotate(90deg)]",
				CSSParser
						.parse(".fa-rotate-90 { filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1); transform: rotate(90deg);	}")
						.get(0).getPropertyValues().toString());
	}
}
