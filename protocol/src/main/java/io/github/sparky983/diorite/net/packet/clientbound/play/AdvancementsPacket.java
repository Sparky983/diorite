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

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class AdvancementsPacket implements ClientBoundPacket {

    // TODO(Sparky983): Parse this, can't be bothered

    private final byte[] data;

    @ApiStatus.Experimental
    @Contract(pure = true)
    public AdvancementsPacket(final byte @NotNull [] data) {

        Preconditions.requireNotNull(data, "data");

        this.data = data;
    }

    @Contract(mutates = "param")
    public AdvancementsPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.data = inputStream.readAllBytes();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeBytes(data);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.ADVANCEMENTS;
    }

    @ApiStatus.Experimental
    @Contract(pure = true)
    public byte @NotNull [] getData() {

        return data;
    }
}
