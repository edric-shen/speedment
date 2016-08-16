/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.lazy.specialized;

import java.sql.Timestamp;
import com.speedment.common.lazy.Lazy;
import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;

/**
 * Specialized Lazy initialization class for Timestamp types.
 *
 * @author Per Minborg
 */
public final class LazyTimestamp implements Lazy<Timestamp> {

    private volatile Timestamp value;

    private LazyTimestamp() {
    }
    
    @Override
    public Timestamp getOrCompute(Supplier<Timestamp> supplier) {
        // With this local variable, we only need to do one volatile read
        final Timestamp result = value;
        return result == null ? maybeCompute(supplier) : result;
    }

    private synchronized Timestamp maybeCompute(Supplier<Timestamp> supplier) {
        if (value == null) {
            value = requireNonNull(supplier.get());
        }
        return value;
    }

    public static LazyTimestamp create() {
        return new LazyTimestamp();
    }
}
