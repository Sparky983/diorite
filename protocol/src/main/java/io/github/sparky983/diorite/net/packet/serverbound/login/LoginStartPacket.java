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

package io.github.sparky983.diorite.net.packet.serverbound.login;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class LoginStartPacket implements ServerBoundPacket {

    private static final int MAX_NAME_LENGTH = 16;

    private final String name;

    @Contract(pure = true)
    public LoginStartPacket(final @NotNull String name) {

        Preconditions.requireNotNull(name, "name");
        Preconditions.requireRange(name.length(), 0, MAX_NAME_LENGTH, "name.length()");

        this.name = name;
    }

    @Contract(mutates = "param")
    public LoginStartPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream);

        this.name = inputStream.readString(MAX_NAME_LENGTH);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        outputStream.writeString(name);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Login.LOGIN_START;
    }

    @Contract(pure = true)
    public @NotNull String getName() {

        return name;
    }
}
