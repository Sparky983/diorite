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
import org.jetbrains.annotations.Nullable;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Identifier;

public final class StopSoundPacket implements ClientBoundPacket {

    private static final byte SOURCE_BIT = 0b00000001;
    private static final byte SOUND_BIT = 0b00000010;

    private final @Nullable Source source;
    private final @Nullable Identifier identifier;

    @Contract(pure = true)
    public StopSoundPacket(final @Nullable Source source, final @Nullable Identifier identifier) {

        this.source = source;
        this.identifier = identifier;
    }

    @Contract(mutates = "param")
    public StopSoundPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream);

        final byte flags = inputStream.readByte();

        if ((flags & SOURCE_BIT) != 0) {
            this.source = inputStream.readVarIntEnum(Source.class);
        } else {
            this.source = null;
        }

        if ((flags & SOUND_BIT) != 0) {
            this.identifier = inputStream.readIdentifier();
        } else {
            this.identifier = null;
        }
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream);

        byte flags = 0;

        if (source != null) {
            flags |= SOURCE_BIT;
        }

        if (identifier != null) {
            flags |= SOUND_BIT;
        }

        outputStream.writeByte(flags);

        if (source != null) {
            outputStream.writeVarIntEnum(source);
        }

        if (identifier != null) {
            outputStream.writeIdentifier(identifier);
        }
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.STOP_SOUND;
    }

    @Contract(pure = true)
    public @Nullable Source getSource() {

        return source;
    }

    @Contract(pure = true)
    public @Nullable Identifier getIdentifier() {

        return identifier;
    }

    public enum Source {

        MASTER,
        MUSIC,
        RECORD,
        WEATHER,
        BLOCKS,
        HOSTILE,
        NEUTRAL,
        PLAYERS,
        AMBIENT,
        VOICE
    }
}
