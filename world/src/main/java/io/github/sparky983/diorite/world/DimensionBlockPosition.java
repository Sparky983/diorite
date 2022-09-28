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

/**
 * A position inside a world.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface DimensionBlockPosition extends BlockPosition {

    /**
     * Creates a new position with the specified dimension, x, y and z.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @return The created position.
     * @throws NullPointerException if dimension is {@code null}.
     * @since 1.0.0
     */
    @Contract(pure = true)
    static @NotNull DimensionBlockPosition of(final @NotNull Dimension dimension,
                                              final int x,
                                              final int y,
                                              final int z) {

        return new DimensionBlockPositionImpl(dimension, x, y, z);
    }

    /**
     * Creates a new blockPosition with the specified dimension and blockPosition.
     *
     * @param dimension The dimension
     * @param blockPosition The blockPosition
     * @return The created blockPosition.
     * @throws NullPointerException if dimension or blockPosition are {@code null}.
     * @since 1.0.0
     */
    @Contract(pure = true)
    static @NotNull DimensionBlockPosition of(final @NotNull Dimension dimension,
                                              final @NotNull BlockPosition blockPosition) {

        Preconditions.requireNotNull(blockPosition, "blockPosition");

        return of(dimension, blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }

    /**
     * Returns the dimension of the position.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull Dimension getDimension();
}
