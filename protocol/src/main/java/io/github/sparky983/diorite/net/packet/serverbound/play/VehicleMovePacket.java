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

public final class VehicleMovePacket implements ServerBoundPacket {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    @Contract(pure = true)
    public VehicleMovePacket(final double x,
            final double y,
            final double z,
            final float yaw,
            final float pitch) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Contract(mutates = "param")
    public VehicleMovePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.x = inputStream.readDouble();
        this.y = inputStream.readDouble();
        this.z = inputStream.readDouble();
        this.yaw = inputStream.readFloat();
        this.pitch = inputStream.readFloat();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeDouble(x)
                .writeDouble(y)
                .writeDouble(z)
                .writeFloat(yaw)
                .writeFloat(pitch);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.VEHICLE_MOVE;
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
    public double getZ() {

        return z;
    }

    @Contract(pure = true)
    public float getYaw() {

        return yaw;
    }

    @Contract(pure = true)
    public float getPitch() {

        return pitch;
    }
}
