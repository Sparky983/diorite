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

public class EntityEffectPacket implements ClientBoundPacket {

    private static final byte AMBIENT_BIT = 0b00000001;
    private static final byte SHOW_PARTICLES_BIT = 0b00000010;
    private static final byte SHOW_ICON_BIT = 0b00000100;

    private final int entityId;
    private final byte effectId;
    private final byte amplifier;
    private final int duration;
    private final byte flags;

    @Contract(pure = true)
    public EntityEffectPacket(final int entityId, final byte effectId, final byte amplifier, final int duration, final byte flags) {

        this.entityId = entityId;
        this.effectId = effectId;

        this.amplifier = amplifier;
        this.duration = duration;
        this.flags = flags;
    }

    @Contract(mutates = "param")
    public EntityEffectPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.effectId = inputStream.readByte();
        this.amplifier = inputStream.readByte();
        this.duration = inputStream.readVarInt();
        this.flags = inputStream.readByte();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeByte(effectId)
                .writeByte(amplifier)
                .writeVarInt(duration)
                .writeByte(flags);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.ENTITY_EFFECT;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public byte getEffectId() {

        return effectId;
    }

    @Contract(pure = true)
    public byte getAmplifier() {

        return amplifier;
    }

    @Contract(pure = true)
    public int getDuration() {

        return duration;
    }

    @Contract(pure = true)
    public byte getFlags() {

        return flags;
    }

    @Contract(pure = true)
    public boolean isAmbient() {

        return (flags & AMBIENT_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean showsParticles() {

        return (flags & SHOW_PARTICLES_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean showsIcon() {

        return (flags & SHOW_ICON_BIT) != 0;
    }
}
