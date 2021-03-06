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

package panda.interpreter.architecture.type;

import org.jetbrains.annotations.Nullable;
import panda.interpreter.architecture.module.Module;
import panda.interpreter.architecture.type.member.Metadata;
import panda.interpreter.parser.Context;
import panda.interpreter.parser.PandaParserFailure;
import panda.interpreter.source.Source;
import panda.interpreter.token.Snippetable;
import panda.std.Option;

public final class VisibilityComparator {

    public static final String NOTE_MESSAGE = "You may want to change the architecture of your application or you can just simply hack it";

    public static boolean requireAccess(Metadata requested, Context<?> context, Snippetable source) {
        canAccess(requested, context).peek(message -> {
            throw new PandaParserFailure(context, source, message, NOTE_MESSAGE);
        });
        return true;
    }

    public static Option<String> canAccess(Metadata requested, Context<?> context) {
        return canAccess(requested, context.getScript().getModule(), context.getSource().getLocation().getSource());
    }

    public static Option<String> canAccess(Metadata requested, Module currentModule, @Nullable Source currentSource) {
        if (requested.getVisibility() == Visibility.OPEN) {
            return Option.none();
        }

        if (requested.getVisibility() == Visibility.SHARED) {
            Module requestedModule = requested.getType().getModule();

            if (currentModule.equals(requestedModule) /*|| requestedModule.hasSubmodule(currentModule) */) {
                return Option.none();
            }

            return Option.of("Cannot access the property '" + requested + "' outside of the '" + requestedModule.getName() + "' module or its submodules");
        }

        if (requested.getType().getLocation().getSource().equals(currentSource)) {
            return Option.none();
        }

        return Option.of("Cannot access the property '" + requested + "' outside of the " + requested.getType().getLocation().getSource().getId() + " source");
    }

}
