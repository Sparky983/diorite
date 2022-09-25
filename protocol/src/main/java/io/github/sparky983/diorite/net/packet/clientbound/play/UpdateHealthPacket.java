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

import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class UpdateHealthPacket implements ClientBoundPacket {

    private final float health;
    private final int food;
    private final float saturation; // TODO(Sparky983): more information needed

    @Contract(pure = true)
    public UpdateHealthPacket(final float health, final int food, final float saturation) {

        if (health > 20) {
            throw new IllegalArgumentException("Health cannot be greater than 20");
        }

        this.health = health;
        this.food = food;
        this.saturation = saturation;
    }

    @Contract(mutates = "param")
    public UpdateHealthPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.health = inputStream.readFloat();

        if (this.health > 20) {
            throw new DecodeException("Health cannot be greater than 20");
        }

        this.food = inputStream.readVarInt();

        this.saturation = inputStream.readFloat();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeFloat(health)
                .writeVarInt(food)
                .writeFloat(saturation);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.UPDATE_HEALTH;
    }

    @Contract(pure = true)
    public float getHealth() {

        return health;
    }

    @Contract(pure = true)
    public int getFood() {

        return food;
    }

    @Contract(pure = true)
    public float getSaturation() {

        return saturation;
    }
}
