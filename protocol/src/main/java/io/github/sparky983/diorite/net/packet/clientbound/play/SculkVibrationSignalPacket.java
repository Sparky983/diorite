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

public final class SculkVibrationSignalPacket implements ClientBoundPacket {

    private final Position sourceLocation;
    private final Identifier destinationIdentifier;
    // private final Varies destination
    // TODO(Sparky983): figure out destination identifier
    private final int arrivalTicks;

    @Contract(pure = true)
    public SculkVibrationSignalPacket(
            final @NotNull Position sourceLocation,
            final @NotNull Identifier destinationIdentifier,
            final int arrivalTicks) {

        Preconditions.requireNotNull(sourceLocation, "sourceLocation");
        Preconditions.requireNotNull(destinationIdentifier, "destinationIdentifier");

        this.sourceLocation = sourceLocation;
        this.destinationIdentifier = destinationIdentifier;
        this.arrivalTicks = arrivalTicks;
    }

    @Contract(mutates = "param")
    public SculkVibrationSignalPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.sourceLocation = inputStream.readPosition();
        this.destinationIdentifier = inputStream.readIdentifier();
        // this.destination = inputStream.readPosition();
        // for blocks
        // this.destination = inputStream.readVarInt();
        // for entities
        this.arrivalTicks = inputStream.readVarInt();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writePosition(sourceLocation)
                .writeIdentifier(destinationIdentifier)
                // outputStream.writePosition(destination);
                // for blocks
                // outputStream.writeVarInt(destination);
                // for entities
                .writeVarInt(arrivalTicks);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SCULK_VIBRATION_SIGNAL;
    }

    @Contract(pure = true)
    public @NotNull Position getSourceLocation() {

        return sourceLocation;
    }

    @Contract(pure = true)
    public @NotNull Identifier getDestinationIdentifier() {

        return destinationIdentifier;
    }

    @Contract(pure = true)
    public int getArrivalTicks() {

        return arrivalTicks;
    }
}
