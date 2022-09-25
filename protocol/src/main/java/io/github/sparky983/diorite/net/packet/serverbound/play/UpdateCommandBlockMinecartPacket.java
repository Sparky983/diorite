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
import io.github.sparky983.diorite.util.Protocol;

public class UpdateCommandBlockMinecartPacket implements ServerBoundPacket {

    private final int entityId;
    private final String command;
    private final boolean trackOutput;

    @Contract(pure = true)
    public UpdateCommandBlockMinecartPacket(final int entityId,
                                            final @NotNull String command,
                                            final boolean trackOutput) {

        Preconditions.requireNotNull(command, "command");
        Preconditions.requireRange(command.length(), 0, Protocol.MAX_STRING_LENGTH, "command.length()");

        this.entityId = entityId;
        this.command = command;
        this.trackOutput = trackOutput;
    }

    @Contract(mutates = "param")
    public UpdateCommandBlockMinecartPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.command = inputStream.readString();
        this.trackOutput = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeString(command)
                .writeBoolean(trackOutput);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.UPDATE_COMMAND_BLOCK_MINECART;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull String getCommand() {

        return command;
    }

    @Contract(pure = true)
    public boolean isTrackOutput() {

        return trackOutput;
    }
}
