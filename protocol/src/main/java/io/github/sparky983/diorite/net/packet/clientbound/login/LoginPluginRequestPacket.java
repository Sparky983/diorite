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

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Identifier;

public class LoginPluginRequestPacket implements ClientBoundPacket {

    private final int messageId;
    private final Identifier channel;
    private final byte[] data;

    @Contract(pure = true)
    public LoginPluginRequestPacket(final int messageId,
            final @NotNull Identifier channel,
            final byte @NotNull [] data) {

        Preconditions.requireNotNull(channel, "channel");
        Preconditions.requireNotNull(data, "data");

        this.messageId = messageId;
        this.channel = channel;
        this.data = data;
    }

    @Contract(mutates = "param")
    public LoginPluginRequestPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.messageId = inputStream.readVarInt();
        this.channel = inputStream.readIdentifier();
        this.data = inputStream.readAllBytes();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(messageId)
                .writeIdentifier(channel)
                .writeBytes(data);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Login.LOGIN_PLUGIN_REQUEST;
    }

    @Contract(pure = true)
    public int getMessageId() {

        return messageId;
    }

    @Contract(pure = true)
    public @NotNull Identifier getChannel() {

        return channel;
    }

    @Contract(pure = true)
    public byte @NotNull [] getData() {

        // TODO(Sparky983): Think about returning a clone
        return data;
    }
}
