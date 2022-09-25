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

import java.util.UUID;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.CardinalDirection;
import io.github.sparky983.diorite.world.Position;

public class SpawnPaintingPacket implements ClientBoundPacket {

    private final int entityId;
    private final UUID entityUuid;
    private final int motive;
    private final Position location;
    private final CardinalDirection direction;

    @Contract(pure = true)
    public SpawnPaintingPacket(final int entityId,
                               final @NotNull UUID entityUuid,
                               final int motive,
                               final @NotNull Position location,
                               final @NotNull CardinalDirection direction) {

        Preconditions.requireNotNull(entityUuid, "entityUuid");
        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(direction, "direction");

        this.entityId = entityId;
        this.entityUuid = entityUuid;
        this.motive = motive;
        this.location = location;
        this.direction = direction;
    }

    @Contract(mutates = "param")
    public SpawnPaintingPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.entityUuid = inputStream.readUuid();
        this.motive = inputStream.readVarInt();
        this.location = inputStream.readPosition();
        this.direction = inputStream.readByteEnum(CardinalDirection.class);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeUuid(entityUuid)
                .writeVarInt(motive)
                .writePosition(location)
                .writeByteEnum(direction);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SPAWN_PAINTING;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull UUID getEntityUuid() {

        return entityUuid;
    }

    @Contract(pure = true)
    public int getMotive() {

        return motive;
    }

    @Contract(pure = true)
    public @NotNull Position getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @NotNull CardinalDirection getDirection() {

        return direction;
    }
}
