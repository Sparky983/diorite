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

public class ChangeGameStatePacket implements ClientBoundPacket {

    private final Reason reason;
    private final float value;

    @Contract(pure = true)
    public ChangeGameStatePacket(final @NotNull Reason reason, final float value) {

        this.reason = reason;
        this.value = value;
    }

    @Contract(mutates = "param")
    public ChangeGameStatePacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.reason = inputStream.readUByteEnum(Reason.class);
        this.value = inputStream.readFloat();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeUByteEnum(reason)
                .writeFloat(value);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.CHANGE_GAME_STATE;
    }

    @Contract(pure = true)
    public @NotNull Reason getReason() {

        return reason;
    }

    @Contract(pure = true)
    public float getValue() {

        return value;
    }

    public enum Reason {

        NO_RESPAWN_BLOCK_AVAILABLE,
        END_RAINING,
        BEGIN_RAINING,
        CHANGE_GAMEMODE,
        WIN_GAME,
        DEMO_EVENT,
        ARROW_HIT_PLAYER,
        RAIN_LEVEL_CHANGE,
        THUNDER_LEVEL_CHANGE,
        PLAY_PUFFERFISH_STING_SOUND,
        PLAY_ELDER_GUARDIAN_MOB_APPEARANCE, // (effect and sound)
        ENABLE_RESPAWN_SCREEN
    }
}
