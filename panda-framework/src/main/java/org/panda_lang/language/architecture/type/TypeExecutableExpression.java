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

package org.panda_lang.language.architecture.type;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.language.architecture.expression.Expression;
import org.panda_lang.language.architecture.expression.ExpressionUtils;
import org.panda_lang.language.architecture.expression.ExpressionValueType;
import org.panda_lang.language.architecture.type.member.Member;
import org.panda_lang.language.architecture.type.signature.Signature;
import org.panda_lang.language.runtime.ProcessStack;

public final class TypeExecutableExpression implements Expression {

    private final Member executable;
    private final Expression instanceExpression;
    private final Expression[] arguments;

    public TypeExecutableExpression(@Nullable Expression instance, Adjustment<?> adjustment) {
        this(instance, adjustment.getExecutable(), adjustment.getArguments());
    }

    public TypeExecutableExpression(@Nullable Expression instance, Member executable, Expression[] arguments) {
        this.executable = executable;
        this.instanceExpression = instance;
        this.arguments = arguments;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object evaluate(ProcessStack stack, Object instance) throws Exception {
        Object[] values = ExpressionUtils.evaluate(stack, instance, arguments);

        if (instanceExpression != null) {
            instance = instanceExpression.evaluate(stack, instance);
        }

        return executable.invoke(stack, instance, values);
    }

    @Override
    public Signature getSignature() {
        return executable.getReturnType();
    }

    @Override
    public ExpressionValueType getExpressionType() {
        return ExpressionValueType.DYNAMIC;
    }

    @Override
    public String toString() {
        return executable + " -> " + getSignature();
    }

}
