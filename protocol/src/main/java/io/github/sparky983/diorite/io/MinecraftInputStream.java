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

import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.InputStream;
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

/**
 * A byte input stream that natively supports reading types specified in the Minecraft Java protocol
 * but by default are not supported by Java streams.
 * <p>
 * Thread safety is expected to be managed at a different level.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface MinecraftInputStream extends Closeable {

    /**
     * Creates a minecraft input stream from the specified input stream.
     *
     * @param inputStream The input stream.
     * @return The read input.
     * @throws NullPointerException if inputStream is {@code null}.
     * @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull MinecraftInputStream from(final @NotNull InputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        return from(new DataInputStream(inputStream));
    }

    /**
     * Creates a minecraft input stream from the specified data input stream.
     *
     * @param inputStream The data output stream.
     * @return The read input.
     * @throws NullPointerException if inputStream is {@code null}.
     * @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull MinecraftInputStream from(final @NotNull DataInputStream inputStream) {

        return new MinecraftInputStreamImpl(inputStream);
    }

    /**
     * Creates a new byte array minecraft output stream reading input from the specified byte array.
     *
     * @param input The input to read from.
     * @return The newly created input stream.
     * @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull ByteArrayMinecraftInputStream createByteArrayInputStream(
            final byte @NotNull [] input) {

        Preconditions.requireNotNull(input, "input");

        return new ByteArrayMinecraftInputStreamImpl(new ByteArrayInputStream(input));
    }

    @Contract(pure = true)
    @NotNull InputStream toInputStream();

    /**
     * Skips over the next n bytes.
     *
     * @param n The amount of bytes to skip over.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws IllegalStateException if n bytes were not able to be skipped.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    void skip(int n);

    // information about the data types used in the Minecraft Java protocol
    // https://wiki.vg/Protocol#Data_types

    /**
     * Reads the next boolean from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    boolean readBoolean();

    /**
     * Reads the next byte from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    byte readByte();

    /**
     * Reads the next unsigned byte to from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @Range(from = 0, to = 0xFF) int readUByte();

    /**
     * Reads the next short from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    short readShort();

    /**
     * Reads the next unsigned short to from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @Range(from = 0, to = 0xFFFF) int readUShort();

    /**
     * Reads the next int from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    int readInt();

    /**
     * Reads the next long from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    long readLong();

    /**
     * Reads the next float from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    float readFloat();

    /**
     * Reads the next double from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    double readDouble();

    /**
     * Reads the next string from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @NotNull String readString();

    /**
     * Reads the next string from the input stream
     *
     * @param maxLength The maximum length the string is allowed to be.
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value or if the string
     *                         exceeds the maximum length.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @NotNull String readString(@Range(from = 1, to = Protocol.MAX_STRING_LENGTH) int maxLength);

    /**
     * Reads the next chat component from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @NotNull Component readChat();

    /**
     * Reads the next identifier to from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @NotNull Identifier readIdentifier();

    /**
     * Reads the next var int from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    int readVarInt();

    /**
     * Reads the next var long from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    long readVarLong();

    /**
     * Reads the next nbt compound from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @NotNull CompoundBinaryTag readNbtCompound();

    /**
     * Reads the next position from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @NotNull BlockPosition readBlockPosition();

    /**
     * Reads the next uuid from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @NotNull UUID readUuid();

    /**
     * Reads the rest of the bytes from the input.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    byte @NotNull [] readAllBytes();

    /**
     * Reads the specified number of bytes from the input stream.
     *
     * @param length The number of bytes to read.
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    byte @NotNull [] readBytes(@Range(from = 0, to = Integer.MAX_VALUE) int length);

    /**
     * Reads bytes prefixed by their length from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    byte @NotNull [] readLengthPrefixedBytes();

    /**
     * Reads the specified number of var ints from the input stream.
     *
     * @param length The number of bytes to read.
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    int @NotNull [] readVarInts(@Range(from = 0, to = Integer.MAX_VALUE) int length);

    /**
     * Reads var ints prefixed by their length from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    int @NotNull [] readLengthPrefixedVarInts();

    /**
     * Reads the specified number of longs from the input stream.
     *
     * @param length The number of bytes to read.
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    long @NotNull [] readLongs(@Range(from = 0, to = Integer.MAX_VALUE) int length);

    /**
     * Reads longs prefixed by their length from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    long @NotNull [] readLengthPrefixedLongs();

    /**
     * Reads the specified number of var longs from the input stream.
     *
     * @param length The number of bytes to read.
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    long @NotNull [] readVarLongs(@Range(from = 0, to = Integer.MAX_VALUE) int length);

    /**
     * Reads bytes prefixed by their length from the input stream.
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    long @NotNull [] readLengthPrefixedVarLongs();

    /**
     * Reads the next position from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @NotNull Position readPosition();

    /**
     * Reads the next direction from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @NotNull Direction readDirection();

    /**
     * Reads the next velocity from the input stream
     *
     * @return The read input.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    @NotNull Velocity readVelocity();

    /**
     * Reads an optional from the input stream. Reads from the specified reader if the optional is
     * present.
     *
     * @param reader The reader to read the data from.
     * @param <T> The data type.
     * @return The read data.
     * @throws NullPointerException if reader is {@code null} or if the reader returns {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @see #readOptional(Supplier)
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    <T> @NotNull Optional<T> readOptional(@NotNull Function<@NotNull MinecraftInputStream, @NotNull T> reader);

    /**
     * Reads an optional from the input stream. Reads from the specified reader if the optional is
     * present.
     * <p>
     * This is an alternative to {@link #readOptional(Function)}, and should be used if the caller
     * already has a reference to the input stream.
     * Example:
     * With {@link #readOptional(Function)}
     * <pre>
     * MinecraftInputStream input;
     *
     * input.readOptional((in) -> in.readString());
     * </pre>
     * , and with this method:
     * <pre>
     * MinecraftInputStream input;
     *
     * input.readOptional(() -> input.readString());
     * </pre>
     *
     * @param reader The reader to read the data from.
     * @param <T> The data type.
     * @return The read data.
     * @throws NullPointerException if reader is {@code null} or if the reader returns {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @see #readOptional(Function)
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    <T> @NotNull Optional<T> readOptional(@NotNull Supplier<@NotNull T> reader);

    /**
     * Reads an enum encoded as a var int from the input stream.
     *
     * @return The read input.
     * @throws NullPointerException if enumClass is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    <T extends Enum<T>> @NotNull T readVarIntEnum(@NotNull Class<T> enumClass);

    /**
     * Reads an enum encoded as a byte from the input stream.
     *
     * @return The read input.
     * @throws NullPointerException if enumClass is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    <T extends Enum<T>> @NotNull T readByteEnum(@NotNull Class<T> enumClass);

    /**
     * Reads an enum encoded as an unsigned byte from the input stream.
     *
     * @return The read input.
     * @throws NullPointerException if enumClass is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @throws DecodeException if an exception occurred while decoding the value.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    <T extends Enum<T>> @NotNull T readUByteEnum(@NotNull Class<T> enumClass);

    /**
     * Reads a list from the input stream, reading each element with the specified reader.
     *
     * @param reader The reader to read elements from.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data or writer are {@code null} or if the reader returns
     *                              {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    <T> @Unmodifiable @NotNull List<@NotNull T> readList(@NotNull Function<@NotNull MinecraftInputStream, @NotNull T> reader);

    /**
     * Reads a list from the input stream, reading each element with the specified reader.
     * <p>
     * This is an alternative to {@link #readList(Function)} (Function)}, and should be used
     * if the caller already has a reference to the input stream.
     * Example:
     * With {@link #readList(Function)}
     * <pre>
     * MinecraftInputStream input;
     *
     * input.readCollection((in) -> in.readString());
     * </pre>
     * , and with this method:
     * <pre>
     * MinecraftInputStream input;
     *
     * input.readCollection(() -> input.readString());
     * </pre>
     *
     * @param reader The reader to read elements from.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data or writer are {@code null} or if the reader returns
     *                              {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(mutates = "this")
    <T> @Unmodifiable @NotNull List<@NotNull T> readList(@NotNull Supplier<@NotNull T> reader);

    /**
     * @throws RuntimeIOException if an io exception occurred.
     */
    @Override
    void close();
}
