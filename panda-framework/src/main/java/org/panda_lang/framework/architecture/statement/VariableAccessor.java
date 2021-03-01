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

package org.panda_lang.framework.architecture.statement;

import org.panda_lang.framework.architecture.dynamic.accessor.AbstractAccessor;
import org.panda_lang.framework.architecture.dynamic.assigner.Assigner;
import org.panda_lang.framework.architecture.expression.Expression;
import org.panda_lang.framework.interpreter.source.Localizable;

public final class VariableAccessor extends AbstractAccessor<Variable> {

    public VariableAccessor(Variable variable) {
        super((stack, instance) -> stack.getCurrentFrame(), variable, variable.getPointer());
    }

    @Override
    public Assigner<Variable> toAssigner(Localizable localizable, boolean initialize, Expression value) {
        return new VariableAssigner(localizable.toLocation(), this, initialize, value);
    }

}