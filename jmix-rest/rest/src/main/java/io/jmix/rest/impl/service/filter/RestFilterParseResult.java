/*
 * Copyright (c) 2008-2019 Haulmont.
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

package io.jmix.rest.impl.service.filter;

import jakarta.annotation.Nullable;
import java.util.Map;

/**
 * The result of JSON entities filter parsing. It is used by the {@link RestFilterParser}
 */
public class RestFilterParseResult {

    protected String jpqlWhere;

    protected Map<String, Object> queryParameters;

    public RestFilterParseResult(@Nullable String jpqlWhere, @Nullable Map<String, Object> queryParameters) {
        this.jpqlWhere = jpqlWhere;
        this.queryParameters = queryParameters;
    }

    @Nullable
    public String getJpqlWhere() {
        return jpqlWhere;
    }

    @Nullable
    public Map<String, Object> getQueryParameters() {
        return queryParameters;
    }
}
