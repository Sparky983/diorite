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

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class NameItemPacket implements ServerBoundPacket {

    private static final int MAX_ITEM_NAME = 35;

    private final String itemName;

    @Contract(pure = true)
    public NameItemPacket(final @NotNull String itemName) {

        Preconditions.requireNotNull(itemName, "itemName");
        Preconditions.requireRange(itemName.length(), 0, MAX_ITEM_NAME, "itemName.length()");
        // Postel's law: "be conservative with in what you send"
        // name item allows for limit higher than 35 characters in the protocol, but they'll be ignored

        this.itemName = itemName;
    }

    @Contract(mutates = "param")
    public NameItemPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.itemName = inputStream.readString();
        // Postel's law: "be liberal with in what you accept"
        // name item allows for limit higher than 35 characters in the protocol, but they'll be ignored
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeString(itemName);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.NAME_ITEM;
    }

    @Contract(pure = true)
    public @NotNull String getItemName() {

        return itemName;
    }
}
