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

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Direction;
import io.github.sparky983.diorite.world.Position;

public class SpawnPlayerPacket implements ClientBoundPacket {

    private final int entityId;
    private final UUID entityUuid;
    private final Position position;
    private final Direction direction;

    @Contract(pure = true)
    public SpawnPlayerPacket(final int entityId,
                             final @NotNull UUID entityUuid,
                             final @NotNull Position position,
                             final @NotNull Direction direction) {

        Preconditions.requireNotNull(entityUuid, "entityUuid");
        Preconditions.requireNotNull(position, "position");
        Preconditions.requireNotNull(direction, "direction");

        this.entityId = entityId;
        this.entityUuid = entityUuid;
        this.position = position;
        this.direction = direction;
    }

    @Contract(mutates = "param")
    public SpawnPlayerPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.entityUuid = inputStream.readUuid();
        this.position = inputStream.readPosition();
        this.direction = inputStream.readDirection();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(this.entityId)
                .writeUuid(this.entityUuid)
                .writePosition(this.position)
                .writeDirection(this.direction);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SPAWN_PLAYER;
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
    public @NotNull Position getPosition() {

        return position;
    }

    @Contract(pure = true)
    public @NotNull Direction getDirection() {

        return direction;
    }
}
