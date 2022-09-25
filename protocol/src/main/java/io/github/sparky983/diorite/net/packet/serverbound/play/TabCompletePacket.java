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

public class TabCompletePacket implements ServerBoundPacket {

    private static final int MAX_TEXT_LENGTH = 32500;

    private final int transactionId;
    private final String text;

    @Contract(pure = true)
    public TabCompletePacket(final int transactionId, final @NotNull String text) {

        Preconditions.requireNotNull(text, "text");
        Preconditions.requireRange(text.length(), 0, MAX_TEXT_LENGTH, "text.length()");

        this.transactionId = transactionId;
        this.text = text;
    }

    @Contract(mutates = "param")
    public TabCompletePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.transactionId = inputStream.readVarInt();
        this.text = inputStream.readString(MAX_TEXT_LENGTH);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(transactionId)
                .writeString(text);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.TAB_COMPLETE;
    }

    @Contract(pure = true)
    public int getTransactionId() {

        return transactionId;
    }

    @Contract(pure = true)
    public @NotNull String getText() {

        return text;
    }
}
