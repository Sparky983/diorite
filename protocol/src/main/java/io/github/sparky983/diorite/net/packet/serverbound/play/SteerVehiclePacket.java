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

package io.github.sparky983.diorite.net.packet.serverbound.play;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class SteerVehiclePacket implements ServerBoundPacket {

    private static final byte JUMP_BIT = 0b00000001;
    private static final byte UNMOUNT_BIT = 0b00000010;

    private final float sideways;
    private final float forwards;
    private final byte flags;

    @Contract(pure = true)
    public SteerVehiclePacket(final float sideways, final float forwards, final byte flags) {

        this.sideways = sideways;
        this.forwards = forwards;
        this.flags = flags;
    }

    @Contract(mutates = "param")
    public SteerVehiclePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.sideways = inputStream.readFloat();
        this.forwards = inputStream.readFloat();
        this.flags = inputStream.readByte();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeFloat(sideways)
                .writeFloat(forwards)
                .writeByte(flags);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.STEER_VEHICLE;
    }

    @Contract(pure = true)
    public float getSideways() {

        return sideways;
    }

    @Contract(pure = true)
    public float getForwards() {

        return forwards;
    }

    @Contract(pure = true)
    public byte getFlags() {

        return flags;
    }
}
