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

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class UnloadChunkPacket implements ClientBoundPacket {

    private final int chunkX;
    private final int chunkZ;

    @Contract(pure = true)
    public UnloadChunkPacket(final int chunkX, final int chunkZ) {

        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Contract(mutates = "param")
    public UnloadChunkPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.chunkX = inputStream.readInt();
        this.chunkZ = inputStream.readInt();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeInt(chunkX)
                .writeInt(chunkZ);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.UNLOAD_CHUNK;
    }

    @Contract(pure = true)
    public int getChunkX() {

        return chunkX;
    }

    @Contract(pure = true)
    public int getChunkZ() {

        return chunkZ;
    }
}
