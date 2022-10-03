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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class TagsPacket implements ClientBoundPacket {

    private final List<Tag> tags;

    @Contract(pure = true)
    public TagsPacket(final @NotNull List<@NotNull Tag> tags) {

        Preconditions.requireContainsNoNulls(tags, "tags");

        this.tags = List.copyOf(tags);
    }

    @Contract(mutates = "param")
    public TagsPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.tags = inputStream.readList(Tag::new);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeList(tags, StreamOut::writeWritable);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.TAGS;
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull Tag> getTags() {

        return tags;
    }

    public static final class Tag implements Writable {

        private final String name;
        private final int[] entries;

        @Contract(pure = true)
        public Tag(final @NotNull String name, final int @NotNull [] entries) {

            Preconditions.requireNotNull(name, "name");
            Preconditions.requireNotNull(entries, "entries");

            this.name = name;
            // TODO(Sparky983): consider using cloned array
            this.entries = entries;
        }

        @Contract(mutates = "param")
        public Tag(final @NotNull StreamIn inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.name = inputStream.readString();

            final int entriesLength = inputStream.readVarInt();
            this.entries = new int[entriesLength];
            for (int i = 0; i < entriesLength; i++) {
                this.entries[i] = inputStream.readVarInt();
            }
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeString(name)
                    .writeVarInt(entries.length);

            for (final int entry : entries) {
                outputStream.writeVarInt(entry);
            }
        }

        @Contract(pure = true)
        public @NotNull String getName() {

            return name;
        }

        @Contract(pure = true)
        public int @NotNull [] getEntries() {

            return entries;
        }
    }
}
