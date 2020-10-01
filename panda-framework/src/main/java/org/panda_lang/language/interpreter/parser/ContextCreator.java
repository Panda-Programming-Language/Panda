package org.panda_lang.language.interpreter.parser;

import org.panda_lang.language.architecture.Script;
import org.panda_lang.language.architecture.module.Imports;
import org.panda_lang.language.architecture.statement.Scope;
import org.panda_lang.language.interpreter.token.Snippet;
import org.panda_lang.language.interpreter.token.SourceStream;

public interface ContextCreator<T> extends Contextual<T> {

    ContextCreator<T> fork();

    ContextCreator<T> withSubject(T subject);

    ContextCreator<T> withStream(SourceStream stream);

    ContextCreator<T> withSource(Snippet source);

    ContextCreator<T> withScope(Scope scope);

    ContextCreator<T> withImports(Imports imports);

    ContextCreator<T> withScriptSource(Snippet scriptSource);

    ContextCreator<T> withScript(Script script);

}
