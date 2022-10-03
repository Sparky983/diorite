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

public final class EntityAnimationPacket implements ClientBoundPacket {

    private final int entityId;
    private final Animation animation;

    @Contract(pure = true)
    public EntityAnimationPacket(final int entityId, final @NotNull Animation animation) {

        Preconditions.requireNotNull(animation, "animation");

        this.entityId = entityId;
        this.animation = animation;
    }

    @Contract(mutates = "param")
    public EntityAnimationPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.animation = inputStream.readUnsignedByteEnum(Animation.class);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeUnsignedByteEnum(animation);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.ENTITY_ANIMATION;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull Animation getAnimation() {

        return animation;
    }

    public enum Animation {

        SWING_MAIN_ARM,
        TAKE_DAMAGE,
        LEAVE_BED,
        SWING_OFFHAND,
        CRITICAL_EFFECT,
        SHARPNESS_EFFECT
    }
}
