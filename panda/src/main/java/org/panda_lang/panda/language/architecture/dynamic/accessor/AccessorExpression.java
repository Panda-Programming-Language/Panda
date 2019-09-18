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

package org.panda_lang.panda.language.architecture.dynamic.accessor;

import org.panda_lang.panda.framework.design.runtime.ProcessStack;
import org.panda_lang.panda.language.runtime.expression.DynamicExpression;
import org.panda_lang.panda.language.runtime.expression.AbstractDynamicExpression;
import org.panda_lang.panda.language.runtime.expression.PandaExpression;

public class AccessorExpression extends PandaExpression {

    private final Accessor<?> accessor;

    public AccessorExpression(Accessor<?> accessor, DynamicExpression callback) {
        super(callback);
        this.accessor = accessor;
    }

    public AccessorExpression(Accessor<?> accessor) {
        this(accessor, new AbstractDynamicExpression(accessor.getTypeReference().fetch()) {
            @Override
            public <T> T evaluate(ProcessStack stack, Object instance) {
                return accessor.getValue(stack, instance);
            }
        });
    }

    public Accessor<?> getAccessor() {
        return accessor;
    }

}