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
import io.github.sparky983.diorite.world.Difficulty;

public final class ServerDifficultyPacket implements ClientBoundPacket {

    private final Difficulty difficulty;
    private final boolean isLocked;

    @Contract(pure = true)
    public ServerDifficultyPacket(final @NotNull Difficulty difficulty, final boolean isLocked) {

        Preconditions.requireNotNull(difficulty, "difficulty");

        this.difficulty = difficulty;
        this.isLocked = isLocked;
    }

    @Contract(mutates = "param")
    public ServerDifficultyPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.difficulty = inputStream.readByteEnum(Difficulty.class);
        this.isLocked = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeByteEnum(difficulty)
                .writeBoolean(isLocked);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SERVER_DIFFICULTY;
    }

    @Contract(pure = true)
    public @NotNull Difficulty getDifficulty() {

        return difficulty;
    }

    @Contract(pure = true)
    public boolean isLocked() {

        return isLocked;
    }
}
