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

public final class SpawnLivingEntityPacket implements ClientBoundPacket {

    private final int entityId;
    private final UUID entityUuid;
    private final int type;
    private final Position location;
    private final Direction direction;
    private final byte headPitch;
    private final Velocity velocity;

    @Contract(pure = true)
    public SpawnLivingEntityPacket(final int entityId,
            final @NotNull UUID entityUuid,
            final int type,
            final @NotNull Position location,
            final @NotNull Direction direction,
            final byte headPitch,
            final @NotNull Velocity velocity) {

        Preconditions.requireNotNull(entityUuid, "entityUuid");
        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(direction, "direction");
        Preconditions.requireNotNull(velocity, "velocity");

        this.entityId = entityId;
        this.entityUuid = entityUuid;
        this.type = type;
        this.location = location;
        this.direction = direction;
        this.headPitch = headPitch;
        this.velocity = velocity;
    }

    @Contract(mutates = "param")
    public SpawnLivingEntityPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.entityUuid = inputStream.readUuid();
        this.type = inputStream.readVarInt();
        this.location = inputStream.readPosition();
        this.direction = inputStream.readDirection();
        this.headPitch = inputStream.readByte();
        this.velocity = inputStream.readVelocity();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeUuid(entityUuid)
                .writeVarInt(type)
                .writePosition(location)
                .writeDirection(direction)
                .writeByte(headPitch)
                .writeVelocity(velocity);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SPAWN_LIVING_ENTITY;
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
    public int getType() {

        return type;
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
    public byte getHeadPitch() {

        return headPitch;
    }

    @Contract(pure = true)
    public @NotNull Velocity getVelocity() {

        return velocity;
    }
}
