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
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.ItemStack;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class CreativeInventoryActionPacket implements ServerBoundPacket {

    private final short slot;
    private final @Nullable ItemStack clickedItem;

    @Contract(pure = true)
    public CreativeInventoryActionPacket(final short slot, final @Nullable ItemStack clickedItem) {

        this.slot = slot;
        this.clickedItem = clickedItem;
    }

    @Contract(mutates = "param")
    public CreativeInventoryActionPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.slot = inputStream.readShort();
        this.clickedItem = ItemStack.read(inputStream).orElse(null);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeShort(slot)
                .writeNullable(clickedItem, outputStream::writeWritable);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.CREATIVE_INVENTORY_ACTION;
    }

    @Contract(pure = true)
    public short getSlot() {

        return slot;
    }

    @Contract(pure = true)
    public @NotNull Optional<ItemStack> getClickedItem() {

        return Optional.ofNullable(clickedItem);
    }
}
