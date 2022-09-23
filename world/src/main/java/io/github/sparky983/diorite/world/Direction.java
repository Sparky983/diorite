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
 * A pitch and yaw.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface Direction {

    /**
     * Creates a new direction with the specified pitch, and yaw.
     *
     * @param pitch The pitch.
     * @param yaw The yaw.
     * @return The newly created direction.
     * @since 1.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull Direction of(final byte pitch, final byte yaw) {

        return new DirectionImpl(pitch, yaw);
    }

    /**
     * Returns the pitch.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    byte getPitch();

    /**
     * Returns the yaw.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    byte getYaw();
}
