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

package org.panda_lang.panda.language.syntax.expressions;

import org.panda_lang.framework.interpreter.parser.expression.ExpressionSubparser;
import org.panda_lang.framework.interpreter.parser.expression.ExpressionSubparsers;
import org.panda_lang.panda.language.syntax.expressions.subparsers.CastParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.InstanceCreationParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.CreaseParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.DeclarationParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.IsParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.LiteralParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.MethodParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.NegateParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.number.NegativeParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.number.NotBitwiseParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.number.NumberParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.operation.OperationExpressionParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.SectionParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.SequenceParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.StaticParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.VariableParser;
import org.panda_lang.panda.language.syntax.expressions.subparsers.AssignationPrarser;

import java.util.Arrays;
import java.util.Collection;

public final class PandaExpressions {

    public static Collection<ExpressionSubparser> createSubparsers() {
        return Arrays.asList(
                new AssignationPrarser(),
                new CastParser(),
                new InstanceCreationParser(),
                new CreaseParser(),
                new DeclarationParser(),
                new IsParser(),
                new LiteralParser(),
                new MethodParser(),
                new NegateParser(),
                new NegativeParser(),
                new NotBitwiseParser(),
                new NumberParser(),
                new OperationExpressionParser(),
                new SectionParser(),
                new SequenceParser(),
                new StaticParser(),
                new VariableParser()
        );
    }

    public static ExpressionSubparsers createExpressionSubparsers() {
        return new ExpressionSubparsers(createSubparsers());
    }

}
