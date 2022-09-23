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
import org.jetbrains.annotations.Nullable;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.SlotData;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class SetSlotPacket implements ClientBoundPacket {

    private final byte windowId;
    private final int stateId;
    private final short slot;
    private final @Nullable SlotData slotData;

    @Contract(pure = true)
    public SetSlotPacket(final byte windowId,
                         final int stateId,
                         final short slot,
                         final @NotNull SlotData slotData) {

        this.windowId = windowId;
        this.stateId = stateId;
        this.slot = slot;
        this.slotData = slotData;
    }

    @Contract(mutates = "param")
    public SetSlotPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.windowId = inputStream.readByte();
        this.stateId = inputStream.readVarInt();
        this.slot = inputStream.readShort();
        this.slotData = SlotData.readNullable(inputStream);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeByte(windowId)
                .writeVarInt(stateId)
                .writeShort(slot)
                .writeNullable(slotData, outputStream::writeWritable);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SET_SLOT;
    }

    @Contract(pure = true)
    public byte getWindowId() {

        return windowId;
    }

    @Contract(pure = true)
    public int getStateId() {

        return stateId;
    }

    @Contract(pure = true)
    public short getSlot() {

        return slot;
    }

    @Contract(pure = true)
    public @Nullable SlotData getSlotData() {

        return slotData;
    }
}
