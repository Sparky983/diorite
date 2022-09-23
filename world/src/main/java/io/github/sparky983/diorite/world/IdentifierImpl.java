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

import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import io.github.sparky983.diorite.util.Preconditions;

final class IdentifierImpl implements Identifier {

    private static final Pattern COMPILED_NAMESPACE_PATTERN = Pattern.compile(NAMESPACE_PATTERN);
    private static final Pattern COMPILED_VALUE_PATTERN = Pattern.compile(VALUE_PATTERN);

    @Subst(DEFAULT_NAMESPACE)
    private final String namespace;
    @Subst("zombie")
    private final String value;

    IdentifierImpl(final @org.intellij.lang.annotations.Pattern(NAMESPACE_PATTERN) @NotNull String namespace,
                   final @org.intellij.lang.annotations.Pattern(VALUE_PATTERN) @NotNull String value) {

        Preconditions.requireNotNull(namespace, "namespace");
        Preconditions.requireNotNull(value, "value");
        Preconditions.requireTrue(COMPILED_NAMESPACE_PATTERN.matcher(namespace).matches(),
                "[namespace] must match \"" + NAMESPACE_PATTERN + "\"");
        Preconditions.requireTrue(COMPILED_VALUE_PATTERN.matcher(value).matches(),
                "[value] must match \"" + VALUE_PATTERN + "\"");

        this.namespace = namespace;
        this.value = value;
    }

    @org.intellij.lang.annotations.Pattern(NAMESPACE_PATTERN)
    @Override
    public @NotNull String getNamespace() {

        return namespace;
    }

    @org.intellij.lang.annotations.Pattern(VALUE_PATTERN)
    @Override
    public @NotNull String getValue() {

        return value;
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof IdentifierImpl)) {
            return false;
        }

        final IdentifierImpl other = (IdentifierImpl) o;

        return namespace.equals(other.namespace) &&
                value.equals(other.value);
    }

    @Override
    public int hashCode() {

        return namespace.hashCode() + value.hashCode();
    }

    @Override
    public @NotNull String toString() {

        return namespace + ":" + value;
    }
}
