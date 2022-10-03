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
import io.github.sparky983.diorite.world.Position;

public final class PlayerPositionAndLookPacket implements ClientBoundPacket {

    private final byte X_IS_RELATIVE_BIT = 0b00000001;
    private final byte Y_IS_RELATIVE_BIT = 0b00000010;
    private final byte Z_IS_RELATIVE_BIT = 0b00000100;
    private final byte Y_ROT_IS_RELATIVE_BIT = 0b00001000;
    private final byte X_ROT_IS_RELATIVE_BIT = 0b00010000;

    private final Position location;
    private final float yaw;
    private final float pitch;
    private final byte flags;
    private final int teleportId;
    private final boolean dismountVehicle;

    @Contract(pure = true)
    public PlayerPositionAndLookPacket(final @NotNull Position location,
                                       final float yaw,
                                       final float pitch,
                                       final byte flags,
                                       final int teleportId,
                                       final boolean dismountVehicle) {

        Preconditions.requireNotNull(location, "location");

        this.location = location;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = flags;
        this.teleportId = teleportId;
        this.dismountVehicle = dismountVehicle;
    }

    @Contract(mutates = "param")
    public PlayerPositionAndLookPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.location = inputStream.readPosition();
        this.yaw = inputStream.readFloat();
        this.pitch = inputStream.readFloat();
        this.flags = inputStream.readByte();
        this.teleportId = inputStream.readVarInt();
        this.dismountVehicle = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writePosition(location)
                .writeFloat(yaw)
                .writeFloat(pitch)
                .writeByte(flags)
                .writeVarInt(teleportId)
                .writeBoolean(dismountVehicle);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.PLAYER_POSITION_AND_LOOK;
    }

    @Contract(pure = true)
    public @NotNull Position getLocation() {

        return location;
    }

    @Contract(pure = true)
    public float getYaw() {

        return yaw;
    }

    @Contract(pure = true)
    public float getPitch() {

        return pitch;
    }

    @Contract(pure = true)
    public byte getFlags() {

        return flags;
    }

    @Contract(pure = true)
    public boolean isXRelative() {

        return (flags & X_IS_RELATIVE_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isYRelative() {

        return (flags & Y_IS_RELATIVE_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isZRelative() {

        return (flags & Z_IS_RELATIVE_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isYRotRelative() {

        return (flags & Y_ROT_IS_RELATIVE_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isXRotRelative() {

        return (flags & X_ROT_IS_RELATIVE_BIT) != 0;
    }

    @Contract(pure = true)
    public int getTeleportId() {

        return teleportId;
    }

    @Contract(pure = true)
    public boolean isDismountVehicle() {

        return dismountVehicle;
    }
}
