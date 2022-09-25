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

public class SelectAdvancementTabPacket implements ClientBoundPacket {

    private final @Nullable Identifier identifier;

    @Contract(pure = true)
    public SelectAdvancementTabPacket(final @Nullable Identifier identifier) {

        this.identifier = identifier;
    }

    @Contract(mutates = "param")
    public SelectAdvancementTabPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.identifier = inputStream.readOptional(StreamIn::readIdentifier).orElse(null);
    }

    @Override
    public void write(@NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeNullable(identifier, StreamOut::writeIdentifier);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SELECT_ADVANCEMENT_TAB;
    }

    @Contract(pure = true)
    public @Nullable Identifier getIdentifier() {

        return identifier;
    }
}
