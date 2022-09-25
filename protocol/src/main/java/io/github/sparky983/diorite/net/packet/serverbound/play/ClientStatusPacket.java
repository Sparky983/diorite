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

public class ClientStatusPacket implements ServerBoundPacket {

    private final Action action;

    @Contract(pure = true)
    public ClientStatusPacket(final @NotNull Action action) {

        Preconditions.requireNotNull(action, "action");

        this.action = action;
    }

    @Contract(mutates = "param")
    public ClientStatusPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.action = inputStream.readVarIntEnum(Action.class);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        outputStream.writeVarIntEnum(action);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.CLIENT_STATUS;
    }

    @Contract(pure = true)
    public @NotNull Action getAction() {

        return action;
    }

    public enum Action {

        PERFORM_RESPAWN, REQUEST_STATS
    }
}
