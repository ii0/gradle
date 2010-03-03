/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.project.taskfactory;

import org.gradle.api.execution.TaskExecutionResult;
import org.gradle.api.internal.TaskInternal;
import org.gradle.api.internal.changedetection.TaskArtifactState;
import org.gradle.api.internal.changedetection.TaskArtifactStateRepository;
import org.gradle.api.internal.tasks.TaskExecuter;
import org.gradle.api.internal.tasks.TaskStateInternal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionShortCircuitTaskExecuter implements TaskExecuter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionShortCircuitTaskExecuter.class);
    private final TaskExecuter executer;
    private final TaskArtifactStateRepository repository;
    private static final TaskExecutionResult UP_TO_DATE_RESULT = new TaskExecutionResult() {
        public Throwable getFailure() {
            return null;
        }

        public void rethrowFailure() {
        }

        public String getSkipMessage() {
            return "UP-TO-DATE";
        }
    };

    public ExecutionShortCircuitTaskExecuter(TaskExecuter executer, TaskArtifactStateRepository repository) {
        this.executer = executer;
        this.repository = repository;
    }

    public void execute(TaskInternal task, TaskStateInternal state) {
        LOGGER.debug("Determining if {} is up-to-date", task);
        TaskArtifactState taskArtifactState = repository.getStateFor(task);
        if (taskArtifactState.isUpToDate()) {
            LOGGER.debug("{} is up-to-date", task);
            state.skipped("UP-TO-DATE");
            return;

        }
        LOGGER.debug("{} is not up-to-date", task);

        taskArtifactState.invalidate();
        executer.execute(task, state);
        if (state.getFailure() == null) {
            taskArtifactState.update();
        }
    }
}
