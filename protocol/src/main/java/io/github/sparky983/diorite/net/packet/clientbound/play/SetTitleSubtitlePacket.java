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

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class SetTitleSubtitlePacket implements ClientBoundPacket {

    private final Component subtitleText;

    @Contract(pure = true)
    public SetTitleSubtitlePacket(final @NotNull Component subtitleText) {

        Preconditions.requireNotNull(subtitleText, "subtitleText");

        this.subtitleText = subtitleText;
    }

    @Contract(mutates = "param")
    public SetTitleSubtitlePacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.subtitleText = inputStream.readChat();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeChat(subtitleText);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SET_TITLE_SUBTITLE;
    }

    @Contract(pure = true)
    public @NotNull Component getSubtitleText() {

        return subtitleText;
    }
}
