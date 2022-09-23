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

import java.util.List;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class TabCompletePacket implements ClientBoundPacket {

    private final int transactionId;
    private final int start;
    private final int length;
    private final List<Match> matches;

    @Contract(pure = true)
    public TabCompletePacket(final int transactionId,
                             final int start,
                             final int length,
                             final @NotNull List<@NotNull Match> matches) {

        Preconditions.requireContainsNoNulls(matches, "matches");

        this.transactionId = transactionId;
        this.start = start;
        this.length = length;
        this.matches = matches;
    }

    @Contract(mutates = "param")
    public TabCompletePacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        transactionId = inputStream.readVarInt();
        start = inputStream.readVarInt();
        length = inputStream.readVarInt();
        matches = inputStream.readList(Match::new);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(transactionId);
        outputStream.writeVarInt(start);
        outputStream.writeVarInt(length);
        outputStream.writeList(matches, (match) -> match.write(outputStream));
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.TAB_COMPLETE;
    }

    public static final class Match implements Writable {

        private final String match;
        private final @Nullable Component toolTip;

        @Contract(pure = true)
        public Match(final @NotNull String match, final @Nullable Component toolTip) {

            Preconditions.requireNotNull(match, "match");

            this.match = match;
            this.toolTip = toolTip;
        }

        @Contract(mutates = "param")
        public Match(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.match = inputStream.readString();
            this.toolTip = inputStream.readOptional(inputStream::readChat).orElse(null);
        }

        @Contract(pure = true)
        public @NotNull String getMatch() {

            return match;
        }

        @Contract(pure = true)
        public @Nullable Component getToolTip() {

            return toolTip;
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeString(match);
            outputStream.writeNullable(toolTip, outputStream::writeChat);
        }
    }
}
