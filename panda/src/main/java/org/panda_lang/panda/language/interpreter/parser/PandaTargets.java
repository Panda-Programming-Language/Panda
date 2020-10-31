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

package org.panda_lang.panda.language.interpreter.parser;

import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.AssignationContext;
import org.panda_lang.utilities.commons.collection.Component;

import java.util.Collection;
import java.util.Collections;

public final class PandaTargets {

    /**
     * Text representation of {@link PandaTargets#ASSIGNATION}
     */
    public static final String ASSIGNER_LABEL = "assignation";
    /**
     * Assigner parsers, used by {@link org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.AssignationExpressionSubparser}
     */
    public static final Component<AssignationContext> ASSIGNATION = Component.of(ASSIGNER_LABEL, AssignationContext.class);

    /**
     * Get collection of all components
     *
     * @return the collection of components
     */
    public static Collection<Component<?>> getPipelineComponents() {
        return Collections.singletonList(ASSIGNATION);
    }

}