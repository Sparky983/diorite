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

public final class WorldBorderWarningReachPacket implements ClientBoundPacket {

    private final int warningDistance; // in blocks

    @Contract(pure = true)
    public WorldBorderWarningReachPacket(final int warningDistance) {

        this.warningDistance = warningDistance;
    }

    @Contract(mutates = "param")
    public WorldBorderWarningReachPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.warningDistance = inputStream.readVarInt();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(warningDistance);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.WORLD_BORDER_WARNING_REACH;
    }

    @Contract(pure = true)
    public int getWarningDistance() {

        return warningDistance;
    }
}
