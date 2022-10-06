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

public abstract class EntityMovementPacket implements ClientBoundPacket {

    private static final int BLOCK_SCALING_FACTOR = 32 * 128;

    protected final int entityId;
    protected final short dx;
    protected final short dy;
    protected final short dz;
    protected final Direction direction;
    protected final boolean isOnGround;

    @Contract(pure = true)
    protected EntityMovementPacket(final int entityId,
            final short dx,
            final short dy,
            final short dz,
            final @NotNull Direction direction,
            final boolean isOnGround) {

        Preconditions.requireNotNull(direction, "direction");

        this.entityId = entityId;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.direction = direction;
        this.isOnGround = isOnGround;
    }

    /*
     * Used by callers to get input stream null check.
     */
    @Contract(pure = true)
    protected EntityMovementPacket(final @NotNull StreamIn inputStream,
            final int entityId,
            final short dx,
            final short dy,
            final short dz,
            final @NotNull Direction direction,
            final boolean isOnGround) {

        Preconditions.requireNotNull(inputStream, "inputStream");
        Preconditions.requireNotNull(direction, "direction");

        this.entityId = entityId;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.direction = direction;
        this.isOnGround = isOnGround;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public short getDx() {

        return dx;
    }

    @Contract(pure = true)
    public short getDy() {

        return dy;
    }

    @Contract(pure = true)
    public short getDz() {

        return dz;
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
        public Position(final int entityId,
                final short dx,
                final short dy,
                final short dz,
                final boolean isOnGround) {

            // TODO(Sparky983): Use singleton zero direction
            super(entityId, dx, dy, dz, Direction.of((byte) 0, (byte) 0), isOnGround);
        }

        @Contract(mutates = "param")
        public Position(final @NotNull StreamIn inputStream) {

            super(
                    inputStream,
                    inputStream.readVarInt(),
                    inputStream.readShort(),
                    inputStream.readShort(),
                    inputStream.readShort(),
                    Direction.of((byte) 0, (byte) 0),
                    inputStream.readBoolean()
            );
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            outputStream.writeVarInt(entityId)
                    .writeDouble(dx)
                    .writeDouble(dy)
                    .writeDouble(dz)
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
                final short dx,
                final short dy,
                final short dz,
                final @NotNull Direction direction,
                final boolean isOnGround) {

            super(entityId, dx, dy, dz, direction, isOnGround);
        }

        @Contract(mutates = "param")
        public PositionAndRotation(final @NotNull StreamIn inputStream) {

            super(
                    inputStream,
                    inputStream.readVarInt(),
                    inputStream.readShort(),
                    inputStream.readShort(),
                    inputStream.readShort(),
                    inputStream.readDirection(),
                    inputStream.readBoolean()
            );
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            outputStream.writeVarInt(entityId)
                    .writeDouble(dx)
                    .writeDouble(dy)
                    .writeDouble(dz)
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

            super(entityId, (short) 0, (short) 0, (short) 0, direction, isOnGround);
        }

        @Contract(mutates = "param")
        public Rotation(final @NotNull StreamIn inputStream) {

            super(
                    inputStream,
                    inputStream.readVarInt(),
                    (short) 0,
                    (short) 0,
                    (short) 0,
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
