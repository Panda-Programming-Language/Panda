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

package panda.interpreter.architecture.dynamic.assigner;

import panda.interpreter.architecture.expression.DynamicExpression;
import panda.interpreter.architecture.expression.PandaExpression;
import panda.interpreter.architecture.type.signature.Signature;
import panda.interpreter.runtime.ProcessStack;

public final class AssignerExpression extends PandaExpression {

    public AssignerExpression(Assigner<?> assigner) {
        super(new AssignerDynamicExpression(assigner));
    }

    private static final class AssignerDynamicExpression implements DynamicExpression {

        private final Assigner<?> assigner;

        public AssignerDynamicExpression(Assigner<?> assigner) {
            this.assigner = assigner;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object evaluate(ProcessStack stack, Object instance) throws Exception {
            return assigner.execute(stack, instance);
        }

        @Override
        public Signature getReturnType() {
            return assigner.getAccessor().getSignature();
        }

    }

}
