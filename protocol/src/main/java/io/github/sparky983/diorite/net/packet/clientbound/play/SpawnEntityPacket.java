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
import io.github.sparky983.diorite.world.Direction;
import io.github.sparky983.diorite.world.Position;
import io.github.sparky983.diorite.world.Velocity;

public final class SpawnEntityPacket implements ClientBoundPacket {

    private final int entityId;
    private final UUID objectId;
    private final int entityType;
    private final Position location;
    private final Direction direction;
    private final int data;
    private final Velocity velocity;

    @Contract(pure = true)
    public SpawnEntityPacket(final int entityId,
            final @NotNull UUID objectId,
            final int entityType,
            final @NotNull Position location,
            final @NotNull Direction direction,
            final int data,
            final @NotNull Velocity velocity) {

        Preconditions.requireNotNull(objectId, "objectId");
        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(direction, "direction");
        Preconditions.requireNotNull(velocity, "velocity");

        this.entityId = entityId;
        this.objectId = objectId;
        this.entityType = entityType;
        this.location = location;
        this.direction = direction;
        this.data = data;
        this.velocity = velocity;
    }

    @Contract(mutates = "param")
    public SpawnEntityPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.objectId = inputStream.readUuid();
        this.entityType = inputStream.readVarInt();
        this.location = inputStream.readPosition();
        this.direction = inputStream.readDirection();
        this.data = inputStream.readInt();
        this.velocity = inputStream.readVelocity();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeUuid(objectId)
                .writeVarInt(entityType)
                .writePosition(location)
                .writeDirection(direction)
                .writeInt(data)
                .writeVelocity(velocity);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SPAWN_ENTITY;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull UUID getObjectId() {

        return objectId;
    }

    @Contract(pure = true)
    public int getEntityType() {

        return entityType;
    }

    @Contract(pure = true)
    public @NotNull Position getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @NotNull Direction getDirection() {

        return direction;
    }

    @Contract(pure = true)
    public int getData() {

        return data;
    }

    @Contract(pure = true)
    public @NotNull Velocity getVelocity() {

        return velocity;
    }
}
