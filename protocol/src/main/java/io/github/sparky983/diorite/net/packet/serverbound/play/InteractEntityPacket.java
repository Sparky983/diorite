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

package io.github.sparky983.diorite.net.packet.serverbound.play;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Hand;

public final class InteractEntityPacket implements ServerBoundPacket {

    private final int entityId;
    private final Type type;
    private final @UnknownNullability Float targetX;
    private final @UnknownNullability Float targetY;
    private final @UnknownNullability Float targetZ;
    private final @UnknownNullability Hand hand;
    private final boolean isSneaking;

    @Contract(pure = true)
    public InteractEntityPacket(final int entityId,
            final @NotNull Type type,
            final @Nullable Float targetX,
            final @Nullable Float targetY,
            final @Nullable Float targetZ,
            final @Nullable Hand hand,
            final boolean isSneaking) {

        Preconditions.requireNotNull(type, "type");

        if (type == Type.INTERACT_AT) {
            Preconditions.requireNotNull(targetX, "targetX");
            Preconditions.requireNotNull(targetY, "targetY");
            Preconditions.requireNotNull(targetZ, "targetZ");
        } else {
            Preconditions.requireTrue(targetX == null,
                    "[targetX] must be null if type is not INTERACT_AT");
            Preconditions.requireTrue(targetY == null,
                    "[targetY] must be null if type is not INTERACT_AT");
            Preconditions.requireTrue(targetZ == null,
                    "[targetZ] must be null if type is not INTERACT_AT");
        }

        if (type == Type.INTERACT || type == Type.INTERACT_AT) {
            Preconditions.requireNotNull(hand);
        } else {
            Preconditions.requireTrue(hand == null,
                    "[hand] must be null if type is INTERACT or INTERACT_AT");
        }

        this.entityId = entityId;
        this.type = type;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.hand = hand;
        this.isSneaking = isSneaking;
    }

    @Contract(mutates = "param")
    public InteractEntityPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.type = inputStream.readVarIntEnum(Type.class);

        if (type == Type.INTERACT_AT) {
            targetX = inputStream.readFloat();
            targetY = inputStream.readFloat();
            targetZ = inputStream.readFloat();
        } else {
            this.targetX = null;
            this.targetY = null;
            this.targetZ = null;
        }

        if (type == Type.INTERACT || type == Type.INTERACT_AT) {
            this.hand = inputStream.readVarIntEnum(Hand.class);
        } else {
            this.hand = null;
        }

        this.isSneaking = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        outputStream.writeVarInt(entityId)
                .writeVarIntEnum(type);

        if (type == Type.INTERACT_AT) {
            outputStream.writeFloat(targetX)
                    .writeFloat(targetY)
                    .writeFloat(targetZ);
        }

        if (type == Type.INTERACT || type == Type.INTERACT_AT) {
            outputStream.writeVarIntEnum(hand);
        }

        outputStream.writeBoolean(isSneaking);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.INTERACT_ENTITY;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull Type getType() {

        return type;
    }

    @Contract(pure = true)
    public @UnknownNullability Float getTargetX() {

        return targetX;
    }

    @Contract(pure = true)
    public @UnknownNullability Float getTargetY() {

        return targetY;
    }

    @Contract(pure = true)
    public @UnknownNullability Float getTargetZ() {

        return targetZ;
    }

    @Contract(pure = true)
    public @UnknownNullability Hand getHand() {

        return hand;
    }

    @Contract(pure = true)
    public boolean isSneaking() {

        return isSneaking;
    }

    public enum Type {

        INTERACT,
        ATTACK,
        INTERACT_AT
    }
}
