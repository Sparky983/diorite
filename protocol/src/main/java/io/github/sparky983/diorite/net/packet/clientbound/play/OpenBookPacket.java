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
import io.github.sparky983.diorite.world.Hand;

public class OpenBookPacket implements ClientBoundPacket {

    private final Hand hand;

    @Contract(pure = true)
    public OpenBookPacket(final @NotNull Hand hand) {

        Preconditions.requireNotNull(hand, "hand");

        this.hand = hand;
    }

    @Contract(mutates = "param")
    public OpenBookPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.hand = inputStream.readVarIntEnum(Hand.class);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarIntEnum(hand);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.OPEN_BOOK;
    }

    @Contract(pure = true)
    public @NotNull Hand getHand() {

        return hand;
    }
}
