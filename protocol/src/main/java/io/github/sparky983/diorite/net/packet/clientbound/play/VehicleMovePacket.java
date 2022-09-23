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
import io.github.sparky983.diorite.world.Position;

public class VehicleMovePacket implements ClientBoundPacket {

    private final Position location;
    private final float yaw;
    private final float pitch;

    @Contract(pure = true)
    public VehicleMovePacket(final @NotNull Position location, final float yaw, final float pitch) {

        Preconditions.requireNotNull(location, "location");

        this.location = location;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Contract(mutates = "param")
    public VehicleMovePacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.location = inputStream.readPosition();
        this.yaw = inputStream.readFloat();
        this.pitch = inputStream.readFloat();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writePosition(location)
                .writeFloat(yaw)
                .writeFloat(pitch);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.VEHICLE_MOVE;
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
}
