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
import org.jetbrains.annotations.Range;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Position;

public class BlockBreakAnimationPacket implements ClientBoundPacket {

    private final int entityId;
    private final Position location;
    private final byte destroyStage;

    @Contract(pure = true)
    public BlockBreakAnimationPacket(final int entityId,
                                     final @NotNull Position location,
                                     final @Range(from = 0, to = 9) byte destroyStage) {

        Preconditions.requireNotNull(location, "location");
        Preconditions.requireRange(destroyStage, 0, 9, "destroyStage");

        this.entityId = entityId;
        this.location = location;
        this.destroyStage = destroyStage;
    }

    @Contract(mutates = "param")
    public BlockBreakAnimationPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.location = inputStream.readPosition();
        this.destroyStage = inputStream.readByte();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId);
        outputStream.writePosition(location);
        outputStream.writeByte(destroyStage);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.BLOCK_BREAK_ANIMATION;
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
    public @Range(from = 0, to = 9) byte getDestroyStage() {

        return destroyStage;
    }
}
