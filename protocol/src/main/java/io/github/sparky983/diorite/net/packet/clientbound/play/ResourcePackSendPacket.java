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
import org.jetbrains.annotations.Nullable;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class ResourcePackSendPacket implements ClientBoundPacket {

    private static final int MAX_HASH_LENGTH = 40;

    private final String url;
    private final String hash;
    private final boolean isForced;
    private final @Nullable Component promptMessage;

    @Contract(pure = true)
    public ResourcePackSendPacket(final @NotNull String url,
                                    final @NotNull String hash,
                                    final boolean isForced,
                                    final @Nullable Component promptMessage) {

        Preconditions.requireNotNull(url, "url");
        Preconditions.requireNotNull(hash, "hash");
        Preconditions.requireRange(hash.length(), 0, MAX_HASH_LENGTH, "hash.length()");
        this.url = url;
        this.hash = hash;
        this.isForced = isForced;
        this.promptMessage = promptMessage;
    }

    @Contract(mutates = "param")
    public ResourcePackSendPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.url = inputStream.readString();
        this.hash = inputStream.readString(MAX_HASH_LENGTH);
        this.isForced = inputStream.readBoolean();
        this.promptMessage = inputStream.readOptional(StreamIn::readComponent).orElse(null);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeString(this.url)
                .writeString(this.hash)
                .writeBoolean(this.isForced)
                .writeNullable(this.promptMessage, StreamOut::writeComponent);
    }

    @Override
    public int getId() {
        return ClientBoundPacketId.Play.RESOURCE_PACK_SEND;
    }

    @Contract(pure = true)
    public @NotNull String getUrl() {

        return url;
    }

    @Contract(pure = true)
    public @NotNull String getHash() {

        return hash;
    }

    @Contract(pure = true)
    public boolean isForced() {

        return isForced;
    }

    @Contract(pure = true)
    public @Nullable Component getPromptMessage() {

        return promptMessage;
    }
}
