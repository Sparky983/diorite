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

/**
 * Represents a block position with no corresponding dimension.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface BlockPosition {

    /**
     * Creates a new block position with the specified x, y and z.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @return The newly created position.
     * @since 1.0.0
     */
    @Contract(pure = true)
    static BlockPosition of(final int x, final int y, final int z) {

        return new BlockPositionImpl(x, y, z);
    }

    /**
     * Returns the x coordinate.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    int getX();

    /**
     * Returns the y coordinate.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    int getY();

    /**
     * Returns the z coordinate.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    int getZ();
}
