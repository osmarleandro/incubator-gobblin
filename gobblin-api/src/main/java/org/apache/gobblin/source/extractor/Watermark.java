/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.gobblin.source.extractor;

import com.google.gson.JsonElement;

/**
 * A {@link Watermark} represents a checkpoint in data processing, and indicates that all data up to  specific point has
 * been pulled from some source. Implementations of this interface are responsible for defining data structures in order
 * to track this state.
 *
 * <p>
 *  A {@link Watermark} will be serialized in {@link org.apache.gobblin.source.workunit.WorkUnit}s and
 *  {@link org.apache.gobblin.configuration.WorkUnitState}s. The {@link #toJson()} method will be used to serialize the
 *  {@link Watermark} into a {@link JsonElement}.
 * </p>
 */
public interface Watermark extends IWatermark {
}
