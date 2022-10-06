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

import net.kyori.adventure.text.Component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class ChatMessagePacket implements ClientBoundPacket {

    private final Component message;
    private final Position position;
    private final UUID sender;

    @Contract(pure = true)
    public ChatMessagePacket(final @NotNull Component message,
            final @NotNull Position position,
            final @NotNull UUID sender) {

        Preconditions.requireNotNull(message, "message");
        Preconditions.requireNotNull(position, "position");
        Preconditions.requireNotNull(sender, "sender");

        this.message = message;
        this.position = position;
        this.sender = sender;
    }

    @Contract(mutates = "param")
    public ChatMessagePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        message = inputStream.readComponent();
        position = inputStream.readByteEnum(Position.class);
        sender = inputStream.readUuid();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeComponent(message)
                .writeByteEnum(position)
                .writeUuid(sender);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.CHAT_MESSAGE;
    }

    @Contract(pure = true)
    public @NotNull Component getMessage() {

        return message;
    }

    @Contract(pure = true)
    public @NotNull Position getPosition() {

        return position;
    }

    @Contract(pure = true)
    public @NotNull UUID getSender() {

        return sender;
    }

    public enum Position {

        CHAT,
        SYSTEM,
        GAME_INFO
    }
}
