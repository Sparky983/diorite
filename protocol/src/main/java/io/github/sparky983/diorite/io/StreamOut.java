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
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.OutputStream;
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

/**
 * A byte output stream that natively supports writing types specified in the Minecraft Java
 * protocol but by default are not supported by Java streams.
 * <p>
 * Thread safety is expected to be managed at a different level.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface StreamOut extends Closeable {

    /**
     * Creates a minecraft output stream from the specified output stream.
     *
     * @param outputStream The output stream.
     * @return The created output stream.
     * @throws NullPointerException if outputStream is {@code null}.
     * @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull StreamOut from(final @NotNull OutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        return from(new DataOutputStream(outputStream));
    }

    /**
     * Creates a minecraft output stream from the specified data output stream.
     *
     * @param outputStream The data output stream.
     * @return The created output stream.
     * @throws NullPointerException if outputStream is {@code null}.
     * @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull StreamOut from(final @NotNull DataOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        return new StreamOutImpl(outputStream);
    }

    /**
     * Creates a new byte array minecraft output stream.
     *
     * @return The newly created byte array minecraft output stream.
     * @since 1.0.0
     */
    @Contract(value = "-> new", pure = true)
    static @NotNull ByteArrayStreamOut createByteArrayStream() {

        return new ByteArrayStreamOutImpl(new ByteArrayOutputStream());
    }

    /**
     * Returns the output stream this output stream is writing to.
     * <p>
     * An exception may be thrown if returning the output stream is unsafe or cannot be returned.
     *
     * @return The output stream.
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull OutputStream toOutputStream();

    // information about the data types used in the Minecraft Java protocol
    // https://wiki.vg/Protocol#Data_types

    /**
     * Writes the specified boolean to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeBoolean(boolean data);

    /**
     * Writes the specified byte to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeByte(byte data);

    /**
     * Writes the specified unsigned byte to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws IllegalArgumentException if the data cannot be converted to an unsigned
     *         byte.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeUnsignedByte(@Range(from = 0, to = 0xFF) int data);

    /**
     * Writes the specified short to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeShort(short data);

    /**
     * Writes the specified unsigned short to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws IllegalArgumentException if the data cannot be converted to an unsigned
     *         short.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeUnsignedShort(@Range(from = 0, to = 0xFFFF) int data);

    /**
     * Writes the specified int to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeInt(int data);

    /**
     * Writes the specified long to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeLong(long data);

    /**
     * Writes the specified float to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeFloat(float data);

    /**
     * Writes the specified double to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeDouble(double data);

    /**
     * Writes the specified string to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeString(@NotNull String data);

    /**
     * Writes the specified chat component to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeComponent(@NotNull Component data);

    /**
     * Writes the specified identifier to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeIdentifier(@NotNull Identifier data);

    /**
     * Writes the specified var int to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeVarInt(int data);

    /**
     * Writes the specified var long to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeVarLong(long data);

    /**
     * Writes the specified nbt tag to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeCompoundTag(@NotNull CompoundBinaryTag data);

    /**
     * Writes the specified position to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeBlockPosition(@NotNull BlockPosition data);

    /**
     * Writes the specified uuid to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeUuid(@NotNull UUID data);

    /**
     * Writes the specified byte array to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeBytes(byte @NotNull [] data);

    /**
     * Writes the specified byte array, prefixed by its length to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeByteList(byte @NotNull [] data);


    /**
     * Writes the specified int array to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeVarInts(int @NotNull [] data);

    /**
     * Writes the specified var int array, prefixed by its length to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeVarIntList(int @NotNull [] data);

    /**
     * Writes the specified long array to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeLongs(long @NotNull [] data);

    /**
     * Writes the specified long array, prefixed by its length to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeLongList(long @NotNull [] data);

    /**
     * Writes the specified var long array to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeVarLongs(long @NotNull [] data);

    /**
     * Writes the specified var long array, prefixed by its length to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeVarLongList(long @NotNull [] data);

    /**
     * Writes the specified position to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writePosition(@NotNull Position data);

    /**
     * Writes the specified direction to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeDirection(@NotNull Direction data);

    /**
     * Writes the specified velocity to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeVelocity(@NotNull Velocity data);

    /**
     * Writes the specified nullable type to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data or write are {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_, _ -> this", mutates = "this")
    <T> @NotNull StreamOut writeNullable(@Nullable T data, @NotNull BiConsumer<@NotNull StreamOut, @NotNull T> writer);

    /**
     * Writes the specified nullable type to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data or write are {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_, _ -> this", mutates = "this")
    <T> @NotNull StreamOut writeNullable(@Nullable T data, @NotNull Consumer<@NotNull T> writer);

    /**
     * Writes the specified nullable type to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data or write are {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_, _ -> this", mutates = "this")
    <T> @NotNull StreamOut writeNullable(@Nullable T data, @NotNull Runnable writer);

    /**
     * Writes the specified enum encoded as a var int to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeVarIntEnum(@NotNull Enum<?> data);

    /**
     * Writes the specified enum encoded as a byte to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeByteEnum(@NotNull Enum<?> data);

    /**
     * Writes the specified enum encoded as an unsigned byte to the output stream.
     *
     * @param data The data to write.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeUnsignedByteEnum(@NotNull Enum<?> data);

    /**
     * Writes the specified list to the output stream, writing each element with the specified.
     *
     * @param data The data to write.
     * @param writer The writer to use.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data or writer are {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_, _ -> this", mutates = "this")
    <T> @NotNull StreamOut writeList(@NotNull List<T> data, @NotNull BiConsumer<@NotNull StreamOut, T> writer);

    /**
     * Writes the specified list to the output stream, writing each element with the specified.
     *
     * @param data The data to write.
     * @param writer The writer to use.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if data or writer are {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_, _ -> this", mutates = "this")
    <T> @NotNull StreamOut writeList(@NotNull List<T> data, @NotNull Consumer<T> writer);

    /**
     * Writes the specified writable to the output stream.
     * <p>
     * The same as calling {@code Writable.write(StreamOut)}.
     *
     * @param writable The writable.
     * @return The output stream instance (for chaining).
     * @throws NullPointerException if writer is {@code null}.
     * @throws RuntimeIOException if an io exception occurred.
     * @since 1.0.0
     */
    @Contract(value = "_ -> this", mutates = "this")
    @NotNull StreamOut writeWritable(@NotNull Writable writable);

    /**
     * @throws RuntimeIOException if an io exception occurred.
     */
    @Override
    void close();
}
