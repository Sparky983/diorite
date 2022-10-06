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
public final class DeclareCommandsPacket implements ClientBoundPacket {

    private final int nodeCount;
    private final byte[] nodes; // TODO: Add node data type and implement parsers
    private final int rootIndex;

    @Contract(pure = true)
    public DeclareCommandsPacket(final int nodeCount,
            final byte @NotNull [] nodes,
            final int rootIndex) {

        Preconditions.requireNotNull(nodes, "nodes");

        this.nodeCount = nodeCount;
        this.nodes = nodes;
        this.rootIndex = rootIndex;
    }

    @Contract(pure = true)
    public DeclareCommandsPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        throw new DecodeException("Unable to read declare commands packet", true);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(nodeCount)
                .writeBytes(nodes)
                .writeVarInt(rootIndex);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.DECLARE_COMMANDS;
    }

    @Contract(pure = true)
    public int getNodeCount() {

        return nodeCount;
    }

    @Contract(pure = true)
    public byte @NotNull [] getNodes() {

        // TODO(Sparky983): Think about returning a clone
        return nodes;
    }

    @Contract(pure = true)
    public int getRootIndex() {

        return rootIndex;
    }
}
