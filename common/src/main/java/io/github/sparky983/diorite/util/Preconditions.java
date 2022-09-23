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

package io.github.sparky983.diorite.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for checking preconditions.
 *
 * @since 1.0.0
 */
public final class Preconditions {

    private Preconditions() {

    }

    /**
     * Checks that the provided argument is valid.
     *
     * @param isValid Whether the parameter is valid.
     * @throws IllegalArgumentException if {@code isValid} is {@code false}.
     * @since 1.0.0
     */
    @Contract(value = "false -> fail", pure = true)
    public static void requireTrue(final boolean isValid) {

        requireTrue(isValid, null);
    }

    /**
     * Checks that the provided argument is valid.
     *
     * @param isValid Whether the parameter is valid.
     * @param message The error message.
     * @throws IllegalArgumentException if {@code isValid} is {@code false}.
     * @since 1.0.0
     */
    @Contract(value = "false, _ -> fail", pure = true)
    public static void requireTrue(final boolean isValid, final @Nullable String message) {

        if (!isValid) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks that the provided argument is in the specified range.
     *
     * @param n The argument.
     * @param from The minimum number (inclusive).
     * @param to The maximum number (inclusive).
     * @throws IllegalArgumentException if the number is not in range.
     * @since 1.0.0
     */
    @Contract(pure = true)
    public static void requireRange(final long n, final long from, final long to) {

        if (n < from || n > to) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that the provided argument is in the specified range.
     *
     * @param n The argument.
     * @param from The minimum number (inclusive).
     * @param to The maximum number (inclusive).
     * @throws IllegalArgumentException if the number is not in range.
     * @since 1.0.0
     */
    @Contract(pure = true)
    public static void requireRange(final long n,
                                    final long from,
                                    final long to,
                                    final @Nullable String parameterName) {

        if (n < from || n > to) {
            throw new IllegalArgumentException("[" + parameterName + "] must be in range " + from + "-" + to + ", but was: " + n);
        }
    }

    /**
     * Checks that the provided argument is not {@code null}.
     *
     * @param o The argument.
     * @throws NullPointerException if {@code t} is {@code null}.
     * @see #requireNotNull(Object, String)
     * @since 1.0.0
     */
    @Contract(value = "null -> fail", pure = true)
    public static void requireNotNull(final @Nullable Object o) {

        requireNotNull(o, null);
    }

    /**
     * Checks that the provided argument is not {@code null}.
     *
     * @param o The argument.
     * @param parameterName The parameter name of the argument that is being checked. This
     *         may be used to construct an error message.
     * @throws NullPointerException if {@code t} is {@code null}.
     * @see #requireNotNull(Object)
     * @since 1.0.0
     */
    @Contract(value = "null, _ -> fail", pure = true)
    public static void requireNotNull(
            final @Nullable Object o, final @Nullable String parameterName) {

        if (o == null) {
            throw new NullPointerException("[" + parameterName + "] cannot be null");
        }
    }

    /**
     * Checks that the provided argument contains no {@code null} values.
     *
     * @param array The argument.
     * @param parameterName The parameter name of the argument that is being checked. This
     *         may be used to construct an error message.
     * @throws NullPointerException if any elements of {@code array} or the {@code array}
     *         itself are {@code null}.
     * @since 1.0.0
     */
    @Contract(value = "null, _ -> fail")
    public static void requireContainsNoNulls(
            final @Nullable Object @Nullable [] array, final @Nullable String parameterName) {

        Preconditions.requireNotNull(array, parameterName);

        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new NullPointerException(parameterName + "[" + i + "] cannot be null");
            }
        }
    }

    /**
     * Checks that the provided argument contains no {@code null} values.
     *
     * @param array The argument.
     * @throws NullPointerException if any elements of {@code array} or the {@code array}
     *         itself are {@code null}.
     * @since 1.0.0
     */
    @Contract(value = "null -> fail")
    public static void requireContainsNoNulls(final @Nullable Object @Nullable [] array) {

        requireContainsNoNulls(array, null);
    }

    /**
     * Checks that the provided argument contains no {@code null} values.
     *
     * @param iter The argument.
     * @param parameterName The parameter name of the argument that is being checked. This
     *         may be used to construct an error message.
     * @throws NullPointerException if any elements of {@code array} or the {@code array}
     *         itself are {@code null}.
     * @since 1.0.0
     */
    @Contract(value = "null, _ -> fail")
    public static void requireContainsNoNulls(
            final @Nullable Iterable<?> iter, final @Nullable String parameterName) {

        Preconditions.requireNotNull(iter, parameterName);

        int i = 0;
        for (final Object t : iter) {
            if (t == null) {
                throw new NullPointerException(parameterName + "[" + i + "] cannot be null");
            }
            i++;
        }
    }

    /**
     * Checks that the provided argument contains no {@code null} values.
     *
     * @param iter The argument.
     * @throws NullPointerException if any elements of {@code array} or the {@code array}
     *         itself are {@code null}.
     * @since 1.0.0
     */
    @Contract(value = "null -> fail")
    public static void requireContainsNoNulls(
            final @Nullable Iterable<?> iter) {

        requireContainsNoNulls(iter, null);
    }
}
