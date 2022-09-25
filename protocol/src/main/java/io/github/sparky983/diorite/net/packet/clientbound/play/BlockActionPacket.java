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
import org.jetbrains.annotations.Range;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.BlockPosition;

public class BlockActionPacket implements ClientBoundPacket {

    private final BlockPosition location;
    private final int actionId;
    private final int actionParam;
    private final int blockType;

    @Contract(pure = true)
    public BlockActionPacket(final @NotNull BlockPosition location,
                             final @Range(from = 0x00, to = 0xFF) int actionId,
                             final @Range(from = 0x00, to = 0xFF) int actionParam,
                             final int blockType) {

        Preconditions.requireNotNull(location, "location");
        Preconditions.requireRange(actionId, 0x00, 0xFF, "actionId");
        Preconditions.requireRange(actionParam, 0x00, 0xFF, "actionParam");

        this.location = location;
        this.actionId = actionId;
        this.actionParam = actionParam;
        this.blockType = blockType;
    }

    @Contract(mutates = "param")
    public BlockActionPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.location = inputStream.readBlockPosition();
        this.actionId = inputStream.readUnsignedByte();
        this.actionParam = inputStream.readUnsignedByte();
        this.blockType = inputStream.readVarInt();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeBlockPosition(location)
                .writeUnsignedByte(actionId)
                .writeUnsignedByte(actionParam)
                .writeVarInt(blockType);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.BLOCK_ACTION;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @Range(from = 0x00, to = 0xFF) int getActionId() {

        return actionId;
    }

    @Contract(pure = true)
    public @Range(from = 0x00, to = 0xFF) int getActionParam() {

        return actionParam;
    }

    @Contract(pure = true)
    public int getBlockType() {

        return blockType;
    }
}
