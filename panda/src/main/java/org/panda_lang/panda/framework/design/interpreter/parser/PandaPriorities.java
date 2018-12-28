/*
 * Copyright (c) 2015-2018 Dzikoysk
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

package org.panda_lang.panda.framework.design.interpreter.parser;

public class PandaPriorities {

    /**
     * Used by {@link org.panda_lang.panda.framework.language.interpreter.parser.prototype.method.MethodParser}
     */
    public static final int PROTOTYPE_METHOD_PARSER = 1;
    /**
     * Used by {@link org.panda_lang.panda.framework.language.interpreter.parser.prototype.field.FieldParser}
     */
    public static final int PROTOTYPE_FIELD_PARSER = 2;

    /**
     * Used by {@link org.panda_lang.panda.framework.language.interpreter.parser.statement.scope.block.BlockParser}
     */
    public static final int SCOPE_BLOCK_PARSER = 1;
    /**
     * Used by {@link org.panda_lang.panda.framework.language.interpreter.parser.statement.invoker.MethodInvokerParser}
     */
    public static final int SCOPE_METHOD_INVOKER_PARSER = 2;
    /**
     * Used by {@link org.panda_lang.panda.framework.language.interpreter.parser.statement.assignation.AssignationParser}
     */
    public static final int SCOPE_ASSIGNATION_PARSER = 3;
    /**
     *
     */
    public static final int SCOPE_VARIABLE_PARSER = 3;

}
