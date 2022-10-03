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

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class InitializeWorldBorderPacket implements ClientBoundPacket {

    private final double x;
    private final double y;
    private final double previousDiameter;
    private final double newDiameter;
    private final long speed;
    private final int portalTeleportBoundary;
    private final int warningBlocks;
    private final int warningTime;

    @Contract(pure = true)
    public InitializeWorldBorderPacket(final double x,
                                       final double y,
                                       final double previousDiameter,
                                       final double newDiameter,
                                       final long speed,
                                       final int portalTeleportBoundary,
                                       final int warningBlocks,
                                       final int warningTime) {

        this.x = x;
        this.y = y;
        this.previousDiameter = previousDiameter;
        this.newDiameter = newDiameter;
        this.speed = speed;
        this.portalTeleportBoundary = portalTeleportBoundary;
        this.warningBlocks = warningBlocks;
        this.warningTime = warningTime;
    }

    @Contract(mutates = "param")
    public InitializeWorldBorderPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.x = inputStream.readDouble();
        this.y = inputStream.readDouble();
        this.previousDiameter = inputStream.readDouble();
        this.newDiameter = inputStream.readDouble();
        this.speed = inputStream.readVarLong();
        this.portalTeleportBoundary = inputStream.readVarInt();
        this.warningBlocks = inputStream.readVarInt();
        this.warningTime = inputStream.readVarInt();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeDouble(x)
                .writeDouble(y)
                .writeDouble(previousDiameter)
                .writeDouble(newDiameter)
                .writeVarLong(speed)
                .writeVarInt(portalTeleportBoundary)
                .writeVarInt(warningBlocks)
                .writeVarInt(warningTime);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.INITIALIZE_WORLD_BORDER;
    }

    @Contract(pure = true)
    public double getX() {

        return x;
    }

    @Contract(pure = true)
    public double getY() {

        return y;
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

    @Contract(pure = true)
    public int getPortalTeleportBoundary() {

        return portalTeleportBoundary;
    }

    @Contract(pure = true)
    public int getWarningBlocks() {

        return warningBlocks;
    }

    @Contract(pure = true)
    public int getWarningTime() {

        return warningTime;
    }
}
