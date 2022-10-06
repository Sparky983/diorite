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
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.ItemStack;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class WindowItemsPacket implements ClientBoundPacket {

    private final int windowId;
    private final int stateId;
    private final List<@Nullable ItemStack> itemStackList;
    private final @Nullable ItemStack carriedItem;

    @Contract(pure = true)
    public WindowItemsPacket(final int windowId,
            final int stateId,
            final @NotNull List<@Nullable ItemStack> itemStackList,
            final @Nullable ItemStack carriedItem) {

        Preconditions.requireNotNull(itemStackList, "itemStackList");

        this.windowId = windowId;
        this.stateId = stateId;

        final List<ItemStack> copy = new ArrayList<>();
        Collections.copy(copy, itemStackList);
        this.itemStackList = Collections.unmodifiableList(copy);

        this.carriedItem = carriedItem;
    }

    @Contract(mutates = "param")
    public WindowItemsPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.windowId = inputStream.readUnsignedByte();
        this.stateId = inputStream.readVarInt();
        this.itemStackList = inputStream.readList(ItemStack::readNullable);
        this.carriedItem = ItemStack.readNullable(inputStream);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeUnsignedByte(windowId)
                .writeVarInt(stateId)
                .writeList(
                        itemStackList,
                        (slotDataList) -> outputStream.writeNullable(
                                slotDataList,
                                outputStream::writeWritable
                        )
                )
                .writeWritable(carriedItem);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.WINDOW_ITEMS;
    }

    @Contract(pure = true)
    public @Range(from = 0x00, to = 0xFF) int getWindowId() {

        return windowId;
    }

    @Contract(pure = true)
    public int getStateId() {

        return stateId;
    }

    @Contract(pure = true)
    public @NotNull List<@Nullable ItemStack> getSlotData() {

        return itemStackList;
    }

    @Contract(pure = true)
    public @NotNull ItemStack getCarriedItem() {

        return carriedItem;
    }
}
