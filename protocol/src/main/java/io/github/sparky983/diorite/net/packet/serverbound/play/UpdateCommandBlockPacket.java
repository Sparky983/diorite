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
import io.github.sparky983.diorite.world.BlockPosition;

public final class UpdateCommandBlockPacket implements ServerBoundPacket {

    private static final byte TRACK_OUTPUT_BIT = 0b00000001;
    private static final byte CONDITIONAL = 0b00000010;
    private static final byte AUTOMATIC = 0b00000100;

    private final BlockPosition location;
    private final String command;
    private final Mode mode;
    private final byte flags;

    @Contract(pure = true)
    public UpdateCommandBlockPacket(final @NotNull BlockPosition location,
            final @NotNull String command,
            final @NotNull Mode mode,
            final byte flags) {

        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(command, "command");
        Preconditions.requireRange(command.length(), 0, Protocol.MAX_STRING_LENGTH,
                "command.length()");
        Preconditions.requireNotNull(mode, "mode");

        this.location = location;
        this.command = command;
        this.mode = mode;
        this.flags = flags;
    }

    @Contract(mutates = "param")
    public UpdateCommandBlockPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.location = inputStream.readBlockPosition();
        this.command = inputStream.readString();
        this.mode = inputStream.readVarIntEnum(Mode.class);
        this.flags = inputStream.readByte();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeBlockPosition(location)
                .writeString(command)
                .writeVarIntEnum(mode)
                .writeByte(flags);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.UPDATE_COMMAND_BLOCK;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @NotNull String getCommand() {

        return command;
    }

    @Contract(pure = true)
    public @NotNull Mode getMode() {

        return mode;
    }

    @Contract(pure = true)
    public byte getFlags() {

        return flags;
    }

    @Contract(pure = true)
    public boolean isOutputTracked() {

        return (flags & TRACK_OUTPUT_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isConditional() {

        return (flags & CONDITIONAL) != 0;
    }

    @Contract(pure = true)
    public boolean isAutomatic() {

        return (flags & AUTOMATIC) != 0;
    }

    public enum Mode {

        SEQUENCE,
        AUTO,
        REDSTONE
    }
}
