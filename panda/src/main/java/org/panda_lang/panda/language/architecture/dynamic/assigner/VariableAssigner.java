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

package org.panda_lang.panda.language.architecture.dynamic.assigner;

import org.panda_lang.panda.framework.design.architecture.statement.Variable;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.runtime.flow.Flow;
import org.panda_lang.panda.language.architecture.dynamic.accessor.Accessor;
import org.panda_lang.panda.language.runtime.PandaRuntimeException;

public class VariableAssigner extends AbstractAssigner<Variable> {

    private final boolean initialize;
    private final Expression expression;

    public VariableAssigner(Accessor<Variable> accessor, boolean initialize, Expression expression) {
        super(accessor);
        this.initialize = initialize;
        this.expression = expression;
    }

    @Override
    public void execute(Flow flow) {
        Variable variable = accessor.getVariable();

        if (!initialize && !variable.isMutable()) {
            throw new PandaRuntimeException("Cannot change value of immutable variable '" + variable.getName() + "'");
        }

        Object value = expression.evaluate(flow);

        if (value == null && !variable.isNillable()) {
            throw new PandaRuntimeException("Cannot assign null to variable '" + variable.getName() + "' without nil modifier");
        }

        accessor.fetchMemoryContainer(flow).set(accessor.getMemoryPointer(), value);
    }

    @Override
    public String toString() {
        return "'v_memory'[" + accessor.getMemoryPointer() + "] << " + expression;
    }

}
