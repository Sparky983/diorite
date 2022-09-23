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

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class CollectItemPacket implements ClientBoundPacket {

    private final int collectedEntityId;
    private final int collectorEntityId;
    private final int pickupItemCount;

    @Contract(pure = true)
    public CollectItemPacket(final int collectedEntityId, final int collectorEntityId, final int pickupItemCount) {

        this.collectedEntityId = collectedEntityId;
        this.collectorEntityId = collectorEntityId;
        this.pickupItemCount = pickupItemCount;
    }

    @Contract(mutates = "param")
    public CollectItemPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.collectedEntityId = inputStream.readVarInt();
        this.collectorEntityId = inputStream.readVarInt();
        this.pickupItemCount = inputStream.readVarInt();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(collectedEntityId)
                .writeVarInt(collectorEntityId)
                .writeVarInt(pickupItemCount);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.COLLECT_ITEM;
    }

    @Contract(pure = true)
    public int getCollectedEntityId() {

        return collectedEntityId;
    }

    @Contract(pure = true)
    public int getCollectorEntityId() {

        return collectorEntityId;
    }

    @Contract(pure = true)
    public int getPickupItemCount() {

        return pickupItemCount;
    }
}
