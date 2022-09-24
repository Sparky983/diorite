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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * An exception that is thrown when an error occurs while attempting to decode something.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public class DecodeException extends RuntimeException {

    private final boolean canIgnore;

    /**
     * Constructs a new decode exception.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    public DecodeException() {

        canIgnore = false;
    }

    /**
     * Constructs a new decode exception with the specified message.
     *
     * @param message The message.
     * @since 1.0.0
     */
    @Contract(pure = true)
    public DecodeException(final @Nullable String message) {

        super(message);
        this.canIgnore = false;
    }

    /**
     * Constructs a new decode exception with the specified message and cause.
     *
     * @param message The message.
     * @param cause The cause.
     * @since 1.0.0
     */
    @Contract(pure = true)
    public DecodeException(final @Nullable String message, final @Nullable Throwable cause) {

        super(message, cause);
        this.canIgnore = false;
    }

    /**
     * Constructs a new decode exception with the specified cause.
     *
     * @param cause The cause.
     * @since 1.0.0
     */
    @Contract(pure = true)
    public DecodeException(final @Nullable Throwable cause) {

        super(cause);
        this.canIgnore = false;
    }


    /**
     * Constructs a new decode exception with the specified message and whether it is safe to
     * ignore.
     *
     * @param message The message.
     * @param canIgnore whether it is safe to ignore the exception.
     * @since 1.0.0
     */
    @Contract(pure = true)
    public DecodeException(final @Nullable String message, final boolean canIgnore) {

        super(message);
        this.canIgnore = canIgnore;
    }

    /**
     * Constructs a new decode exception with the specified message, cause and whether it is safe to
     * ignore.
     *
     * @param message The message.
     * @param cause The cause.
     * @param canIgnore whether it is safe to ignore the exception.
     * @since 1.0.0
     */
    @Contract(pure = true)
    public DecodeException(final @Nullable String message, final @Nullable Throwable cause, final boolean canIgnore) {

        super(message, cause);
        this.canIgnore = canIgnore;
    }

    /**
     * Constructs a new decode exception with the specified cause and whether it is safe to ignore.
     *
     * @param cause The cause.
     * @param canIgnore whether it is safe to ignore the exception.
     * @since 1.0.0
     */
    @Contract(pure = true)
    public DecodeException(final @Nullable Throwable cause, final boolean canIgnore) {

        super(cause);
        this.canIgnore = canIgnore;
    }

    @Contract(pure = true)
    public boolean isIgnorable() {

        return canIgnore;
    }
}
