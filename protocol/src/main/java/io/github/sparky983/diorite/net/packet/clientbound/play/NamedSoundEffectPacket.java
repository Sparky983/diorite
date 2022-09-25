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
import io.github.sparky983.diorite.world.Identifier;
import io.github.sparky983.diorite.world.Position;

public class NamedSoundEffectPacket implements ClientBoundPacket {

    private static final int POSITION_SCALING_FACTOR = 8; // 3 bits

    private final Identifier soundName;
    private final Category category;

    // Encoded as a fixed-point number with 3 bits of fractional part
    private final Position position;

    private final float volume;
    private final float pitch;

    @Contract(pure = true)
    public NamedSoundEffectPacket(final @NotNull Identifier soundName,
                                  final @NotNull Category category,
                                  final @NotNull Position position,
                                  final float volume,
                                  final float pitch) {

        Preconditions.requireNotNull(soundName, "soundName");
        Preconditions.requireNotNull(category, "category");
        Preconditions.requireNotNull(position, "position");

        this.soundName = soundName;
        this.category = category;
        this.position = position;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Contract(mutates = "param")
    public NamedSoundEffectPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.soundName = inputStream.readIdentifier();
        this.category = inputStream.readVarIntEnum(Category.class);
        this.position = decodeFixedPointNumberPosition(inputStream);
        this.volume = inputStream.readFloat();
        this.pitch = inputStream.readFloat();
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

        outputStream.writeIdentifier(soundName)
                .writeVarIntEnum(category);

        writeFixedPointNumberPosition(position, outputStream);

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

        return ClientBoundPacketId.Play.NAMED_SOUND_EFFECT;
    }

    @Contract(pure = true)
    public @NotNull Identifier getSoundName() {

        return soundName;
    }

    @Contract(pure = true)
    public @NotNull Category getCategory() {

        return category;
    }

    @Contract(pure = true)
    public @NotNull Position getPosition() {

        return position;
    }

    @Contract(pure = true)
    public float getVolume() {

        return volume;
    }

    @Contract(pure = true)
    public float getPitch() {

        return pitch;
    }

    public enum Category {

        MASTER,
        MUSIC,
        RECORDS,
        WEATHER,
        BLOCKS,
        HOSTILE,
        NEUTRAL,
        PLAYERS,
        AMBIENT,
        VOICE
    }
}
