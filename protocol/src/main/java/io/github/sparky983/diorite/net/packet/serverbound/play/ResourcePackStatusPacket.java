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

public final class ResourcePackStatusPacket implements ServerBoundPacket {

    private final Status status;

    @Contract(pure = true)
    public ResourcePackStatusPacket(final @NotNull Status status) {

        Preconditions.requireNotNull(status, "status");

        this.status = status;
    }

    @Contract(mutates = "param")
    public ResourcePackStatusPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.status = inputStream.readVarIntEnum(Status.class);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarIntEnum(status);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.RESOURCE_PACK_STATUS;
    }

    @Contract(pure = true)
    public @NotNull Status getStatus() {

        return status;
    }

    public enum Status {

        LOADED,
        DECLINED,
        FAILED_DOWNLOAD,
        ACCEPTED;
    }
}
