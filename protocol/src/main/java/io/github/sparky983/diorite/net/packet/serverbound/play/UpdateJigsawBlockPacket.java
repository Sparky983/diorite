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

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.BlockPosition;
import io.github.sparky983.diorite.world.Identifier;

public class UpdateJigsawBlockPacket implements ServerBoundPacket {

    private final BlockPosition location;
    private final Identifier name;
    private final Identifier target;
    private final Identifier pool;
    private final String finalState;
    private final String jointType;

    @Contract(pure = true)
    public UpdateJigsawBlockPacket(final @NotNull BlockPosition location,
                                   final @NotNull Identifier name,
                                   final @NotNull Identifier target,
                                   final @NotNull Identifier pool,
                                   final @NotNull String finalState,
                                   final @NotNull String jointType) {

        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(name, "name");
        Preconditions.requireNotNull(target, "target");
        Preconditions.requireNotNull(pool, "pool");
        Preconditions.requireNotNull(finalState, "finalState");
        Preconditions.requireNotNull(jointType, "jointType");

        this.location = location;
        this.name = name;
        this.target = target;
        this.pool = pool;
        this.finalState = finalState;
        this.jointType = jointType;
    }

    @Contract(mutates = "param")
    public UpdateJigsawBlockPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.location = inputStream.readBlockPosition();
        this.name = inputStream.readIdentifier();
        this.target = inputStream.readIdentifier();
        this.pool = inputStream.readIdentifier();
        this.finalState = inputStream.readString();
        this.jointType = inputStream.readString();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeBlockPosition(location)
                .writeIdentifier(name)
                .writeIdentifier(target)
                .writeIdentifier(pool)
                .writeString(finalState)
                .writeString(jointType);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.UPDATE_JIGSAW_BLOCK;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @NotNull Identifier getName() {

        return name;
    }

    @Contract(pure = true)
    public @NotNull Identifier getTarget() {

        return target;
    }

    @Contract(pure = true)
    public @NotNull Identifier getPool() {

        return pool;
    }

    @Contract(pure = true)
    public @NotNull String getFinalState() {

        return finalState;
    }

    @Contract(pure = true)
    public @NotNull String getJointType() {

        return jointType;
    }
}
