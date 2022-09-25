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
import io.github.sparky983.diorite.world.BlockFace;
import io.github.sparky983.diorite.world.BlockPosition;

public class PlayerDiggingPacket implements ServerBoundPacket {

    private final Status status;
    private final BlockPosition location;
    private final BlockFace face;

    @Contract(pure = true)
    public PlayerDiggingPacket(final @NotNull Status status,
                               final @NotNull BlockPosition location,
                               final @NotNull BlockFace face) {

        Preconditions.requireNotNull(status, "status");
        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(face, "face");

        this.status = status;
        this.location = location;
        this.face = face;
    }

    @Contract(mutates = "param")
    public PlayerDiggingPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.status = inputStream.readVarIntEnum(Status.class);
        this.location = inputStream.readBlockPosition();
        this.face = inputStream.readByteEnum(BlockFace.class);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarIntEnum(status)
                .writeBlockPosition(location)
                .writeByteEnum(face);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.PLAYER_DIGGING;
    }

    @Contract(pure = true)
    public @NotNull Status getStatus() {

        return status;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @NotNull BlockFace getFace() {

        return face;
    }

    public enum Status {

        STARTED_DIGGING,
        CANCELLED_DIGGING,
        FINISHED_DIGGING,
        DROP_ITEM_STACK,
        DROP_ITEM,
        SHOOT_ARROW_FINISH_EATING,
        SWAP_ITEM_IN_HAND
    }
}
