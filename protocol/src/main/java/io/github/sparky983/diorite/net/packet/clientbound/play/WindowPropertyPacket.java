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

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class WindowPropertyPacket implements ClientBoundPacket {

    private final int windowId;
    private final short property;
    private final short value;

    @Contract(pure = true)
    public WindowPropertyPacket(final @Range(from = 0x00, to = 0xFF) int windowId,
                                final short property,
                                final short value) {

        Preconditions.requireRange(windowId, 0x00, 0xFF, "windowId");

        this.windowId = windowId;
        this.property = property;
        this.value = value;
    }

    @Contract(mutates = "param")
    public WindowPropertyPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.windowId = inputStream.readUnsignedByte();
        this.property = inputStream.readShort();
        this.value = inputStream.readShort();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeUnsignedByte(windowId)
                .writeShort(property)
                .writeShort(value);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.WINDOW_PROPERTY;
    }

    @Contract(pure = true)
    public @Range(from = 0x00, to = 0xFF) int getWindowId() {

        return windowId;
    }

    @Contract(pure = true)
    public short getProperty() {

        return property;
    }

    @Contract(pure = true)
    public short getValue() {

        return value;
    }
}
