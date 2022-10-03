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

package io.github.sparky983.diorite.net.packet.serverbound.play;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class SetBeaconEffectPacket implements ServerBoundPacket {

    private final int primaryEffect;
    private final int secondaryEffect;

    @Contract(pure = true)
    public SetBeaconEffectPacket(final int primaryEffect, final int secondaryEffect) {

        this.primaryEffect = primaryEffect;
        this.secondaryEffect = secondaryEffect;
    }

    @Contract(mutates = "param")
    public SetBeaconEffectPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.primaryEffect = inputStream.readVarInt();
        this.secondaryEffect = inputStream.readVarInt();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(primaryEffect)
                .writeVarInt(secondaryEffect);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.SET_BEACON_EFFECT;
    }

    @Contract(pure = true)
    public int getPrimaryEffect() {

        return primaryEffect;
    }

    @Contract(pure = true)
    public int getSecondaryEffect() {

        return secondaryEffect;
    }
}
