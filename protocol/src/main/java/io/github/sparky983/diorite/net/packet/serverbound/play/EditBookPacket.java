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
import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Hand;

public class EditBookPacket implements ServerBoundPacket {

    private final Hand hand;
    private final List<String> entries;
    private final @Nullable String title;

    @Contract(pure = true)
    public EditBookPacket(final @NotNull Hand hand,
                          final @NotNull List<@NotNull String> entries,
                          final @Nullable String title) {

        Preconditions.requireNotNull(hand, "hand");
        Preconditions.requireContainsNoNulls(entries, "entries");

        this.hand = hand;
        this.entries = List.copyOf(entries);
        this.title = title;
    }

    @Contract(mutates = "param")
    public EditBookPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.hand = inputStream.readVarIntEnum(Hand.class);
        this.entries = inputStream.readList(() -> inputStream.readString());
        this.title = inputStream.readOptional(() -> inputStream.readString()).orElse(null);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarIntEnum(hand)
                .writeList(entries, outputStream::writeString)
                .writeNullable(title, outputStream::writeString);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.EDIT_BOOK;
    }

    @Contract(pure = true)
    public @NotNull Hand getHand() {

        return hand;
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull String> getEntries() {

        return entries;
    }

    @Contract(pure = true)
    public @Nullable String getTitle() {

        return title;
    }
}
