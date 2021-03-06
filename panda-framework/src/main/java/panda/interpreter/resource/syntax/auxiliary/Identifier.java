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

package panda.interpreter.resource.syntax.auxiliary;

import panda.interpreter.token.TokenType;
import panda.interpreter.token.EqualableToken;
import panda.interpreter.resource.syntax.TokenTypes;

public final class Identifier extends EqualableToken {

    private final String identifier;

    public Identifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getValue() {
        return identifier;
    }

    @Override
    public TokenType getType() {
        return TokenTypes.IDENTIFIER;
    }

}
