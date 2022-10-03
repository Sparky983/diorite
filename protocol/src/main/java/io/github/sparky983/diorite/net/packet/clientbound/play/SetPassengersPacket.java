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

public final class SetPassengersPacket implements ClientBoundPacket {

    private final int entityId;
    private final int[] passengerIds;

    @Contract(pure = true)
    public SetPassengersPacket(final int entityId, final int @NotNull [] passengerIds) {

        Preconditions.requireNotNull(passengerIds, "passengerIds");

        this.entityId = entityId;

        // TODO(Sparky983): consider using cloned array
        this.passengerIds = passengerIds;
    }

    @Contract(mutates = "param")
    public SetPassengersPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();

        final int passengerCount = inputStream.readVarInt();
        passengerIds = new int[passengerCount];

        for (int i = 0; i < passengerCount; i++) {
            passengerIds[i] = inputStream.readVarInt();
        }
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeVarInt(passengerIds.length);

        for (final int passengerId : passengerIds) {
            outputStream.writeVarInt(passengerId);
        }
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SET_PASSENGERS;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public int @NotNull [] getPassengerIds() {

        // TODO(Sparky983): consider returning a clone
        return passengerIds;
    }
}
