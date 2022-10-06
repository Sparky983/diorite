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

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

@ApiStatus.Experimental
public final class OpenWindowPacket implements ClientBoundPacket {

    private final int windowId;
    private final int windowType; // TODO(Sparky983): Represent as an inventory type enum
    private final Component title;

    @Contract(pure = true)
    public OpenWindowPacket(final int windowId,
            final int windowType,
            final @NotNull Component title) {

        Preconditions.requireNotNull(title, "title");

        this.windowId = windowId;
        this.windowType = windowType;
        this.title = title;
    }

    @Contract(mutates = "param")
    public OpenWindowPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.windowId = inputStream.readVarInt();
        this.windowType = inputStream.readVarInt();
        this.title = inputStream.readComponent();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(windowId)
                .writeVarInt(windowType)
                .writeComponent(title);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.OPEN_WINDOW;
    }

    @Contract(pure = true)
    public int getWindowId() {

        return windowId;
    }

    @Contract(pure = true)
    public int getWindowType() {

        return windowType;
    }

    @Contract(pure = true)
    public @NotNull Component getTitle() {

        return title;
    }
}
