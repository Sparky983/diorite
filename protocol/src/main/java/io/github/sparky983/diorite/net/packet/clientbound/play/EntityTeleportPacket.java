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
import io.github.sparky983.diorite.world.Direction;
import io.github.sparky983.diorite.world.Position;

public class EntityTeleportPacket implements ClientBoundPacket {

    private final int entityId;
    private final Position location;
    private final Direction direction;
    private final boolean isOnGround;

    @Contract(pure = true)
    public EntityTeleportPacket(final int entityId,
                                final @NotNull Position location,
                                final @NotNull Direction direction,
                                final boolean isOnGround) {

        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(direction, "direction");

        this.entityId = entityId;
        this.location = location;
        this.direction = direction;
        this.isOnGround = isOnGround;
    }

    @Contract(mutates = "param")
    public EntityTeleportPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.location = inputStream.readPosition();
        this.direction = inputStream.readDirection();
        this.isOnGround = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writePosition(location)
                .writeDirection(direction)
                .writeBoolean(isOnGround);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.ENTITY_TELEPORT;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull Position getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @NotNull Direction getDirection() {

        return direction;
    }

    public boolean isOnGround() {

        return isOnGround;
    }
}
