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
import io.github.sparky983.diorite.world.BlockPosition;

public class QueryBlockNbtPacket implements ServerBoundPacket {

    private final int transactionId;
    private final BlockPosition location;

    @Contract(pure = true)
    public QueryBlockNbtPacket(final int transactionId, final @NotNull BlockPosition location) {

        Preconditions.requireNotNull(location, "location");

        this.transactionId = transactionId;
        this.location = location;
    }

    @Contract(mutates = "param")
    public QueryBlockNbtPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.transactionId = inputStream.readVarInt();
        this.location = inputStream.readBlockPosition();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(transactionId)
                .writeBlockPosition(location);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.QUERY_BLOCK_NBT;
    }

    @Contract(pure = true)
    public int getTransactionId() {

        return transactionId;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }
}
