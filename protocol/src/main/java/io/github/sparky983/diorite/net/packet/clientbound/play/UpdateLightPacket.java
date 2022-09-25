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

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

@ApiStatus.Experimental
public class UpdateLightPacket implements ClientBoundPacket {

    private static final int LIGHT_ARRAY_LENGTH = 2048;

    private final int chunkX;
    private final int chunkZ;
    private final boolean trustEdges;
    private final long[] skyLightMask;
    private final long[] blockLightMask;
    private final long[] emptySkyLightMask;
    private final long[] emptyBlockLightMask;
    private final byte[][] skyLightArrays;
    private final byte[][] blockLightArrays;

    @Contract(pure = true)
    public UpdateLightPacket(final int chunkX,
                             final int chunkZ,
                             final boolean trustEdges,
                             final long @NotNull [] skyLightMask,
                             final long @NotNull [] blockLightMask,
                             final long @NotNull [] emptySkyLightMask,
                             final long @NotNull [] emptyBlockLightMask,
                             final byte @NotNull [] @NotNull [] skyLightArrays,
                             final byte @NotNull [] @NotNull [] blockLightArrays) {

        Preconditions.requireNotNull(skyLightMask, "skyLightMask");
        Preconditions.requireNotNull(blockLightMask, "blockLightMask");
        Preconditions.requireNotNull(emptySkyLightMask, "emptySkyLightMask");
        Preconditions.requireNotNull(emptyBlockLightMask, "emptyBlockLightMask");

        for (int i = 0; i < skyLightArrays.length; i++) {
            final byte[] skyLightArray = skyLightArrays[i];
            Preconditions.requireNotNull(skyLightArray, "skyLightArrays[" + i + "]");
            Preconditions.requireTrue(skyLightArray.length == LIGHT_ARRAY_LENGTH,
                                  "skyLightArrays[" + i + "] length must be " + LIGHT_ARRAY_LENGTH);
        }

        for (int i = 0; i < blockLightArrays.length; i++) {
            final byte[] blockLightArray = blockLightArrays[i];
            Preconditions.requireNotNull(blockLightArray, "blockLightArrays[" + i + "]");
            Preconditions.requireTrue(blockLightArray.length ==  LIGHT_ARRAY_LENGTH,
                    "blockLightArrays[" + i + "] length must be 2048");
        }

        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.trustEdges = trustEdges;
        this.skyLightMask = skyLightMask;
        this.blockLightMask = blockLightMask;
        this.emptySkyLightMask = emptySkyLightMask;
        this.emptyBlockLightMask = emptyBlockLightMask;
        this.skyLightArrays = skyLightArrays;
        this.blockLightArrays = blockLightArrays;
    }

    @Contract(mutates = "param")
    public UpdateLightPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.chunkX = inputStream.readVarInt();
        this.chunkZ = inputStream.readVarInt();
        this.trustEdges = inputStream.readBoolean();
        this.skyLightMask = inputStream.readLongList();
        this.blockLightMask = inputStream.readLongList();
        this.emptySkyLightMask = inputStream.readLongList();
        this.emptyBlockLightMask = inputStream.readLongList();

        // TODO(Sparky983): Create read array method

        final int skyLightArraysLength = inputStream.readVarInt();
        final byte[][] skyLightArrays = new byte[skyLightArraysLength][];
        for (int i = 0; i < skyLightArraysLength; i++) {
            final byte[] skyLightArray = inputStream.readByteList();
            if (skyLightArray.length != LIGHT_ARRAY_LENGTH) {
                throw new DecodeException("skyLightArrays[" + i + "] length must be " + LIGHT_ARRAY_LENGTH);
            }
            skyLightArrays[i] = skyLightArray;
        }
        this.skyLightArrays = skyLightArrays;

        final int blockLightArraysLength = inputStream.readVarInt();
        final byte[][] blockLightArrays = new byte[blockLightArraysLength][];
        for (int i = 0; i < blockLightArraysLength; i++) {
            final byte[] blockLightArray = inputStream.readByteList();
            if (blockLightArray.length != LIGHT_ARRAY_LENGTH) {
                throw new DecodeException("blockLightArrays[" + i + "] length must be 2048");
            }
            blockLightArrays[i] = blockLightArray;
        }
        this.blockLightArrays = blockLightArrays;

    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(this.chunkX)
                .writeVarInt(chunkZ)
                .writeBoolean(trustEdges)
                .writeLongs(skyLightMask)
                .writeLongs(blockLightMask)
                .writeLongs(emptySkyLightMask)
                .writeLongs(emptyBlockLightMask);

        outputStream.writeVarInt(skyLightArrays.length);
        for (final byte[] skyLightArray : skyLightArrays) {
            outputStream.writeByteList(skyLightArray);
        }

        outputStream.writeVarInt(blockLightArrays.length);
        for (final byte[] blockLightArray : blockLightArrays) {
            outputStream.writeByteList(blockLightArray);
        }
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.UPDATE_LIGHT;
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
    public boolean isTrustEdges() {

        return trustEdges;
    }

    @Contract(pure = true)
    public long @NotNull [] getSkyLightMask() {

        return skyLightMask;
    }

    @Contract(pure = true)
    public long @NotNull [] getBlockLightMask() {

        return blockLightMask;
    }

    @Contract(pure = true)
    public long @NotNull [] getEmptySkyLightMask() {

        return emptySkyLightMask;
    }

    @Contract(pure = true)
    public long @NotNull [] getEmptyBlockLightMask() {

        return emptyBlockLightMask;
    }

    @Contract(pure = true)
    public byte @NotNull [] @NotNull [] getSkyLightArrays() {

        return skyLightArrays;
    }

    @Contract(pure = true)
    public byte @NotNull [] @NotNull [] getBlockLightArrays() {

        return blockLightArrays;
    }
}
