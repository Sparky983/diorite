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

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.BlockPosition;

public class BlockEntityDataPacket implements ClientBoundPacket {

    private final BlockPosition location;
    private final Action action;
    private final CompoundBinaryTag nbt;

    @Contract(pure = true)
    public BlockEntityDataPacket(final @NotNull BlockPosition location,
                                 final @NotNull Action action,
                                 final @NotNull CompoundBinaryTag nbt) {

        this.location = location;
        this.action = action;
        this.nbt = nbt;
    }

    @Contract(mutates = "param")
    public BlockEntityDataPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.location = inputStream.readBlockPosition();
        this.action = inputStream.readUnsignedByteEnum(Action.class);
        this.nbt = inputStream.readCompoundTag();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeBlockPosition(this.location);
        outputStream.writeUnsignedByteEnum(this.action);
        outputStream.writeCompoundTag(this.nbt);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.BLOCK_ENTITY_DATA;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @NotNull Action getAction() {

        return action;
    }

    @Contract(pure = true)
    public @NotNull CompoundBinaryTag getNbt() {

        return nbt;
    }

    public enum Action {

        UNUSED,
        SET_MOB_SPAWNER_DATA,
        SET_COMMAND_BLOCK_DATA,
        SET_BEACON_DATA,
        SET_MOB_HEAD_DATA,
        DECLARE_CONDUIT,
        SET_BANNER_DATA,
        SET_TILE_ENTITY_DATA,
        SET_END_GATEWAY_DATA,
        SET_SIGN_DATA,
        UNUSED_2,
        DECLARE_BED,
        SET_JIGSAW_DATA,
        SET_CAMPFIRE_DATA,
        BEEHIVE_INFORMATION
    }
}
