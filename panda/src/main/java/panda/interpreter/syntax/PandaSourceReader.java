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

package panda.interpreter.syntax;

import panda.interpreter.architecture.expression.Expression;
import panda.interpreter.architecture.type.Visibility;
import panda.interpreter.parser.Context;
import panda.interpreter.parser.SourceReader;
import panda.interpreter.parser.expression.ExpressionParser;
import panda.interpreter.token.PandaSnippet;
import panda.interpreter.token.Snippet;
import panda.interpreter.token.SourceStream;
import panda.interpreter.token.TokenInfo;
import panda.interpreter.resource.syntax.TokenTypes;
import panda.interpreter.resource.syntax.auxiliary.Section;
import panda.interpreter.resource.syntax.keyword.Keywords;
import panda.interpreter.resource.syntax.operator.Operators;
import panda.interpreter.resource.syntax.separator.Separators;
import panda.interpreter.syntax.type.SignatureParser;
import panda.interpreter.syntax.type.SignatureSource;
import panda.std.Option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PandaSourceReader extends SourceReader {

    private static final SignatureParser SIGNATURE_PARSER = new SignatureParser();

    public PandaSourceReader(SourceStream stream) {
        super(stream);
    }

    public Option<Snippet> readBody() {
        return readSection(Separators.BRACE_LEFT)
                .map(token -> token.toToken(Section.class).getContent());
    }

    public Option<Snippet> readArguments() {
        return readSection(Separators.PARENTHESIS_LEFT)
                .map(token -> token.toToken(Section.class).getContent());
    }

    public Option<SignatureSource> readSignature() {
        return read(token -> token.getType() == TokenTypes.UNKNOWN && !Character.isDigit(token.getValue().charAt(0)))
                .flatMap(name -> optionalRead(this::readGenerics)
                    .map(section -> SIGNATURE_PARSER.readSignatures(section.getContent()))
                    .orElse(Collections.emptyList())
                    .map(generics -> new SignatureSource(name, generics))
                );
    }

    public Option<Visibility> readVisibility() {
        return readVariant(Keywords.OPEN, Keywords.SHARED, Keywords.INTERNAL, Keywords.CLOSED).map(Visibility::of);
    }

    public Option<Section> readGenerics() {
        return readBetween(Operators.ANGLE_LEFT, Operators.ANGLE_RIGHT);
    }

    public Option<Snippet> readPandaQualifier() {
        List<TokenInfo> tokens = new ArrayList<>();
        boolean nextAny = false;

        while (super.stream.hasUnreadSource()) {
            boolean any = nextAny;
            nextAny = false;

            Option<TokenInfo> result = optionalRead(() -> read(read -> read.getType() == TokenTypes.UNKNOWN || (any && read.getType() == TokenTypes.KEYWORD)));

            if (result.isEmpty()) {
                result = optionalRead(() -> read(read -> read.contentEquals(Operators.SUBTRACTION)
                        || read.contentEquals(Separators.PERIOD)
                        || read.contentEquals(Operators.COLON)));
                nextAny = true;
            }

            result.peek(tokens::add);

            if (result.isEmpty()) {
                break;
            }
        }

        if (tokens.isEmpty()) {
            return Option.none();
        }

        return Option.of(PandaSnippet.ofImmutable(tokens));
    }

    public List<Expression> readExpressions(Context<?> context) {
        ExpressionParser parser = context.getExpressionParser();
        List<Expression> expressions = new ArrayList<>();

        while (getStream().hasUnreadSource()) {
            Option<Expression> argument = optionalRead(() -> parser.parseSilently(context, getStream()));

            if (argument.isEmpty()) {
                break;
            }

            expressions.add(argument.get());

            if (super.stream.hasUnreadSource()) {
                if (!super.stream.getCurrent().equals(Separators.COMMA)) {
                    break;
                }

                super.stream.read();
            }
        }

        return expressions;
    }

}
