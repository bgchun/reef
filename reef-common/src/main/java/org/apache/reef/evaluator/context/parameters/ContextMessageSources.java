/**
 * Copyright (C) 2014 Microsoft Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.reef.evaluator.context.parameters;

import org.apache.reef.evaluator.context.ContextMessageSource;
import org.apache.reef.runtime.common.evaluator.context.defaults.DefaultContextMessageSource;
import org.apache.reef.tang.annotations.Name;
import org.apache.reef.tang.annotations.NamedParameter;

import java.util.Set;

/**
 * The set of ContextMessageSource implementations called during heartbeats.
 */
@NamedParameter(doc = "The set of ContextMessageSource implementations called during heartbeats.",
    default_classes = DefaultContextMessageSource.class)
public final class ContextMessageSources implements Name<Set<ContextMessageSource>> {
}