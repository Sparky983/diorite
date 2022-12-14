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

final class VelocityImpl implements Velocity {

    private final short x;
    private final short y;
    private final short z;

    @Contract(pure = true)
    VelocityImpl(final short x, final short y, final short z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public short getX() {

        return x;
    }

    @Override
    public short getY() {

        return y;
    }

    @Override
    public short getZ() {

        return z;
    }
}
