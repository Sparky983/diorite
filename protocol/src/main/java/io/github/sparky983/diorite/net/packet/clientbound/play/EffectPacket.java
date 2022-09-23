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
import io.github.sparky983.diorite.world.BlockPosition;

@ApiStatus.Experimental
public class EffectPacket implements ClientBoundPacket {

    private final int effectId; // TODO(Sparky983): enum
    private final BlockPosition location;
    private final int data;
    private final boolean disableRelativeVolume;

    @Contract(pure = true)
    public EffectPacket(final int effectId,
                        final @NotNull BlockPosition location,
                        final int data,
                        final boolean disableRelativeVolume) {

        Preconditions.requireNotNull(location, "location");

        this.effectId = effectId;
        this.location = location;
        this.data = data;
        this.disableRelativeVolume = disableRelativeVolume;
    }

    @Contract(mutates = "param")
    public EffectPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.effectId = inputStream.readInt();
        this.location = inputStream.readBlockPosition();
        this.data = inputStream.readInt();
        this.disableRelativeVolume = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeInt(effectId)
                .writeBlockPosition(location)
                .writeInt(data)
                .writeBoolean(disableRelativeVolume);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.EFFECT;
    }

    @Contract(pure = true)
    public int getEffectId() {

        return effectId;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }

    @Contract(pure = true)
    public int getData() {

        return data;
    }

    @Contract(pure = true)
    public boolean isDisableRelativeVolume() {

        return disableRelativeVolume;
    }
}
