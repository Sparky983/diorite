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
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.BlockPosition;

public class UpdateSignPacket implements ServerBoundPacket {

    private static final int MAX_LINE_LENGTH = 384;

    private final BlockPosition location;
    private final List<String> lines;

    @Contract(pure = true)
    public UpdateSignPacket(final @NotNull BlockPosition location,
                            final @NotNull String line1,
                            final @NotNull String line2,
                            final @NotNull String line3,
                            final @NotNull String line4) {

        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(line1, "line1");
        Preconditions.requireNotNull(line2, "line2");
        Preconditions.requireNotNull(line3, "line3");
        Preconditions.requireNotNull(line4, "line4");

        this.location = location;
        this.lines = List.of(line1, line2, line3, line4);
    }

    @Contract(mutates = "param")
    public UpdateSignPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.location = inputStream.readBlockPosition();
        this.lines = List.of(
                inputStream.readString(MAX_LINE_LENGTH),
                inputStream.readString(MAX_LINE_LENGTH),
                inputStream.readString(MAX_LINE_LENGTH),
                inputStream.readString(MAX_LINE_LENGTH)
        );
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeBlockPosition(location)
                .writeString(getLine(0))
                .writeString(getLine(1))
                .writeString(getLine(2))
                .writeString(getLine(3));
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.UPDATE_SIGN;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @Unmodifiable @NotNull List<String> getLines() {

        return lines;
    }

    @Contract(pure = true)
    public @NotNull String getLine(final @Range(from = 0, to = 3) int line) {

        Preconditions.requireRange(line, 0, 3, "line");

        return lines.get(line);
    }
}
