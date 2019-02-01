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

package org.panda_lang.panda.framework.language.resource.parsers.expression.callbacks.operation.subparsers.math;

import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototype;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.language.resource.PandaTypes;
import org.panda_lang.panda.framework.language.resource.parsers.expression.callbacks.operation.rpn.RPNOperationAction;
import org.panda_lang.panda.framework.language.resource.parsers.expression.callbacks.operation.subparsers.number.NumberOperation;

public abstract class MathOperation extends NumberOperation {

    public abstract RPNOperationAction of(ClassPrototype returnType, int priority);

    @Override
    public RPNOperationAction of(Expression a, Expression b) {
        ClassPrototype returnType = returnType(a.getReturnType(), b.getReturnType());
        return of(returnType, super.getPriority(returnType));
    }

    @Override
    public ClassPrototype returnType(ClassPrototype a, ClassPrototype b) {
        return estimateType(a, b);
    }

    @Override
    public ClassPrototype requiredType() {
        return PandaTypes.NUMBER;
    }

}
