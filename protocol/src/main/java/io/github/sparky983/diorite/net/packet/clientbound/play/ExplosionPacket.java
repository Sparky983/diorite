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
import io.github.sparky983.diorite.world.Position;
import io.github.sparky983.diorite.world.Vector;

public final class ExplosionPacket implements ClientBoundPacket {

    private final Position location; // encoded as 3 floats
    private final float strength;
    private final byte[] records;
    private final Vector playerMotion; // encoded as 3 floats

    @Contract(pure = true)
    public ExplosionPacket(final @NotNull Position location,
            final float strength,
            final byte @NotNull [] records,
            final @NotNull Vector playerMotion) {

        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(records, "records");
        Preconditions.requireNotNull(playerMotion, "playerMotion");

        this.location = location;
        this.strength = strength;
        this.records = records;
        this.playerMotion = playerMotion;
    }

    @Contract(mutates = "param")
    public ExplosionPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        final float x = inputStream.readFloat();
        final float y = inputStream.readFloat();
        final float z = inputStream.readFloat();

        this.location = Position.of(x, y, z);
        this.strength = inputStream.readFloat();
        this.records = inputStream.readByteList();

        final float playerMotionX = inputStream.readFloat();
        final float playerMotionY = inputStream.readFloat();
        final float playerMotionZ = inputStream.readFloat();

        this.playerMotion = Vector.of(playerMotionX, playerMotionY, playerMotionZ);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeFloat((float) location.getX())
                .writeFloat((float) location.getY())
                .writeFloat((float) location.getZ())
                .writeFloat(strength)
                .writeByteList(records)
                .writeFloat((float) playerMotion.getX())
                .writeFloat((float) playerMotion.getY())
                .writeFloat((float) playerMotion.getZ());
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.EXPLOSION;
    }

    @Contract(pure = true)
    public @NotNull Position getLocation() {

        return location;
    }

    @Contract(pure = true)
    public float getStrength() {

        return strength;
    }

    @Contract(pure = true)
    public byte @NotNull [] getRecords() {

        return records;
    }

    @Contract(pure = true)
    public @NotNull Vector getPlayerMotion() {

        return playerMotion;
    }
}
