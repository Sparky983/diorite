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

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Position;
import io.github.sparky983.diorite.world.Vector;

@ApiStatus.Experimental
public class ParticlePacket implements ClientBoundPacket {

    private final int particleId; // TODO(Sparky983): enum
    private final boolean longDistance;
    private final Position position;
    private final Vector offset;
    private final float particleData;
    private final int particleCount;
    private final byte[] data;

    @Contract(pure = true)
    public ParticlePacket(final int particleId,
                          final boolean longDistance,
                          final @NotNull Position position,
                          final @NotNull Vector offset,
                          final float particleData,
                          final int particleCount,
                          final byte @NotNull [] data) {

        Preconditions.requireNotNull(position, "position");
        Preconditions.requireNotNull(offset, "offset");
        Preconditions.requireNotNull(data, "data");

        this.particleId = particleId;
        this.longDistance = longDistance;
        this.position = position;
        this.offset = offset;
        this.particleData = particleData;
        this.particleCount = particleCount;
        this.data = data;
    }

    @Contract(mutates = "param")
    public ParticlePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.particleId = inputStream.readInt();
        this.longDistance = inputStream.readBoolean();
        this.position = inputStream.readPosition();

        final float offsetX = inputStream.readFloat();
        final float offsetY = inputStream.readFloat();
        final float offsetZ = inputStream.readFloat();
        this.offset = Vector.of(offsetX, offsetY, offsetZ);

        this.particleData = inputStream.readFloat();
        this.particleCount = inputStream.readInt();
        this.data = inputStream.readAllBytes();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeInt(particleId)
                .writeBoolean(longDistance)
                .writePosition(position)
                .writeFloat((float) offset.getX())
                .writeFloat((float) offset.getY())
                .writeFloat((float) offset.getZ())
                .writeFloat(particleData)
                .writeInt(particleCount)
                .writeBytes(data);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.PARTICLE;
    }

    @Contract(pure = true)
    public int getParticleId() {

        return particleId;
    }

    @Contract(pure = true)
    public boolean isLongDistance() {

        return longDistance;
    }

    @Contract(pure = true)
    public @NotNull Position getPosition() {

        return position;
    }

    @Contract(pure = true)
    public @NotNull Vector getOffset() {

        return offset;
    }

    @Contract(pure = true)
    public float getParticleData() {

        return particleData;
    }

    @Contract(pure = true)
    public int getParticleCount() {

        return particleCount;
    }

    @Contract(pure = true)
    public byte @NotNull [] getData() {

        return data;
    }
}
