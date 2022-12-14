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
import io.github.sparky983.diorite.net.packet.serverbound.play.PlayerDiggingPacket;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Position;

public final class AcknowledgePlayerDiggingPacket implements ClientBoundPacket {

    private final Position location;
    private final int block;
    private final PlayerDiggingPacket.Status status;
    private final boolean successful;

    @Contract(pure = true)
    public AcknowledgePlayerDiggingPacket(final @NotNull Position location,
            final int block,
            final PlayerDiggingPacket.@NotNull Status status,
            final boolean successful) {

        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(status, "status");

        this.location = location;
        this.block = block;
        this.status = status;
        this.successful = successful;
    }

    @Contract(mutates = "param")
    public AcknowledgePlayerDiggingPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.location = inputStream.readPosition();
        this.block = inputStream.readVarInt();
        this.status = inputStream.readVarIntEnum(PlayerDiggingPacket.Status.class);
        this.successful = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writePosition(location)
                .writeVarInt(block)
                .writeVarIntEnum(status)
                .writeBoolean(successful);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.ACKNOWLEDGE_PLAYER_DIGGING;
    }

    @Contract(pure = true)
    public @NotNull Position getLocation() {

        return location;
    }

    @Contract(pure = true)
    public int getBlock() {

        return block;
    }

    @Contract(pure = true)
    public PlayerDiggingPacket.@NotNull Status getStatus() {

        return status;
    }

    @Contract(pure = true)
    public boolean isSuccessful() {

        return successful;
    }
}
