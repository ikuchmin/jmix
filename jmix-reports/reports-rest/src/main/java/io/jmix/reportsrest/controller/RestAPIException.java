/*
 * Copyright 2021 Haulmont.
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

package io.jmix.reportsrest.controller;

import org.springframework.http.HttpStatus;

import jakarta.annotation.Nullable;

public class RestAPIException extends RuntimeException {

    protected HttpStatus httpStatus;

    protected String details;

    public RestAPIException(String message, String details, HttpStatus httpStatus) {
        this(message, details, httpStatus, null);
    }

    public RestAPIException(String message, String details, HttpStatus httpStatus, @Nullable Throwable cause) {
        super(message, cause);
        this.details = details;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getDetails() {
        return details;
    }
}
