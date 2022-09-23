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

import net.kyori.adventure.nbt.CompoundBinaryTag;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Gamemode;
import io.github.sparky983.diorite.world.Identifier;

public class RespawnPacket implements ClientBoundPacket {

    private final CompoundBinaryTag dimension;
    private final Identifier worldName;
    private final long hashedSeed;
    private final Gamemode gamemode;
    private final Gamemode previousGamemode;
    private final boolean isDebug;
    private final boolean isFlag;
    private final boolean copyMetadata;

    @Contract(pure = true)
    public RespawnPacket(final @NotNull CompoundBinaryTag dimension,
                         final @NotNull Identifier worldName,
                         final long hashedSeed,
                         final @NotNull Gamemode gamemode,
                         final @NotNull Gamemode previousGamemode,
                         final boolean isDebug,
                         final boolean isFlag,
                         final boolean copyMetadata) {

        Preconditions.requireNotNull(dimension, "dimension");
        Preconditions.requireNotNull(worldName, "worldName");
        Preconditions.requireNotNull(gamemode, "gamemode");
        Preconditions.requireNotNull(previousGamemode, "previousGamemode");

        this.dimension = dimension;
        this.worldName = worldName;
        this.hashedSeed = hashedSeed;
        this.gamemode = gamemode;
        this.previousGamemode = previousGamemode;
        this.isDebug = isDebug;
        this.isFlag = isFlag;
        this.copyMetadata = copyMetadata;
    }

    @Contract(mutates = "param")
    public RespawnPacket(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.dimension = inputStream.readNbtCompound();
            this.worldName = inputStream.readIdentifier();
            this.hashedSeed = inputStream.readLong();
            this.gamemode = inputStream.readUByteEnum(Gamemode.class);
            this.previousGamemode = inputStream.readUByteEnum(Gamemode.class);
            this.isDebug = inputStream.readBoolean();
            this.isFlag = inputStream.readBoolean();
            this.copyMetadata = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeNbtCompound(dimension)
                .writeIdentifier(worldName)
                .writeLong(hashedSeed)
                .writeUByteEnum(gamemode)
                .writeUByteEnum(previousGamemode)
                .writeBoolean(isDebug)
                .writeBoolean(isFlag)
                .writeBoolean(copyMetadata);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.RESPAWN;
    }

    @Contract(pure = true)
    @NotNull public CompoundBinaryTag getDimension() {

        return dimension;
    }

    @Contract(pure = true)
    public @NotNull Identifier getWorldName() {

        return worldName;
    }

    @Contract(pure = true)
    public long getHashedSeed() {

        return hashedSeed;
    }

    @Contract(pure = true)
    public @NotNull Gamemode getGamemode() {

        return gamemode;
    }

    @Contract(pure = true)
    public @NotNull Gamemode getPreviousGamemode() {

        return previousGamemode;
    }

    @Contract(pure = true)
    public boolean isDebug() {

        return isDebug;
    }

    @Contract(pure = true)
    public boolean isFlag() {

        return isFlag;
    }

    @Contract(pure = true)
    public boolean isCopyMetadata() {

        return copyMetadata;
    }
}
