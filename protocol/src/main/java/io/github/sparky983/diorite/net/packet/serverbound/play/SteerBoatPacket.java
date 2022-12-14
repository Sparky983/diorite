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

public final class SteerBoatPacket implements ServerBoundPacket {

    private final boolean leftPaddleIsTurning;
    private final boolean rightPaddleIsTurning;

    @Contract(pure = true)
    public SteerBoatPacket(final boolean leftPaddleIsTurning, final boolean rightPaddleIsTurning) {

        this.leftPaddleIsTurning = leftPaddleIsTurning;
        this.rightPaddleIsTurning = rightPaddleIsTurning;
    }

    @Contract(mutates = "param")
    public SteerBoatPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.leftPaddleIsTurning = inputStream.readBoolean();
        this.rightPaddleIsTurning = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeBoolean(leftPaddleIsTurning)
                .writeBoolean(rightPaddleIsTurning);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.STEER_BOAT;
    }

    @Contract(pure = true)
    public boolean isLeftPaddleIsTurning() {

        return leftPaddleIsTurning;
    }

    @Contract(pure = true)
    public boolean isRightPaddleIsTurning() {

        return rightPaddleIsTurning;
    }
}
