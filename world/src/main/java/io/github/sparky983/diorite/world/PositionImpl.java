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

final class PositionImpl implements Position {

    private final double x;
    private final double y;
    private final double z;

    @Contract(pure = true)
    PositionImpl(final double x, final double y, final double z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double getX() {

        return x;
    }

    @Override
    public double getY() {

        return y;
    }

    @Override
    public double getZ() {

        return z;
    }
}
