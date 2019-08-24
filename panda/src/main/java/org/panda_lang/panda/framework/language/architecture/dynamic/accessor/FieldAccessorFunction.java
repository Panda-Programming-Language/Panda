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

package org.panda_lang.panda.framework.language.architecture.dynamic.accessor;

import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.runtime.flow.Flow;
import org.panda_lang.panda.framework.design.runtime.memory.MemoryContainer;
import org.panda_lang.panda.framework.language.architecture.prototype.standard.structure.ClassPrototypeLivingFrame;
import org.panda_lang.panda.framework.language.runtime.PandaRuntimeException;

import java.util.function.Function;

public class FieldAccessorFunction implements Function<Flow, MemoryContainer> {

    private final Expression instanceExpression;

    public FieldAccessorFunction(Expression instanceExpression) {
        this.instanceExpression = instanceExpression;
    }

    @Override
    public MemoryContainer apply(Flow flow) {
        Object instance = instanceExpression.evaluate(flow);

        if (instance == null) {
            throw new PandaRuntimeException("Instance is not defined");
        }

        if (!(instance instanceof ClassPrototypeLivingFrame)) {
            throw new PandaRuntimeException("Cannot get field value of external object");
        }

        return (MemoryContainer) instance;
    }

}