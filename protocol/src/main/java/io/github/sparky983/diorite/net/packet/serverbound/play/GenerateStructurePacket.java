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

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.BlockPosition;

public class GenerateStructurePacket implements ServerBoundPacket {

    private final BlockPosition location;
    private final int levels;
    private final boolean keepJigsaws;

    @Contract(pure = true)
    public GenerateStructurePacket(final @NotNull BlockPosition location,
                                   final int levels,
                                   final boolean keepJigsaws) {

        Preconditions.requireNotNull(location, "location");

        this.location = location;
        this.levels = levels;
        this.keepJigsaws = keepJigsaws;
    }

    @Contract(mutates = "param")
    public GenerateStructurePacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.location = inputStream.readBlockPosition();
        this.levels = inputStream.readVarInt();
        this.keepJigsaws = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeBlockPosition(location)
                .writeVarInt(levels)
                .writeBoolean(keepJigsaws);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.GENERATE_STRUCTURE;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }

    @Contract(pure = true)
    public int getLevels() {

        return levels;
    }

    @Contract(pure = true)
    public boolean isKeepJigsaws() {

        return keepJigsaws;
    }
}
