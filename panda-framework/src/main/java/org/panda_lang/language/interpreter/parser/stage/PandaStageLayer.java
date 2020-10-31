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

package org.panda_lang.language.interpreter.parser.stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class PandaStageLayer implements StageLayer {

    private static final AtomicInteger ID = new AtomicInteger();

    private final int id;
    private final StagePhase cycle;
    private final Map<StageOrder, List<StageTask>> tasks = new HashMap<>();

    public PandaStageLayer(StagePhase cycle) {
        this.id = ID.getAndIncrement();
        this.cycle = cycle;
    }

    @Override
    public void callTasks() {
        Map<StageOrder, List<StageTask>> unitsMap = new TreeMap<>(tasks);
        tasks.clear();

        for (List<StageTask> units : unitsMap.values()) {
            for (StageTask unit : units) {
                unit.call(cycle);
            }
        }
    }

    @Override
    public StageLayer delegate(StageOrder priority, StageTask task) {
        tasks.computeIfAbsent(priority, (key) -> new ArrayList<>(2)).add(task);
        return this;
    }

    @Override
    public int countTasks() {
        return tasks.size();
    }

    @Override
    public String toString() {
        return "Stage Layer " + id + ": " + countTasks() + " tasks";
    }

}