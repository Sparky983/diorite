/*
 * Copyright 2022 Sparky983
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

package io.github.sparky983.diorite.io;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * A runtime io exception.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public class RuntimeIOException extends RuntimeException {

    /**
     * Constructs a new runtime io exception.
     *
     * @since 1.0.0
     */
    public RuntimeIOException() {

    }

    /**
     * Constructs a new runtime io exception with the specified message.
     *
     * @param message The message.
     * @since 1.0.0
     */
    public RuntimeIOException(final @Nullable String message) {

        super(message);
    }

    /**
     * Constructs a new runtime io exception with the specified cause.
     *
     * @param cause The checked version of the io exception.
     * @since 1.0.0
     */
    public RuntimeIOException(final @Nullable IOException cause) {

        super(cause);
    }
}
