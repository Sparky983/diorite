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

public class PlayerAbilitiesPacket implements ClientBoundPacket {

    private static final byte INVULNERABLE_BIT = 0b00000001;
    private static final byte FLYING_BIT = 0b00000010;
    private static final byte ALLOW_FLYING_BIT = 0b00000100;
    private static final byte CREATIVE_MODE_BIT = 0b00001000;

    private final byte flags;
    private final float flyingSpeed;
    private final float fieldOfViewModifier;

    @Contract(pure = true)
    public PlayerAbilitiesPacket(final byte flags,
                                 final float flyingSpeed,
                                 final float fieldOfViewModifier) {

        this.flags = flags;
        this.flyingSpeed = flyingSpeed;
        this.fieldOfViewModifier = fieldOfViewModifier;
    }

    @Contract(mutates = "param")
    public PlayerAbilitiesPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.flags = inputStream.readByte();
        this.flyingSpeed = inputStream.readFloat();
        this.fieldOfViewModifier = inputStream.readFloat();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeByte(flags)
                .writeFloat(flyingSpeed)
                .writeFloat(fieldOfViewModifier);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.PLAYER_ABILITIES;
    }

    @Contract(pure = true)
    public byte getFlags() {

        return flags;
    }

    @Contract(pure = true)
    public boolean isInvulnerable() {

        return (flags & INVULNERABLE_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isFlying() {

        return (flags & FLYING_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean allowsFlying() {

        return (flags & ALLOW_FLYING_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isCreativeMode() {

        return (flags & CREATIVE_MODE_BIT) != 0;
    }

    @Contract(pure = true)
    public float getFlyingSpeed() {

        return flyingSpeed;
    }

    @Contract(pure = true)
    public float getFieldOfViewModifier() {

        return fieldOfViewModifier;
    }
}
