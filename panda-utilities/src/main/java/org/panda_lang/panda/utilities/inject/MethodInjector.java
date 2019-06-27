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

package org.panda_lang.panda.utilities.inject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class MethodInjector {

    private final InjectorProcessor processor;

    MethodInjector(Injector injector) {
        this.processor = new InjectorProcessor(injector);
    }

    @SuppressWarnings("unchecked")
    protected <T> T invoke(Method method, Object instance) {
        try {
            method.setAccessible(true);
            return (T) method.invoke(instance, processor.fetchValues(method));
        } catch (IllegalAccessException | InvocationTargetException | InjectorException e ) {
            throw new InjectorException("Cannot invoke the method, caused by " + e.getClass() + (e.getMessage() != null ? ": " + e.getMessage() : ""), e);
        }
    }

}