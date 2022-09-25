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
import io.github.sparky983.diorite.world.Direction;
import io.github.sparky983.diorite.world.Vector;

public abstract class EntityMovementPacket implements ClientBoundPacket {

    private static final int BLOCK_SCALING_FACTOR = 32 * 128;

    protected final int entityId;
    protected final Vector delta;
    protected final Direction direction;
    protected final boolean isOnGround;

    @Contract(pure = true)
    protected EntityMovementPacket(final int entityId,
                                   final @NotNull Vector delta,
                                   final @NotNull Direction direction,
                                   final boolean isOnGround) {

        Preconditions.requireNotNull(delta, "delta");
        Preconditions.requireNotNull(direction, "direction");

        this.entityId = entityId;
        this.delta = delta;
        this.direction = direction;
        this.isOnGround = isOnGround;
    }

    /*
     * Used by callers to get input stream null check.
     */
    @Contract(pure = true)
    protected EntityMovementPacket(final @NotNull StreamIn inputStream,
                                   final int entityId,
                                   final @NotNull Vector delta,
                                   final @NotNull Direction direction,
                                   final boolean isOnGround) {

        Preconditions.requireNotNull(inputStream, "inputStream");
        Preconditions.requireNotNull(delta, "delta");
        Preconditions.requireNotNull(direction, "direction");

        this.entityId = entityId;
        this.delta = delta;
        this.direction = direction;
        this.isOnGround = isOnGround;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull Vector getDelta() {

        return delta;
    }

    @Contract(pure = true)
    public @NotNull Direction getDirection() {

        return direction;
    }

    @Contract(pure = true)
    public boolean isOnGround() {

        return isOnGround;
    }

    public static final class Position extends EntityMovementPacket {

        @Contract(pure = true)
        public Position(final int entityId, final @NotNull Vector delta, final boolean isOnGround) {

            // TODO(Sparky983): Use singleton zero direction
            super(entityId, delta, Direction.of((byte) 0, (byte) 0), isOnGround);
        }

        @Contract(mutates = "param")
        public Position(final @NotNull StreamIn inputStream) {

            super(
                    inputStream,
                    inputStream.readVarInt(),
                    Vector.of(
                            inputStream.readDouble(),
                            inputStream.readDouble(),
                            inputStream.readDouble()
                    ),
                    Direction.of((byte) 0, (byte) 0),
                    inputStream.readBoolean()
            );
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            outputStream.writeVarInt(entityId)
                    .writeDouble(delta.getX())
                    .writeDouble(delta.getY())
                    .writeDouble(delta.getZ())
                    .writeBoolean(isOnGround);
        }

        @Override
        public int getId() {

            return ClientBoundPacketId.Play.ENTITY_POSITION;
        }
    }

    public static final class PositionAndRotation extends EntityMovementPacket {

        @Contract(pure = true)
        public PositionAndRotation(final int entityId,
                                   final @NotNull Vector delta,
                                   final @NotNull Direction direction,
                                   final boolean isOnGround) {

            // TODO(Sparky983): Use singleton zero direction
            super(entityId, delta, direction, isOnGround);
        }

        @Contract(mutates = "param")
        public PositionAndRotation(final @NotNull StreamIn inputStream) {

            super(
                    inputStream,
                    inputStream.readVarInt(),
                    Vector.of(
                            inputStream.readDouble(),
                            inputStream.readDouble(),
                            inputStream.readDouble()
                    ),
                    inputStream.readDirection(),
                    inputStream.readBoolean()
            );
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            outputStream.writeVarInt(entityId)
                    .writeDouble(delta.getX())
                    .writeDouble(delta.getY())
                    .writeDouble(delta.getZ())
                    .writeDirection(direction)
                    .writeBoolean(isOnGround);
        }

        @Override
        public int getId() {

            return ClientBoundPacketId.Play.ENTITY_POSITION_AND_ROTATION;
        }
    }

    public static final class Rotation extends EntityMovementPacket {

        @Contract(pure = true)
        public Rotation(final int entityId,
                        final @NotNull Direction direction,
                        final boolean isOnGround) {

            // TODO(Sparky983): Use singleton zero vector
            super(entityId, Vector.of(0, 0, 0), direction, isOnGround);
        }

        @Contract(mutates = "param")
        public Rotation(final @NotNull StreamIn inputStream) {

            super(
                    inputStream,
                    inputStream.readVarInt(),
                    Vector.of(0, 0, 0),
                    inputStream.readDirection(),
                    inputStream.readBoolean()
            );
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            outputStream.writeVarInt(entityId)
                    .writeDirection(direction)
                    .writeBoolean(isOnGround);
        }

        @Override
        public int getId() {

            return ClientBoundPacketId.Play.ENTITY_ROTATION;
        }
    }
}
