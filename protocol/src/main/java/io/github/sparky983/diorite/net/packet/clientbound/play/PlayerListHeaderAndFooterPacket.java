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

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class PlayerListHeaderAndFooterPacket implements ClientBoundPacket {

    private final Component header;
    private final Component footer;

    @Contract(pure = true)
    public PlayerListHeaderAndFooterPacket(final @NotNull Component header,
                                           final @NotNull Component footer) {

        Preconditions.requireNotNull(header, "header");
        Preconditions.requireNotNull(footer, "footer");

        this.header = header;
        this.footer = footer;
    }

    @Contract(mutates = "param")
    public PlayerListHeaderAndFooterPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.header = inputStream.readComponent();
        this.footer = inputStream.readComponent();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeComponent(header)
                .writeComponent(footer);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.PLAYER_LIST_HEADER_FOOTER;
    }

    @Contract(pure = true)
    public @NotNull Component getHeader() {

        return header;
    }

    @Contract(pure = true)
    public @NotNull Component getFooter() {

        return footer;
    }
}
