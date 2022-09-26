/*
 * Copyright 2022 Sparky983
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

package io.github.sparky983.diorite.io;

import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.BinaryTagTypes;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.BlockPosition;
import io.github.sparky983.diorite.world.Direction;
import io.github.sparky983.diorite.world.Identifier;
import io.github.sparky983.diorite.world.Position;
import io.github.sparky983.diorite.world.Velocity;

final class StreamOutImpl implements StreamOut {

    private static final GsonComponentSerializer COMPONENT_SERIALIZER =
            GsonComponentSerializer.gson();

    private static final BinaryTagIO.Writer BINARY_TAG_WRITER = BinaryTagIO.writer();

    private final DataOutputStream outputStream;

    public StreamOutImpl(final @NotNull DataOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        this.outputStream = outputStream;
    }

    @Override
    public @NotNull OutputStream toOutputStream() {

        return outputStream;
    }

    @Override
    public @NotNull StreamOut writeBoolean(final boolean data) {

        try {
            outputStream.writeBoolean(data);
            return this;
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @NotNull StreamOut writeByte(final byte data) {

        try {
            outputStream.writeByte(data);
            return this;
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @NotNull StreamOut writeUnsignedByte(final @Range(from = 0, to = 0xFF) int data) {

        Preconditions.requireRange(data, 0, 0xFF, "data");

        try {
            outputStream.writeByte(data + Byte.MIN_VALUE);
            return this;
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @NotNull StreamOut writeShort(final short data) {

        try {
            outputStream.writeShort(data);
            return this;
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @NotNull StreamOut writeUnsignedShort(
            final @Range(from = 0, to = 0xFFFF) int data) {

        Preconditions.requireRange(data, 0, 0xFFFF, "data");

        try {
            outputStream.writeShort(data + Short.MIN_VALUE);
            return this;
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @NotNull StreamOut writeInt(final int data) {

        try {
            outputStream.writeInt(data);
            return this;
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @NotNull StreamOut writeLong(final long data) {

        try {
            outputStream.writeLong(data);
            return this;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull StreamOut writeFloat(final float data) {

        try {
            outputStream.writeFloat(data);
            return this;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull StreamOut writeDouble(final double data) {

        try {
            outputStream.writeDouble(data);
            return this;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull StreamOut writeString(final @NotNull String data) {

        Preconditions.requireNotNull(data, "data");

        return writeVarInt(data.length())
                .writeBytes(data.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public @NotNull StreamOut writeComponent(final @NotNull Component data) {

        return writeString(COMPONENT_SERIALIZER.serialize(data));
    }

    @Override
    public @NotNull StreamOut writeIdentifier(final @NotNull Identifier data) {

        return writeString(data.toString());
    }

    @Override
    public @NotNull StreamOut writeVarInt(int data) {

        while (true) {
            if ((data & ~VarInts.SEGMENT_BITS) == 0) {
                writeByte((byte) data);
                break;
            }

            writeByte((byte) ((data & VarInts.SEGMENT_BITS) | VarInts.CONTINUE_BIT));

            data >>>= 7;
        }
        return this;
    }

    @Override
    public @NotNull StreamOut writeVarLong(long data) {

        while (true) {
            if ((data & ~((long) VarInts.SEGMENT_BITS)) == 0) {
                writeByte((byte) data);
                break;
            }

            writeByte((byte) ((data & VarInts.SEGMENT_BITS) | VarInts.CONTINUE_BIT));

            data >>>= 7;
        }
        return this;
    }

    @Override
    public @NotNull StreamOut writeCompoundTag(final @NotNull CompoundBinaryTag data) {

        // TODO(Sparky983): Empty compound tag should write an END tag

        try {
            BINARY_TAG_WRITER.write(data, (DataOutput) outputStream);
            return this;
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @NotNull StreamOut writeBlockPosition(final @NotNull BlockPosition data) {

        writeLong(
                ((long) (data.getX() & 0x3FFFFFF) << 38) | ((long) (data.getY() & 0x3FFFFFF) << 12)
                        | (data.getY() & 0xFFF));
        return this;
    }

    @Override
    public @NotNull StreamOut writeUuid(final @NotNull UUID data) {

        Preconditions.requireNotNull(data, "data");

        return writeLong(data.getMostSignificantBits())
                .writeLong(data.getLeastSignificantBits());
    }

    @Override
    public @NotNull StreamOut writeBytes(final byte @NotNull [] data) {

        Preconditions.requireNotNull(data, "data");

        try {
            outputStream.write(data);
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
        return this;
    }

    @Override
    public @NotNull StreamOut writeByteList(final byte @NotNull [] data) {

        Preconditions.requireNotNull(data, "data");

        return writeVarInt(data.length)
                .writeBytes(data);
    }

    @Override
    public @NotNull StreamOut writeVarInts(final int @NotNull [] data) {

        Preconditions.requireNotNull(data, "data");

        for (final int i : data) {
            writeVarInt(i);
        }
        return this;
    }

    @Override
    public @NotNull StreamOut writeVarIntList(final int @NotNull [] data) {

        Preconditions.requireNotNull(data, "data");

        return writeVarInt(data.length)
                .writeVarInts(data);
    }

    @Override
    public @NotNull StreamOut writeLongs(final long @NotNull [] data) {

        Preconditions.requireNotNull(data, "data");

        for (final long l : data) {
            writeLong(l);
        }
        return this;
    }

    @Override
    public @NotNull StreamOut writeLongList(final long @NotNull [] data) {

        Preconditions.requireNotNull(data, "data");

        return writeVarInt(data.length)
                .writeLongs(data);
    }

    @Override
    public @NotNull StreamOut writeVarLongs(final long @NotNull [] data) {

        Preconditions.requireNotNull(data, "data");

        for (final long l : data) {
            writeVarLong(l);
        }
        return this;
    }

    @Override
    public @NotNull StreamOut writeVarLongList(final long @NotNull [] data) {

        Preconditions.requireNotNull(data, "data");

        return writeVarInt(data.length)
                .writeVarLongs(data);
    }

    @Override
    public @NotNull StreamOut writePosition(final @NotNull Position data) {

        return writeDouble(data.getX())
                .writeDouble(data.getY())
                .writeDouble(data.getZ());
    }

    @Override
    public @NotNull StreamOut writeDirection(final @NotNull Direction data) {

        Preconditions.requireNotNull(data, "data");

        return writeByte(data.getPitch())
                .writeByte(data.getYaw());
    }

    @Override
    public @NotNull StreamOut writeVelocity(final @NotNull Velocity data) {

        Preconditions.requireNotNull(data, "data");

        return writeShort(data.getX())
                .writeShort(data.getY())
                .writeShort(data.getZ());
    }

    @Override
    public <T> @NotNull StreamOut writeNullable(final @Nullable T data,
            final @NotNull BiConsumer<@NotNull StreamOut, @NotNull T> writer) {

        Preconditions.requireNotNull(writer, "writer");

        return writeNullable(data, (notNullData) -> writer.accept(this, notNullData));
    }

    @Override
    public <T> @NotNull StreamOut writeNullable(final @Nullable T data,
            final @NotNull Consumer<@NotNull T> writer) {

        Preconditions.requireNotNull(writer, "writer");

        return writeNullable(data, () -> {
            assert data != null;
            writer.accept(data);
        });
    }

    @Override
    public <T> @NotNull StreamOut writeNullable(final @Nullable T data,
                                                            final @NotNull Runnable writer) {

        Preconditions.requireNotNull(writer, "writer");

        if (data == null) {
            writer.run();
        }

        return this;
    }

    @Override
    public @NotNull StreamOut writeVarIntEnum(final @NotNull Enum<?> data) {

        Preconditions.requireNotNull(data, "data");

        return writeVarInt(data.ordinal());
    }

    @Override
    public @NotNull StreamOut writeByteEnum(final @NotNull Enum<?> data) {

        Preconditions.requireNotNull(data, "data");
        Preconditions.requireTrue(data.ordinal() <= Byte.MAX_VALUE, "enum value is too big to be encoded to a single byte");

        return writeByte((byte) data.ordinal());
    }

    @Override
    public @NotNull StreamOut writeUnsignedByteEnum(final @NotNull Enum<?> data) {

        Preconditions.requireNotNull(data, "data");
        Preconditions.requireTrue(data.ordinal() <= 0xFF, "enum value is too big to be encoded to a single unsigned byte");

        return writeUnsignedByte((byte) data.ordinal());
    }

    @Override
    public <T> @NotNull StreamOut writeList(final @NotNull List<T> data,
            final @NotNull BiConsumer<@NotNull StreamOut, @NotNull T> writer) {

        Preconditions.requireNotNull(data, "data");

        return writeList(data, (element) -> writer.accept(this, element));
    }

    @Override
    public <T> @NotNull StreamOut writeList(final @NotNull List<T> data,
            final @NotNull Consumer<@NotNull T> writer) {

        Preconditions.requireNotNull(writer, "writer");

        writeVarInt(data.size());

        for (final T e : data) {
            writer.accept(e);
        }

        return this;
    }

    @Override
    public @NotNull StreamOut writeWritable(final @NotNull Writable writable) {

        Preconditions.requireNotNull(writable, "writable");

        writable.write(this);
        return this;
    }

    @Override
    public void close() {

        try {
            outputStream.close();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }
}
