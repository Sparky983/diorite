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

package io.github.sparky983.diorite.net.packet.clientbound.login;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class LoginSuccessPacket implements ClientBoundPacket {

    private static final int MAX_USERNAME_LENGTH = 16;

    private final UUID uuid;
    private final String username;

    @Contract(pure = true)
    public LoginSuccessPacket(final @NotNull UUID uuid, final @NotNull String username) {

        Preconditions.requireNotNull(uuid, "uuid");
        Preconditions.requireNotNull(username, "username");
        Preconditions.requireRange(username.length(), 0, MAX_USERNAME_LENGTH, "username.length()");

        this.uuid = uuid;
        this.username = username;
    }

    @Contract(mutates = "param")
    public LoginSuccessPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.uuid = inputStream.readUuid();
        this.username = inputStream.readString(MAX_USERNAME_LENGTH);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeUuid(uuid)
                .writeString(username);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Login.LOGIN_SUCCESS;
    }

    @Contract(pure = true)
    public @NotNull UUID getUuid() {

        return uuid;
    }

    @Contract(pure = true)
    public @NotNull String getUsername() {

        return username;
    }
}
