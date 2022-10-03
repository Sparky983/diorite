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
import org.jetbrains.annotations.Range;

import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.BlockPosition;

public final class UpdateStructureBlockPacket implements ServerBoundPacket {

    private static final byte IGNORE_ENTITIES_BIT = 0b00000001;
    private static final byte SHOW_AIR_BIT = 0b00000010;
    private static final byte SHOW_BOUNDING_BOX_BIT = 0b00000100;

    private static final byte MIN_OFFSET = -32;
    private static final byte MAX_OFFSET = 32;
    private static final byte MIN_SIZE = 0;
    private static final byte MAX_SIZE = 32;

    private final BlockPosition location;
    private final Action action;
    private final Mode mode;
    private final String name;
    private final byte offsetX;
    private final byte offsetY;
    private final byte offsetZ;
    private final byte sizeX;
    private final byte sizeY;
    private final byte sizeZ;
    private final Mirror mirror;
    private final Rotation rotation;
    private final String metadata;
    private final float integrity;
    private final long seed;
    private final byte flags;

    @Contract(pure = true)
    public UpdateStructureBlockPacket(
            final @NotNull BlockPosition location,
            final @NotNull Action action,
            final @NotNull Mode mode,
            final @NotNull String name,
            final @Range(from = MIN_OFFSET, to = MAX_OFFSET) byte offsetX,
            final @Range(from = MIN_OFFSET, to = MAX_OFFSET) byte offsetY,
            final @Range(from = MIN_OFFSET, to = MAX_OFFSET) byte offsetZ,
            final @Range(from = MIN_SIZE, to = MAX_SIZE) byte sizeX,
            final @Range(from = MIN_SIZE, to = MAX_SIZE) byte sizeY,
            final @Range(from = MIN_SIZE, to = MAX_SIZE) byte sizeZ,
            final @NotNull Mirror mirror,
            final @NotNull Rotation rotation,
            final @NotNull String metadata,
            final float integrity,
            final long seed,
            final byte flags) {

        Preconditions.requireNotNull(action, "action");
        Preconditions.requireNotNull(mode, "mode");
        Preconditions.requireNotNull(name, "name");
        Preconditions.requireRange(offsetX, MIN_OFFSET, MAX_OFFSET, "offsetX");
        Preconditions.requireRange(offsetY, MIN_OFFSET, MAX_OFFSET, "offsetY");
        Preconditions.requireRange(offsetZ, MIN_OFFSET, MAX_OFFSET, "offsetZ");
        Preconditions.requireRange(sizeX, MIN_SIZE, MAX_SIZE, "sizeX");
        Preconditions.requireRange(sizeY, MIN_SIZE, MAX_SIZE, "sizeY");
        Preconditions.requireRange(sizeZ, MIN_SIZE, MAX_SIZE, "sizeZ");
        Preconditions.requireNotNull(mirror, "mirror");
        Preconditions.requireNotNull(rotation, "rotation");
        Preconditions.requireNotNull(metadata, "metadata");
        Preconditions.requireTrue(integrity >= 0 && integrity <= 1, "[integrity] must be between 0-1");

        this.location = location;
        this.action = action;
        this.mode = mode;
        this.name = name;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.mirror = mirror;
        this.rotation = rotation;
        this.metadata = metadata;
        this.integrity = integrity;
        this.seed = seed;
        this.flags = flags;
    }

    @Contract(mutates = "param")
    public UpdateStructureBlockPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.location = inputStream.readBlockPosition();
        this.action = inputStream.readVarIntEnum(Action.class);
        this.mode = inputStream.readVarIntEnum(Mode.class);
        this.name = inputStream.readString();

        this.offsetX = inputStream.readByte();
        this.offsetY = inputStream.readByte();
        this.offsetZ = inputStream.readByte();

        if (offsetX < MIN_OFFSET || offsetX > MAX_OFFSET ||
                offsetY < MIN_OFFSET || offsetY > MAX_OFFSET ||
                offsetZ < MIN_OFFSET || offsetZ > MAX_OFFSET) {
            throw new DecodeException("Illegal offset");
        }

        this.sizeX = inputStream.readByte();
        this.sizeY = inputStream.readByte();
        this.sizeZ = inputStream.readByte();

        if (sizeX < MIN_SIZE || sizeX > MAX_SIZE ||
                sizeY < MIN_SIZE || sizeY > MAX_SIZE ||
                sizeZ < MIN_SIZE || sizeZ > MAX_SIZE) {
            throw new DecodeException("Illegal size");
        }

        this.mirror = inputStream.readByteEnum(Mirror.class);
        this.rotation = inputStream.readByteEnum(Rotation.class);
        this.metadata = inputStream.readString();

        this.integrity = inputStream.readFloat();

        if (integrity < 0 || integrity > 1) {
            throw new DecodeException("Illegal integrity");
        }

        this.seed = inputStream.readVarLong();
        this.flags = inputStream.readByte();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeBlockPosition(location)
                .writeVarIntEnum(action)
                .writeVarIntEnum(mode)
                .writeString(name)
                .writeByte(offsetX)
                .writeByte(offsetY)
                .writeByte(offsetZ)
                .writeByte(sizeX)
                .writeByte(sizeY)
                .writeByte(sizeZ)
                .writeVarIntEnum(mirror)
                .writeVarIntEnum(rotation)
                .writeString(metadata)
                .writeFloat(integrity)
                .writeVarLong(seed)
                .writeByte(flags);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.UPDATE_STRUCTURE_BLOCK;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @NotNull Action getAction() {

        return action;
    }

    @Contract(pure = true)
    public @NotNull Mode getMode() {

        return mode;
    }

    @Contract(pure = true)
    public @NotNull String getName() {

        return name;
    }

    @Contract(pure = true)
    public @Range(from = MIN_OFFSET, to = MAX_OFFSET) byte getOffsetX() {

        return offsetX;
    }

    @Contract(pure = true)
    public @Range(from = MIN_OFFSET, to = MAX_OFFSET) byte getOffsetY() {

        return offsetY;
    }

    @Contract(pure = true)
    public @Range(from = MIN_OFFSET, to = MAX_OFFSET) byte getOffsetZ() {

        return offsetZ;
    }

    @Contract(pure = true)
    public @Range(from = MIN_SIZE, to = MAX_SIZE) byte getSizeX() {

        return sizeX;
    }

    @Contract(pure = true)
    public @Range(from = MIN_SIZE, to = MAX_SIZE) byte getSizeY() {

        return sizeY;
    }

    @Contract(pure = true)
    public @Range(from = MIN_SIZE, to = MAX_SIZE) byte getSizeZ() {

        return sizeZ;
    }

    @Contract(pure = true)
    public @NotNull Mirror getMirror() {

        return mirror;
    }


    @Contract(pure = true)
    public @NotNull Rotation getRotation() {

        return rotation;
    }


    @Contract(pure = true)
    public @NotNull String getMetadata() {

        return metadata;
    }


    @Contract(pure = true)
    public float getIntegrity() {

        return integrity;
    }


    @Contract(pure = true)
    public long getSeed() {

        return seed;
    }


    @Contract(pure = true)
    public byte getFlags() {

        return flags;
    }

    @Contract(pure = true)
    public boolean isIgnoringEntities() {

        return (flags & IGNORE_ENTITIES_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isShowAir() {

        return (flags & SHOW_AIR_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isShowBoundingBox() {

        return (flags & SHOW_BOUNDING_BOX_BIT) != 0;
    }

    public enum Action {

        UPDATE_DATA,
        SAVE_STRUCTURE,
        LOAD_STRUCTURE,
        DETECT_SIZE
    }

    public enum Mode {

        SAVE,
        LOAD,
        CORNER,
        DATA
    }

    public enum Mirror {

        NONE,
        LEFT_RIGHT,
        FRONT_BACK
    }

    public enum Rotation {

        NONE,
        CLOCKWISE_90,
        CLOCKWISE_180,
        COUNTERCLOCKWISE_90
    }
}
