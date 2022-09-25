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

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class DestroyEntitiesPacket implements ClientBoundPacket {

    private final int[] entityIds;

    @Contract(pure = true)
    public DestroyEntitiesPacket(final int @NotNull [] entityIds) {

        Preconditions.requireNotNull(entityIds, "entityIds");

        this.entityIds = entityIds;
    }

    @Contract(mutates = "param")
    public DestroyEntitiesPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        // TODO(Sparky983): make this a StreamIn method
        final int length = inputStream.readVarInt();
        this.entityIds = new int[length];
        for (int i = 0; i < length; i++) {
            this.entityIds[i] = inputStream.readVarInt();
        }
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(this.entityIds.length);
        for (final int entityId : this.entityIds) {
            outputStream.writeVarInt(entityId);
        }
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.DESTROY_ENTITIES;
    }

    @Contract(pure = true)
    public int @NotNull [] getEntityIds() {

        // TODO(Sparky983): consider returning a clone
        return entityIds;
    }
}
