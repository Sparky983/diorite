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

import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class SetExperiencePacket implements ClientBoundPacket {

    private final float experienceBar;
    private final int level;
    private final int totalExperience;

    @Contract(pure = true)
    public SetExperiencePacket(final float experienceBar, final int level, final int totalExperience) {

        if (experienceBar < 0 || experienceBar > 1) {
            throw new IllegalArgumentException("Experience bar must be between 0 and 1");
        }

        this.experienceBar = experienceBar;
        this.level = level;
        this.totalExperience = totalExperience;
    }

    @Contract(mutates = "param")
    public SetExperiencePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.experienceBar = inputStream.readFloat();

        if (this.experienceBar < 0 || this.experienceBar > 1) {
            throw new DecodeException("Experience bar must be between 0 and 1");
        }

        this.level = inputStream.readVarInt();
        this.totalExperience = inputStream.readVarInt();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeFloat(experienceBar)
                .writeVarInt(level)
                .writeVarInt(totalExperience);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SET_EXPERIENCE;
    }

    @Contract(pure = true)
    public float getExperienceBar() {

        return experienceBar;
    }

    @Contract(pure = true)
    public int getLevel() {

        return level;
    }

    @Contract(pure = true)
    public int getTotalExperience() {

        return totalExperience;
    }
}
