/*
 * Copyright (c) 2015-2019 Dzikoysk
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

package org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.subparsers;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.design.architecture.value.Value;
import org.panda_lang.panda.framework.design.interpreter.token.Token;
import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.TokenType;
import org.panda_lang.panda.framework.design.interpreter.token.snippet.Snippet;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionContext;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionResult;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionSubparser;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.ExpressionSubparserWorker;
import org.panda_lang.panda.framework.design.resource.parsers.expression.fixed.util.AbstractExpressionSubparserWorker;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.language.interpreter.token.PandaSnippet;
import org.panda_lang.panda.framework.language.resource.parsers.general.number.NumberParser;
import org.panda_lang.panda.framework.language.resource.parsers.general.number.NumberUtils;
import org.panda_lang.panda.framework.language.resource.syntax.separator.Separators;
import org.panda_lang.panda.framework.language.runtime.expression.PandaExpression;
import org.panda_lang.panda.utilities.commons.annotation.AlwaysNull;

public class NumberExpressionSubparser implements ExpressionSubparser {

    private static final NumberParser PARSER = new NumberParser();

    @Override
    public ExpressionSubparserWorker createWorker() {
        return new NumberWorker();
    }

    @Override
    public String getSubparserName() {
        return "number";
    }

    private static class NumberWorker extends AbstractExpressionSubparserWorker implements ExpressionSubparserWorker {

        private Snippet content;
        private TokenRepresentation period;

        @Override
        public @Nullable ExpressionResult<Expression> next(ExpressionContext context) {
            Token token = context.getCurrentRepresentation().getToken();

            if (Separators.PERIOD.equals(token)) {
                this.period = context.getCurrentRepresentation();
                return ExpressionResult.empty();
            }

            if (token.getType() != TokenType.UNKNOWN) {
                return dispose();
            }

            if (!NumberUtils.isNumeric(token.getTokenValue())) {
                return dispose();
            }

            if (content == null) {
                this.content = new PandaSnippet();
            }

            if (this.period != null) {
                content.addToken(period);
            }

            content.addToken(context.getCurrentRepresentation());
            Value numericValue = PARSER.parse(context.getData(), content);

            if (numericValue == null) {
                return dispose();
            }

            Expression expression = new PandaExpression(numericValue);

            // remove previous result from stack
            if (period != null) {
                context.popExpression();
                dispose();
            }

            return ExpressionResult.of(expression);
        }

        private @AlwaysNull ExpressionResult<Expression> dispose() {
            this.content = null;
            this.period = null;
            return null;
        }

    }

}