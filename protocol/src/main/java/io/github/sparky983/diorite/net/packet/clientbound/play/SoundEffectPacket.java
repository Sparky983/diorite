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
import io.github.sparky983.diorite.world.Position;

public final class SoundEffectPacket implements ClientBoundPacket {

    private static final int POSITION_SCALING_FACTOR = 8; // 3 bits

    private final int soundId;
    private final int soundCategory;
    private final Position location;
    private final float volume;
    private final float pitch;

    @Contract(pure = true)
    public SoundEffectPacket(final int soundId,
            final int soundCategory,
            final @NotNull Position location,
            final float volume,
            final float pitch) {

        Preconditions.requireNotNull(location, "location");

        if (volume < 0 || volume > 1) {
            throw new IllegalArgumentException("Volume must be between 0 and 1");
        }

        if (pitch < 0.5 || pitch > 2) {
            throw new IllegalArgumentException("Pitch must be between 0 and 2");
        }

        this.soundId = soundId;
        this.soundCategory = soundCategory;
        this.location = location;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Contract(mutates = "param")
    public SoundEffectPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.soundId = inputStream.readVarInt();
        this.soundCategory = inputStream.readVarInt();
        this.location = decodeFixedPointNumberPosition(inputStream);

        this.volume = inputStream.readFloat();
        if (this.volume < 0 || this.volume > 1) {
            throw new IllegalArgumentException("Volume must be between 0 and 1");
        }

        this.pitch = inputStream.readFloat();
        if (this.pitch < 0.5 || this.pitch > 2) {
            throw new IllegalArgumentException("Pitch must be between 0 and 2");
        }
    }

    private @NotNull Position decodeFixedPointNumberPosition(
            final @NotNull StreamIn inputStream) {

        final int x = inputStream.readInt() / POSITION_SCALING_FACTOR;
        final int y = inputStream.readInt() / POSITION_SCALING_FACTOR;
        final int z = inputStream.readInt() / POSITION_SCALING_FACTOR;

        return Position.of(x, y, z);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(soundId)
                .writeVarInt(soundCategory);

        writeFixedPointNumberPosition(location, outputStream);

        outputStream.writeFloat(volume)
                .writeFloat(pitch);
    }

    private void writeFixedPointNumberPosition(final @NotNull Position position,
            final @NotNull StreamOut outputStream) {

        outputStream.writeInt((int) (position.getX() * POSITION_SCALING_FACTOR))
                .writeInt((int) (position.getY() * POSITION_SCALING_FACTOR))
                .writeInt((int) (position.getZ() * POSITION_SCALING_FACTOR));
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SOUND_EFFECT;
    }

    @Contract(pure = true)
    public int getSoundId() {

        return soundId;
    }

    @Contract(pure = true)
    public int getSoundCategory() {

        return soundCategory;
    }

    @Contract(pure = true)
    public @NotNull Position getLocation() {

        return location;
    }

    @Contract(pure = true)
    public float getVolume() {

        return volume;
    }

    @Contract(pure = true)
    public float getPitch() {

        return pitch;
    }
}
