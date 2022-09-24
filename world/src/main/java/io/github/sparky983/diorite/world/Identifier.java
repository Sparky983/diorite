/*
 * Copyright 2022 Sparky
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

package io.github.sparky983.diorite.world;

import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.util.Preconditions;

/**
 * Represents an identifier, a namespace and a value.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface Identifier {

    // https://www.minecraft.net/en-us/article/minecraft-snapshot-17w43a

    /**
     * The pattern all namespaces must follow.
     *
     * @since 1.0.0
     */
    @Language("RegExp")
    String NAMESPACE_PATTERN = "[\\da-z_-]+";

    /**
     * The pattern all values must follow.
     */
    @Language("RegExp")
    String VALUE_PATTERN = "[\\da-z_/.-]+";

    /**
     * The default namespace. This is used if a namespace is not specified in an identifier.
     */
    @Pattern(NAMESPACE_PATTERN)
    String DEFAULT_NAMESPACE = "minecraft";

    /**
     * Creates a new identifier with the specified namespace and value.
     *
     * @param namespace The namespace.
     * @param value The value.
     * @return The created identifier.
     * @throws NullPointerException if namespace or value are {@code null}.
     * @throws IllegalArgumentException if namespace doesn't match
     *         {@link #NAMESPACE_PATTERN} or value doesn't match {@link #VALUE_PATTERN}.
     * @since 1.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static Identifier of(
            @Subst(DEFAULT_NAMESPACE) final
            @Pattern(NAMESPACE_PATTERN)
            @NotNull
            String namespace,
            @Subst("zombie") final
            @Pattern(VALUE_PATTERN)
            @NotNull
            String value) {

        return new IdentifierImpl(namespace, value);
    }

    /**
     * Creates a new identifier with the {@link #DEFAULT_NAMESPACE} and the specified value.
     *
     * @param value The value.
     * @return The created identifier.
     * @throws NullPointerException if value is {@code null}.
     * @throws IllegalArgumentException if value doesn't match {@link #VALUE_PATTERN}.
     * @since 1.0..0
     */
    @Contract(value = "_ -> new", pure = true)
    static Identifier of(final @Subst(DEFAULT_NAMESPACE) @NotNull String value) {

        return of(DEFAULT_NAMESPACE, value);
    }

    /**
     * Parses the identifier string.
     *
     * @param identifierString The input to parse.
     * @return The parsed identifier.
     * @throws IllegalArgumentException if the identifier string is not valid.
     * @since 1.0.0
     */
    @SuppressWarnings("PatternValidation") // TODO(Sparky983): IJ bug?
    @Contract(value = "_ -> new", pure = true)
    static Identifier parse(
            @Subst(DEFAULT_NAMESPACE + ":zombie") final @NotNull String identifierString) {

        Preconditions.requireNotNull(identifierString, "identifierString");

        final int colonPosition = identifierString.indexOf(':');

        if (colonPosition == -1) {
            // ":" is not in string
            return of(DEFAULT_NAMESPACE, identifierString);
        }

        return of(identifierString.substring(0, colonPosition),
                identifierString.substring(colonPosition + 1));
    }

    /**
     * Returns the namespace.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    @Pattern(NAMESPACE_PATTERN)
    @NotNull
    String getNamespace();

    /**
     * Returns the value.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    @Pattern(VALUE_PATTERN)
    @NotNull
    String getValue();

    /**
     * Returns a string representation of the identifier that is parsable by
     * {@link #parse(String)}.
     *
     * @since 1.0.0
     */
    @NotNull
    String toString();
}
