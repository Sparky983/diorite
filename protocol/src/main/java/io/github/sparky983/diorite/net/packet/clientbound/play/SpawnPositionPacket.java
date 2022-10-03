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
import io.github.sparky983.diorite.world.BlockPosition;

public final class SpawnPositionPacket implements ClientBoundPacket {

    private final BlockPosition spawnLocation;
    private final byte angle;

    @Contract(pure = true)
    public SpawnPositionPacket(final @NotNull BlockPosition spawnLocation, final byte angle) {

        Preconditions.requireNotNull(spawnLocation, "spawnLocation");

        this.spawnLocation = spawnLocation;
        this.angle = angle;
    }

    @Contract(mutates = "param")
    public SpawnPositionPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.spawnLocation = inputStream.readBlockPosition();
        this.angle = inputStream.readByte();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeBlockPosition(spawnLocation)
                .writeByte(angle);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SPAWN_POSITION;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getSpawnLocation() {

        return spawnLocation;
    }

    @Contract(pure = true)
    public byte getAngle() {

        return angle;
    }
}
