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
import org.jetbrains.annotations.Range;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class CloseWindowPacket implements ClientBoundPacket {

    private final int windowId;

    @Contract(pure = true)
    public CloseWindowPacket(final @Range(from = 0x00, to = 0xFF) int windowId) {

        Preconditions.requireRange(windowId, 0x00, 0xFF, "windowId");

        this.windowId = windowId;
    }

    @Contract(mutates = "param")
    public CloseWindowPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.windowId = inputStream.readUByte();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeUByte(windowId);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.CLOSE_WINDOW;
    }

    @Contract(pure = true)
    public @Range(from = 0x00, to = 0xFF) int getWindowId() {

        return windowId;
    }
}
