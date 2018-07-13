/*
 * Copyright 2018 the original author or authors.
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

package org.gradle.api.internal.artifacts.result;

import org.gradle.api.artifacts.result.ResolvedComponentResult;

import javax.annotation.Nullable;

/**
 * Represents a component instance in the resolved dependency graph. Provides some basic identity and dependency information about the component.
 */
public interface ResolvedComponentResultInternal extends ResolvedComponentResult {


    /**
     * <p>Returns the identifier of the repository used to source this component.
     *
     * @return the identifier of the repository used to source this component , or {@code null} if this component was not resolved from a repository.
     */
    @Nullable
    String getRepositoryId();

}
