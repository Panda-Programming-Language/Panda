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

package org.panda_lang.framework.language.architecture.statement;

import org.panda_lang.framework.design.architecture.prototype.Referencable;
import org.panda_lang.framework.design.architecture.prototype.Reference;
import org.panda_lang.framework.design.architecture.statement.VariableData;

public class PandaVariableData implements VariableData  {

    private final String name;
    private final Referencable type;
    private final boolean mutable;
    private final boolean nillable;

    public PandaVariableData(Referencable type, String name, boolean mutable, boolean nillable) {
        if (type == null) {
            throw new IllegalArgumentException("Variable type cannot be null");
        }

        if (name == null) {
            throw new IllegalArgumentException("Variable name cannot be null");
        }

        this.name = name;
        this.type = type;
        this.mutable = mutable;
        this.nillable = nillable;
    }

    public PandaVariableData(Referencable type, String name) {
        this(type, name, true, true);
    }

    @Override
    public boolean isNillable() {
        return nillable;
    }

    @Override
    public boolean isMutable() {
        return mutable;
    }

    @Override
    public Reference getType() {
        return type.toReference();
    }

    @Override
    public String getName() {
        return name;
    }

}
