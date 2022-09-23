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

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class ChatMessagePacket implements ServerBoundPacket {

    private static final int MAX_MESSAGE_LENGTH = 256;

    private final String message;

    @Contract(pure = true)
    public ChatMessagePacket(final @NotNull String message) {

        Preconditions.requireNotNull(message, "message");
        Preconditions.requireRange(message.length(), 0, MAX_MESSAGE_LENGTH, "message.length()");

        this.message = message;
    }

    @Contract(mutates = "param")
    public ChatMessagePacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.message = inputStream.readString(MAX_MESSAGE_LENGTH);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        outputStream.writeString(message);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.CHAT_MESSAGE;
    }

    @Contract(pure = true)
    public @NotNull String getMessage() {

        return message;
    }
}
