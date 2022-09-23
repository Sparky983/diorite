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

package io.github.sparky983.diorite.net.packet.clientbound.play;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class WorldBorderLerpSizePacket implements ClientBoundPacket {

    private final double previousDiameter;
    private final double newDiameter;
    private final long speed;

    @Contract(pure = true)
    public WorldBorderLerpSizePacket(final double previousDiameter, final double newDiameter, final long speed) {

        this.previousDiameter = previousDiameter;
        this.newDiameter = newDiameter;
        this.speed = speed;
    }

    @Contract(mutates = "param")
    public WorldBorderLerpSizePacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.previousDiameter = inputStream.readDouble();
        this.newDiameter = inputStream.readDouble();
        this.speed = inputStream.readVarLong();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeDouble(previousDiameter)
                .writeDouble(newDiameter)
                .writeVarLong(speed);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.WORLD_BORDER_LERP_SIZE;
    }

    @Contract(pure = true)
    public double getPreviousDiameter() {

        return previousDiameter;
    }

    @Contract(pure = true)
    public double getNewDiameter() {

        return newDiameter;
    }

    @Contract(pure = true)
    public long getSpeed() {

        return speed;
    }
}
