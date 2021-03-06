/*
 * Copyright (c) 2021 dzikoysk
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

package panda.interpreter.syntax.expressions.subparsers.operation.rpn;

import panda.interpreter.architecture.expression.Expression;
import panda.interpreter.architecture.type.Type;
import panda.interpreter.architecture.module.TypeLoader;

public interface RPNOperationSupplier<R> {

    RPNOperationAction<R> of(TypeLoader typeLoader, Expression a, Expression b);

    Type returnType(TypeLoader typeLoader, Type a, Type b);

    Type requiredType(TypeLoader typeLoader);

}
