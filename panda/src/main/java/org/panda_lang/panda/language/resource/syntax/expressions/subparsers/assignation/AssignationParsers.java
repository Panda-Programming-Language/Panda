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

package org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation;

import org.panda_lang.framework.language.interpreter.parser.PandaParsersUtils;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.array.ArrayValueAssignationSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.variable.VariableAssignationSubparser;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.variable.VariableDeclarationSubparser;

public final class AssignationParsers {

    public static final Class<? extends AssignationSubparser>[] SUBPARSERS = PandaParsersUtils.of(
            VariableAssignationSubparser.class,
            VariableDeclarationSubparser.class,
            ArrayValueAssignationSubparser.class
    );

    private AssignationParsers() { }

}