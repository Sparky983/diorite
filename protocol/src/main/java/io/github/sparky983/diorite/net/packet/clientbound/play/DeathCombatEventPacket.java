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

import net.kyori.adventure.text.Component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class DeathCombatEventPacket implements ClientBoundPacket {

    private final int playerId;
    private final int killerEntityId;
    private final Component message;

    @Contract(pure = true)
    public DeathCombatEventPacket(final int playerId,
                                  final int killerEntityId,
                                  final @NotNull Component message) {

        Preconditions.requireNotNull(message, "message");

        this.playerId = playerId;
        this.killerEntityId = killerEntityId;
        this.message = message;
    }

    @Contract(mutates = "param")
    public DeathCombatEventPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.playerId = inputStream.readVarInt();
        this.killerEntityId = inputStream.readInt();
        this.message = inputStream.readChat();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(playerId)
                .writeInt(killerEntityId)
                .writeChat(message);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.DEATH_COMBAT_EVENT;
    }

    @Contract(pure = true)
    public int getPlayerId() {

        return playerId;
    }

    @Contract(pure = true)
    public int getKillerEntityId() {

        return killerEntityId;
    }

    @Contract(pure = true)
    public @NotNull Component getMessage() {

        return message;
    }
}
