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

public final class SetRecipeBookStatePacket implements ServerBoundPacket {

    private final Book book;
    private final boolean isOpen;
    private final boolean isFilterActive;

    @Contract(pure = true)
    public SetRecipeBookStatePacket(final @NotNull Book book,
                                    final boolean isOpen,
                                    final boolean isFilterActive) {

        Preconditions.requireNotNull(book, "book");

        this.book = book;
        this.isOpen = isOpen;
        this.isFilterActive = isFilterActive;
    }

    @Contract(mutates = "param")
    public SetRecipeBookStatePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.book = inputStream.readVarIntEnum(Book.class);
        this.isOpen = inputStream.readBoolean();
        this.isFilterActive = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarIntEnum(book)
                .writeBoolean(isOpen)
                .writeBoolean(isFilterActive);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.SET_RECIPE_BOOK_STATE;
    }

    @Contract(pure = true)
    public @NotNull Book getBook() {

        return book;
    }

    @Contract(pure = true)
    public boolean isOpen() {

        return isOpen;
    }

    @Contract(pure = true)
    public boolean isFilterActive() {

        return isFilterActive;
    }

    public enum Book {

        CRAFTING,
        FURNACE,
        BLAST_FURNACE,
        SMOKER
    }
}
