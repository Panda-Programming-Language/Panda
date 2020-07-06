/*
 * Copyright (c) 2020 Dzikoysk
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

package org.panda_lang.framework.language.interpreter.pattern.custom.verifiers;

import org.panda_lang.framework.design.interpreter.token.Snippetable;
import org.panda_lang.framework.design.interpreter.token.TokenType;
import org.panda_lang.framework.language.interpreter.pattern.custom.CustomVerify;
import org.panda_lang.framework.language.interpreter.token.SynchronizedSource;
import org.panda_lang.utilities.commons.collection.Pair;

import java.util.List;

public final class NextTokenTypeVerifier implements CustomVerify<Snippetable> {

    private final TokenType[] types;

    public NextTokenTypeVerifier(TokenType... types) {
        this.types = types;
    }

    @Override
    public boolean verify(List<Pair<String, Object>> results, SynchronizedSource source, Snippetable content) {
        if (!source.hasNext()) {
            return false;
        }

        TokenType next = source.getNext().getType();

        for (TokenType type : types) {
            if (type == next) {
                return true;
            }
        }

        return false;
    }

}
