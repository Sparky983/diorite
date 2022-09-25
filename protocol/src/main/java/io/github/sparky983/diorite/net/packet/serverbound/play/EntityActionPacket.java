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

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class EntityActionPacket implements ServerBoundPacket {

    private final int entityId;
    private final Action action;
    private final int jumpBoost;

    @Contract(pure = true)
    public EntityActionPacket(final int entityId,
                              final @NotNull Action action,
                              final int jumpBoost) {

        Preconditions.requireNotNull(action, "action");

        this.entityId = entityId;
        this.action = action;
        this.jumpBoost = jumpBoost;
    }

    @Contract(mutates = "param")
    public EntityActionPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.action = inputStream.readVarIntEnum(Action.class);
        this.jumpBoost = inputStream.readVarInt();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeVarIntEnum(action)
                .writeVarInt(jumpBoost);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.ENTITY_ACTION;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull Action getAction() {

        return action;
    }

    @Contract(pure = true)
    public int getJumpBoost() {

        return jumpBoost;
    }

    public enum Action {

        START_SNEAKING,
        STOP_SNEAKING,
        LEAVE_BED,
        START_SPRINTING,
        STOP_SPRINTING,
        START_JUMP_WITH_HORSE,
        STOP_JUMP_WITH_HORSE,
        OPEN_HORSE_INVENTORY,
        START_FLYING_WITH_ELYTRA
    }
}
