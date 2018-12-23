package org.panda_lang.panda.framework.design.interpreter.pattern.token.wildcard.reader;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.design.interpreter.token.Tokens;
import org.panda_lang.panda.framework.design.interpreter.pattern.token.TokenPattern;
import org.panda_lang.panda.framework.language.interpreter.token.distributors.TokenDistributor;

public class WildcardReaderCompiler {

    private final TokenPattern pattern;

    public WildcardReaderCompiler(TokenPattern pattern) {
        this.pattern = pattern;
    }

    public @Nullable Tokens extract(String data, TokenDistributor distributor) {
        for (WildcardReader wildcardReader : pattern.getWildcardReaders()) {
            if (!wildcardReader.match(data)) {
                continue;
            }

            return wildcardReader.read(data, distributor);
        }

        return null;
    }

}
