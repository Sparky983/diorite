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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.github.sparky983.diorite.world.BlockPosition;
import io.github.sparky983.diorite.world.Direction;
import io.github.sparky983.diorite.world.Identifier;
import io.github.sparky983.diorite.world.Position;
import io.github.sparky983.diorite.world.Velocity;

/**
 * A byte output stream that natively supports writing types specified in the Minecraft Java.
 * <p>
 * Helps to reduce the mess of converting an output stream to a byte array.
 * <p>
 * Here is an example of the old method:
 * <pre>
 * ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
 * StreamOut out = StreamOut.from(byteArrayOut);
 * out.writeVarInt(10);
 *
 * byte[] data = byteArrayOut.toByteArray();
 * </pre>
 * And with the {@link ByteArrayStreamOut}:
 * <pre>
 * ByteArrayStreamOut out = StreamOut.newByteArrayOutputStream();
 * out.writeVarInt(10);
 * byte[] data = out.toByteArray();
 * </pre>
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface ByteArrayStreamOut extends StreamOut {

    @Override
    @NotNull ByteArrayOutputStream toOutputStream();

    @Override
    @NotNull ByteArrayStreamOut writeBoolean(boolean data);

    @Override
    @NotNull ByteArrayStreamOut writeByte(byte data);

    @Override
    @NotNull ByteArrayStreamOut writeUnsignedByte(@Range(from = 0, to = 0xFF) int data);

    @Override
    @NotNull ByteArrayStreamOut writeShort(short data);

    @Override
    @NotNull ByteArrayStreamOut writeUnsignedShort(@Range(from = 0, to = 0xFFFF) int data);

    @Override
    @NotNull ByteArrayStreamOut writeInt(int data);

    @Override
    @NotNull ByteArrayStreamOut writeLong(long data);

    @Override
    @NotNull ByteArrayStreamOut writeFloat(float data);

    @Override
    @NotNull ByteArrayStreamOut writeDouble(double data);

    @Override
    @NotNull ByteArrayStreamOut writeString(@NotNull String data);

    @Override
    @NotNull ByteArrayStreamOut writeComponent(@NotNull Component data);

    @Override
    @NotNull ByteArrayStreamOut writeIdentifier(@NotNull Identifier data);

    @Override
    @NotNull ByteArrayStreamOut writeVarInt(int data);

    @Override
    @NotNull ByteArrayStreamOut writeVarLong(long data);

    @Override
    @NotNull ByteArrayStreamOut writeCompoundTag(@NotNull CompoundBinaryTag data);

    @Override
    @NotNull ByteArrayStreamOut writeBlockPosition(@NotNull BlockPosition data);

    @Override
    @NotNull ByteArrayStreamOut writeUuid(@NotNull UUID data);

    @Override
    @NotNull ByteArrayStreamOut writeBytes(byte @NotNull [] data);

    @Override
    @NotNull ByteArrayStreamOut writeByteList(byte @NotNull [] data);

    @Override
    @NotNull ByteArrayStreamOut writePosition(@NotNull Position data);

    @Override
    @NotNull ByteArrayStreamOut writeDirection(@NotNull Direction data);

    @Override
    @NotNull ByteArrayStreamOut writeVelocity(@NotNull Velocity data);

    @Override
    <T> @NotNull ByteArrayStreamOut writeNullable(@Nullable T data,
            @NotNull BiConsumer<@NotNull StreamOut, @NotNull T> writer);

    @Override
    <T> @NotNull ByteArrayStreamOut writeNullable(@Nullable T data,
            @NotNull Consumer<@NotNull T> writer);

    @Override
    <T> @NotNull ByteArrayStreamOut writeNullable(@Nullable T data, @NotNull Runnable writer);

    @Override
    @NotNull ByteArrayStreamOut writeVarIntEnum(@NotNull Enum<?> data);

    @Override
    @NotNull ByteArrayStreamOut writeByteEnum(@NotNull Enum<?> data);

    @Override
    <T> @NotNull ByteArrayStreamOut writeList(@NotNull List<T> data,
            @NotNull BiConsumer<@NotNull StreamOut, T> writer);

    @Override
    <T> @NotNull ByteArrayStreamOut writeList(@NotNull List<T> data,
            @NotNull Consumer<T> writer);

    @Override
    @NotNull ByteArrayStreamOut writeWritable(@NotNull Writable writable);

    /**
     * Converts the output stream to a byte array.
     * <p>
     * Yields the same result as calling {@code toOutputStream().toByteArray()}.
     *
     * @return The byte array.
     * @since 1.0.0
     */
    byte @NotNull [] toByteArray();
}
