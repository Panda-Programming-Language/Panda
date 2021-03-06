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

package panda.repl

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test
import panda.interpreter.Panda
import panda.interpreter.architecture.statement.PandaVariableData
import panda.interpreter.architecture.statement.Variable
import panda.interpreter.architecture.type.member.method.PandaMethod
import panda.interpreter.architecture.type.member.parameter.PropertyParameterImpl
import panda.interpreter.architecture.type.signature.Signature
import panda.interpreter.parser.Context
import panda.interpreter.source.ClassSource
import panda.interpreter.syntax.expressions.subparsers.number.PandaNumbers
import panda.interpreter.utils.PandaUtils

@CompileStatic
final class ReplTest {

    @Test
    void test() throws Exception {
        Panda panda = PandaUtils.defaultInstance()
        ReplConsole console = new ReplConsole(panda, System.in, true)
        ReplCreator creator = Repl.creator(console)
        Context<?> context = creator.getContext()

        Signature doubleType = context.getTypeLoader().requireType(PandaNumbers.DOUBLE).getSignature()

        Repl repl = creator
                .variable(new PandaVariableData(context.getTypeLoader().requireType(PandaNumbers.INT).getSignature(), "i"), 5)
                .define(PandaMethod.builder()
                        .name("sqrt")
                        .parameters([ new PropertyParameterImpl(0, doubleType, "i") ])
                        .customBody({ method, stack, instance, Object[] arguments ->
                            Math.sqrt(((Number) arguments[0]).doubleValue())
                        })
                        .returnType(doubleType)
                        .location(new ClassSource(null, ReplTest.class).toLocation())
                        .build())
                .addVariableChangeListener({ Variable variable, previous, current ->
                    panda.getLogger().info("// variable change :: " + variable.getName() + " = " + previous + " -> " + current)
                })
                .create()

        ReplUtils.print(panda, repl.evaluate("i"))
        ReplUtils.print(panda, repl.evaluate("i = 4"))

        repl.regenerate()

        ReplUtils.print(panda, repl.evaluate("i"))
        ReplUtils.print(panda, repl.evaluate("String text = 'hello'; 'second expression'"))

        ReplUtils.print(panda, repl.evaluate("vars"))
        ReplUtils.print(panda, repl.evaluate("history"))

        ReplUtils.print(panda, repl.evaluate("Double double = 9.86960440109"))
        ReplUtils.print(panda, repl.evaluate("sqrt(double)"))
    }

}