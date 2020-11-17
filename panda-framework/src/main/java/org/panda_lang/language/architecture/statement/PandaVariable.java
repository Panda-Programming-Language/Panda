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

package org.panda_lang.language.architecture.statement;

import org.panda_lang.language.architecture.type.signature.Signature;

public class PandaVariable extends PandaVariableData implements Variable {

    protected final int pointer;
    protected boolean initialized;

    public PandaVariable(int pointer, Signature signature, String name, boolean mutable, boolean nillable) {
        super(signature, name, mutable, nillable);

        if (pointer < 0) {
            throw new IllegalArgumentException("Invalid variable id");
        }

        this.pointer = pointer;
    }

    public PandaVariable(int pointer, VariableData data) {
        this(pointer, data.getSignature(), data.getName(), data.isMutable(), data.isNillable());
    }

    @Override
    public synchronized Variable initialize() {
        this.initialized = true;
        return this;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public int getPointer() {
        return pointer;
    }

    @Override
    public String toString() {
        return "'" + getName() + "': '" + getSignature() + "'";
    }

}