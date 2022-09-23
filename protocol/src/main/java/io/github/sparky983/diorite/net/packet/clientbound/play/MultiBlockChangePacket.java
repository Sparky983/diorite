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

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

@ApiStatus.Experimental
public class MultiBlockChangePacket implements ClientBoundPacket {

    // TODO(Sparky983): do better decoding of chunk section position and blocks
    private final long chunkSectionPosition;
    private final boolean suppressLightUpdates;
    private final long[] blocks;

    @Contract(pure = true)
    public MultiBlockChangePacket(final long chunkSectionPosition,
                                  final boolean suppressLightUpdates,
                                  final long @NotNull [] blocks) {

        Preconditions.requireNotNull(blocks, "blocks");

        this.chunkSectionPosition = chunkSectionPosition;
        this.suppressLightUpdates = suppressLightUpdates;
        this.blocks = blocks; // TODO(Sparky983): consider cloning array
    }

    @Contract(mutates = "param")
    public MultiBlockChangePacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.chunkSectionPosition = inputStream.readLong();
        this.suppressLightUpdates = inputStream.readBoolean();

        final int blocksLength = inputStream.readVarInt();
        this.blocks = new long[blocksLength];
        for (int i = 0; i < blocksLength; i++) {
            this.blocks[i] = inputStream.readLong();
        }
    }

    @Override
    public void write(@NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeLong(chunkSectionPosition)
                .writeBoolean(suppressLightUpdates)
                .writeVarInt(blocks.length);

        for (final long block : blocks) {
            outputStream.writeLong(block);
        }
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.MULTI_BLOCK_CHANGE;
    }

    @Contract(pure = true)
    public long getChunkSectionPosition() {

        return chunkSectionPosition;
    }

    @Contract(pure = true)
    public boolean isSuppressLightUpdates() {

        return suppressLightUpdates;
    }

    @Contract(pure = true)
    public long @NotNull [] getBlocks() {

        // TODO(Sparky983): consider returning a clone
        return blocks;
    }
}
