/*
 * Copyright (c) 2021 dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.panda_lang.panda.language.resource.expression.subparsers

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.panda_lang.framework.architecture.expression.Expression
import org.panda_lang.framework.interpreter.lexer.PandaLexerUtils
import org.panda_lang.framework.interpreter.parser.expression.ExpressionParser
import org.panda_lang.framework.interpreter.parser.expression.PandaExpressionParser
import org.panda_lang.framework.interpreter.token.Snippet
import org.panda_lang.panda.language.syntax.expressions.PandaExpressions
import org.panda_lang.panda.utils.PandaContextUtils
import org.panda_lang.panda.utils.PandaUtils
import org.panda_lang.utilities.commons.TimeUtils

@CompileStatic
final class OperationExpressionTest {

    private static final ExpressionParser PARSER = new PandaExpressionParser(PandaExpressions.createExpressionSubparsers());
    private static final Snippet SOURCE = PandaLexerUtils.convert(OperationExpressionTest.class.getSimpleName(), "1 + 2");

    @Test
    void testMathOperation() throws Exception {
        Expression expression = PARSER.parse(PandaContextUtils.createStubContext(PandaUtils.defaultInstance(), (context -> new HashMap<>())), SOURCE);
        Assertions.assertEquals((Object) 3, expression.evaluate(null, null));
    }

    @Test
    void test100M() throws Exception {
        Expression expression = PARSER.parse(PandaContextUtils.createStubContext(PandaUtils.defaultInstance(), (context -> new HashMap<>())), SOURCE);
        long time = System.nanoTime();

        for (int times = 0; times < 100_000_000; times++) {
            expression.evaluate(null, null);
        }

        time = System.nanoTime() - time;
        System.out.println("Time: " + TimeUtils.toMilliseconds(time));
    }

}