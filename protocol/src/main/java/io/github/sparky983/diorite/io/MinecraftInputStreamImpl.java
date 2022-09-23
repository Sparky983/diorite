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
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.util.Protocol;
import io.github.sparky983.diorite.world.BlockPosition;
import io.github.sparky983.diorite.world.Direction;
import io.github.sparky983.diorite.world.Identifier;
import io.github.sparky983.diorite.world.Position;
import io.github.sparky983.diorite.world.Velocity;

final class MinecraftInputStreamImpl implements MinecraftInputStream {

    private final static int MAX_UTF_8_CHAR_LENGTH = 4;

    private static final GsonComponentSerializer COMPONENT_SERIALIZER =
            GsonComponentSerializer.gson();
    private static final BinaryTagIO.Reader BINARY_TAG_READER = BinaryTagIO.reader();

    private final DataInputStream inputStream;

    public MinecraftInputStreamImpl(final @NotNull DataInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.inputStream = inputStream;
    }

    @Override
    public @NotNull InputStream toInputStream() {

        return inputStream;
    }

    @Override
    public void skip(final int n) {

        try {
            final long skipped = inputStream.skip(n);
            if (skipped != n) {
                throw new IllegalStateException("Unable to skip " + n + "bytes");
            }
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public boolean readBoolean() {

        try {
            return inputStream.readBoolean();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public byte readByte() {

        try {
            return inputStream.readByte();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @Range(from = 0, to = 0xFF) int readUByte() {

        try {
            return inputStream.readUnsignedByte();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public short readShort() {

        try {
            return inputStream.readShort();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @Range(from = 0, to = 0xFFFF) int readUShort() {

        try {
            return inputStream.readUnsignedShort();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public int readInt() {

        try {
            return inputStream.readInt();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public long readLong() {

        try {
            return inputStream.readLong();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public float readFloat() {

        try {
            return inputStream.readFloat();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public double readDouble() {

        try {
            return inputStream.readDouble();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @NotNull String readString() {

        return readString(Protocol.MAX_STRING_LENGTH);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String readString(final @Range(from = 1, to = Protocol.MAX_STRING_LENGTH) int maxLength) {

        Preconditions.requireTrue(maxLength >= 1, "maxLength");

        final int length = readVarInt();

        if (length > maxLength * MAX_UTF_8_CHAR_LENGTH) {
            throw new DecodeException("Received string was longer than the maximum length (" + maxLength + ")");
        }

        if (length < 0) {
            throw new DecodeException("Received string length was less than 0");
        }

        final String input = new String(readLengthPrefixedBytes(), StandardCharsets.UTF_8);

        if (input.length() > maxLength) {
            throw new DecodeException("Received string was longer than the maximum length (" + maxLength + ")");
        }

        return input;
    }

    @Override
    public @NotNull Component readChat() {

        try {
            return COMPONENT_SERIALIZER.deserialize(readString());
        } catch (final Exception e) {
            throw new DecodeException(e);
        }
    }

    @Override
    public @NotNull Identifier readIdentifier() {

        return Identifier.parse(readString());
    }

    @Override
    public int readVarInt() {

        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = readByte();
            value |= (currentByte & VarInts.SEGMENT_BITS) << position;

            if ((currentByte & VarInts.CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) {
                throw new DecodeException("VarInt is too big");
            }
        }

        return value;
    }

    @Override
    public long readVarLong() {

        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = readByte();
            value |= (long) (currentByte & VarInts.SEGMENT_BITS) << position;

            if ((currentByte & VarInts.CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 64) throw new DecodeException("VarLong is too big");
        }

        return value;
    }

    @Override
    public @NotNull CompoundBinaryTag readNbtCompound() {

        try {
            return BINARY_TAG_READER.read((DataInput) inputStream);
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public @NotNull BlockPosition readBlockPosition() {

        final long position = readLong();
        final int x = (int) (position >> 38);
        final int y = (int) (position << 52 >> 52);
        final int z = (int) (position << 26 >> 38);

        return BlockPosition.of(x, y, z);
    }

    @Override
    public @NotNull UUID readUuid() {

        return new UUID(readLong(), readLong());
    }

    @Override
    public byte @NotNull [] readBytes(final int n) {

        try {
            return inputStream.readNBytes(n);
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public byte @NotNull [] readAllBytes() {

        try {
            return inputStream.readAllBytes();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    @Override
    public byte @NotNull [] readLengthPrefixedBytes() {

        final int length = readVarInt();

        return readBytes(length);
    }

    @Override
    public int @NotNull [] readVarInts(final @Range(from = 0, to = Integer.MAX_VALUE) int length) {

        Preconditions.requireRange(length, 0, Integer.MAX_VALUE, "length");

        final int[] varInts = new int[length];
        for (int i = 0; i < length; i++) {
            varInts[i] = readVarInt();
        }
        return varInts;
    }

    @Override
    public int @NotNull [] readLengthPrefixedVarInts() {

        final int length = readVarInt();
        return readVarInts(length);
    }

    @Override
    public long @NotNull [] readLongs(final @Range(from = 0, to = Integer.MAX_VALUE) int length) {

        Preconditions.requireRange(length, 0, Integer.MAX_VALUE, "length");

        final long[] longs = new long[length];
        for (int i = 0; i < length; i++) {
            longs[i] = readLong();
        }
        return longs;
    }

    @Override
    public long @NotNull [] readLengthPrefixedLongs() {

        final int length = readVarInt();
        return readLongs(length);
    }

    @Override
    public long @NotNull [] readVarLongs(final @Range(from = 0, to = Integer.MAX_VALUE) int length) {

        Preconditions.requireRange(length, 0, Integer.MAX_VALUE, "length");

        final long[] varLongs = new long[length];
        for (int i = 0; i < length; i++) {
            varLongs[i] = readVarLong();
        }
        return varLongs;
    }

    @Override
    public long @NotNull [] readLengthPrefixedVarLongs() {

        final int length = readVarInt();
        return readVarLongs(length);
    }

    @Override
    public @NotNull Position readPosition() {

        final double x = readDouble();
        final double y = readDouble();
        final double z = readDouble();

        return Position.of(x, y, z);
    }

    @Override
    public @NotNull Direction readDirection() {

        final byte pitch = readByte();
        final byte yaw = readByte();

        return Direction.of(pitch, yaw);
    }

    @Override
    public @NotNull Velocity readVelocity() {

        final short x = readShort();
        final short y = readShort();
        final short z = readShort();

        return Velocity.of(x, y, z);
    }

    @Override
    public @NotNull <T> Optional<T> readOptional(
            final @NotNull Function<@NotNull MinecraftInputStream, @NotNull T> reader) {

        Preconditions.requireNotNull(reader, "reader");

        return readOptional(() -> reader.apply(this));
    }

    @Override
    public <T> @NotNull Optional<T> readOptional(final @NotNull Supplier<@NotNull T> reader) {

       Preconditions.requireNotNull(reader, "reader");

       final boolean isPresent = readBoolean();

       if (!isPresent) {
           return Optional.empty();
       }

       return Optional.of(reader.get());
    }

    @SuppressWarnings("ConstantConditions") // Runtime check
    public <T extends Enum<T>> T getEnumById(final @NotNull Class<T> enumClass,
            final @Range(from = 0, to = Integer.MAX_VALUE) int ordinal) {

        // TODO: cache constants
        final T[] enumConstants = enumClass.getEnumConstants();

        assert enumConstants != null : "enumClass should be an enum";

        if (ordinal >= enumConstants.length) {
            throw new DecodeException("Id was too large");
        }

        if (ordinal < 0) {
            throw new DecodeException("Id was too small");
        }

        return enumConstants[ordinal];
    }

    @Override
    public @NotNull <T extends Enum<T>> T readVarIntEnum(final @NotNull Class<T> enumClass) {

        Preconditions.requireNotNull(enumClass, "enumClass");

        return getEnumById(enumClass, readVarInt());
    }

    @Override
    public <T extends Enum<T>> @NotNull T readByteEnum(final @NotNull Class<T> enumClass) {

        Preconditions.requireNotNull(enumClass);

        return getEnumById(enumClass, readByte());
    }

    @Override
    public <T extends Enum<T>> @NotNull T readUByteEnum(final @NotNull Class<T> enumClass) {

        Preconditions.requireNotNull(enumClass);

        return getEnumById(enumClass, readUByte());
    }

    @Override
    public @Unmodifiable @NotNull <T> List<@NotNull T> readList(
            final @NotNull Function<@NotNull MinecraftInputStream, @NotNull T> reader) {

        Preconditions.requireNotNull(reader, "reader");

        return readList(() -> reader.apply(this));
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Unmodifiable @NotNull <T> List<@NotNull T> readList(
            final @NotNull Supplier<@NotNull T> reader) {

        final int size = readVarInt();

        final T[] array = (T[]) new Object[size];

        for (int i = 0; i < size; i++) {
            final T e = reader.get();
            Preconditions.requireNotNull(e, "reader.get([" + i + "])");
            array[i] = e;
        }

        return List.of(array);
    }

    @Override
    public void close() {

        try {
            inputStream.close();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }
}