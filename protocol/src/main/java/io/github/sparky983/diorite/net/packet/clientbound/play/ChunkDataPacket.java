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

import java.util.List;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class ChunkDataPacket implements ClientBoundPacket {

    private final int chunkX;
    private final int chunkZ;
    private final long[] chunkMask;
    private final CompoundBinaryTag heightmaps;
    private final int[] biomes;
    private final byte[] data;
    private final List<CompoundBinaryTag> blockEntities;

    @Contract(pure = true)
    public ChunkDataPacket(final int chunkX,
                           final int chunkZ,
                           final long @NotNull [] chunkMask,
                           final @NotNull CompoundBinaryTag heightMaps,
                           final int @NotNull [] biomes,
                           final byte @NotNull [] data,
                           final @NotNull List<@NotNull CompoundBinaryTag> blockEntities) {

        Preconditions.requireNotNull(chunkMask, "chunkMask");
        Preconditions.requireNotNull(heightMaps, "heightMaps");
        Preconditions.requireNotNull(biomes, "biomes");
        Preconditions.requireNotNull(data, "data");
        Preconditions.requireContainsNoNulls(blockEntities, "blockEntities");

        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.chunkMask = chunkMask;
        this.heightmaps = heightMaps;
        this.biomes = biomes;
        this.data = data;
        this.blockEntities = List.copyOf(blockEntities);
    }

    @Contract(mutates = "param")
    public ChunkDataPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.chunkX = inputStream.readInt();
        this.chunkZ = inputStream.readInt();
        this.chunkMask = inputStream.readLengthPrefixedLongs();
        this.heightmaps = inputStream.readNbtCompound();
        this.biomes = inputStream.readLengthPrefixedVarInts();
        this.data = inputStream.readLengthPrefixedBytes();
        this.blockEntities = inputStream.readList(MinecraftInputStream::readNbtCompound);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeInt(chunkX)
                .writeInt(chunkZ)
                .writeLengthPrefixedVarLongs(chunkMask)
                .writeNbtCompound(heightmaps)
                .writeLengthPrefixedVarInts(biomes)
                .writeLengthPrefixedBytes(data)
                .writeList(blockEntities, MinecraftOutputStream::writeNbtCompound);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.CHUNK_DATA;
    }

    @Contract(pure = true)
    public int getChunkX() {

        return chunkX;
    }

    @Contract(pure = true)
    public int getChunkZ() {

        return chunkZ;
    }

    @Contract(pure = true)
    public long @NotNull [] getChunkMask() {

        return chunkMask;
    }

    @Contract(pure = true)
    public @NotNull CompoundBinaryTag getHeightmaps() {

        return heightmaps;
    }

    @Contract(pure = true)
    public int @NotNull [] getBiomes() {

        return biomes;
    }

    @Contract(pure = true)
    public byte[] getData() {

        return data;
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull CompoundBinaryTag> getBlockEntities() {

        return blockEntities;
    }
}
