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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.util.Preconditions;

final class DimensionImpl implements Dimension {

    private final Identifier identifier;

    @Contract(pure = true)
    public DimensionImpl(final @NotNull Identifier identifier) {

        Preconditions.requireNotNull(identifier, "identifier");

        this.identifier = identifier;
    }

    @Override
    public @NotNull Identifier getIdentifier() {

        return identifier;
    }

    @Override
    public @NotNull String getName() {

        return identifier.getValue();
    }

    @Override
    public String toString() {

        return "DimensionImpl(identifier=" + identifier + ")";
    }
}
