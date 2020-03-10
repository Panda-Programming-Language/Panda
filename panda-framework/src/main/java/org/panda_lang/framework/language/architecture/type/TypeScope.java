/*
 * Copyright (c) 2015-2020 Dzikoysk
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

package org.panda_lang.framework.language.architecture.type;

import org.panda_lang.framework.PandaFrameworkException;
import org.panda_lang.framework.design.architecture.expression.Expression;
import org.panda_lang.framework.design.architecture.type.Type;
import org.panda_lang.framework.design.architecture.type.TypeField;
import org.panda_lang.framework.design.interpreter.source.SourceLocation;
import org.panda_lang.framework.design.runtime.ProcessStack;
import org.panda_lang.framework.language.architecture.statement.AbstractFramedScope;
import org.panda_lang.framework.language.runtime.PandaRuntimeException;
import org.panda_lang.utilities.commons.ArrayUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public final class TypeScope extends AbstractFramedScope {

    private static final Class<?>[] TYPE_FRAME_ARRAY = { TypeFrame.class };

    private final Type type;

    public TypeScope(SourceLocation location, Type type) {
        super(location);
        this.type = type;
    }

    public TypeInstance createInstance(ProcessStack stack, Class<?>[] parameterTypes, Object[] arguments) throws Exception {
        TypeFrame typeFrame = new TypeFrame(this, stack.getProcess());
        TypeInstance typeInstance;

        try {
            //System.out.println(getConstructor() + " | " + this + " | " + stack.getProcess());
            typeInstance = getConstructor(parameterTypes).newInstance(ArrayUtils.merge(typeFrame, arguments, Object[]::new));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new PandaRuntimeException(e.getTargetException().getMessage(), e.getTargetException());
        }

        // typeInstance.__panda__set_frame();

        for (TypeField field : type.getFields().getDeclaredProperties()) {
            if (!field.hasDefaultValue()) {
                continue;
            }

            if (field.isStatic()) {
                field.fetchStaticValue(); // just init
                continue;
            }

            Expression expression = field.getDefaultValue();
            typeInstance.__panda__get_frame().set(field.getPointer(), expression.evaluate(stack, typeInstance));
        }

        return typeInstance;
    }

    @SuppressWarnings("unchecked")
    private Constructor<? extends TypeInstance> getConstructor(Class<?>[] parameterTypes) {
        parameterTypes = ArrayUtils.merge(TypeFrame.class, parameterTypes, Class[]::new);

        try {
            return (Constructor<? extends TypeInstance>) type.getAssociatedClass().fetchImplementation().getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new PandaFrameworkException(type.getAssociatedClass().fetchImplementation() + " does not implement " + Arrays.toString(parameterTypes) + " constructor");
        }
    }

    public SourceLocation getLocation() {
        return location;
    }

    public Type getType() {
        return type;
    }

}
