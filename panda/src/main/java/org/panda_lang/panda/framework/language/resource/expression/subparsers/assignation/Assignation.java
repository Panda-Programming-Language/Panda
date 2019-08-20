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

package org.panda_lang.panda.framework.language.resource.expression.subparsers.assignation;

import org.panda_lang.panda.framework.design.architecture.dynamic.ExecutableStatement;
import org.panda_lang.panda.framework.design.architecture.dynamic.accessor.Accessor;
import org.panda_lang.panda.framework.design.architecture.dynamic.assigner.Assigner;
import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototype;
import org.panda_lang.panda.framework.design.architecture.value.Value;
import org.panda_lang.panda.framework.design.architecture.value.Variable;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.runtime.flow.Flow;
import org.panda_lang.panda.framework.language.architecture.dynamic.AbstractExecutableStatement;
import org.panda_lang.panda.framework.language.runtime.expression.DynamicExpression;

final class Assignation extends AbstractExecutableStatement implements DynamicExpression {

    private final ExecutableStatement assigner;
    private final Accessor<? extends Variable> accessor;

    public Assignation(Assigner<? extends Variable> assigner) {
        this.assigner = assigner.toExecutableStatement();
        this.accessor = assigner.getAccessor();
    }

    @Override
    public void execute(Flow flow) {
        assigner.execute(flow);
    }

    @Override
    public Value call(Expression expression, Flow flow) {
        return accessor.getValue(flow);
    }

    @Override
    public ClassPrototype getReturnType() {
        return accessor.getTypeReference().fetch();
    }

}