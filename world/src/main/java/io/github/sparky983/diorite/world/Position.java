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

/**
 * Represents an entity position.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface Position {

    /**
     * Creates a new position with the specified x, y, and z.
     *
     * @param x The x.
     * @param y The y.
     * @param z The z.
     * @return The newly created position.
     * @since 1.0.0
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    static @NotNull Position of(final double x, final double y, final double z) {

        return new PositionImpl(x, y, z);
    }

    /**
     * Returns the x.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    double getX();

    /**
     * Returns the y.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    double getY();

    /**
     * Returns the z.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    double getZ();
}
