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
 * MinecraftOutputStream out = MinecraftOutputStream.from(byteArrayOut);
 * out.writeVarInt(10);
 *
 * byte[] data = byteArrayOut.toByteArray();
 * </pre>
 * And with the {@link ByteArrayMinecraftOutputStream}:
 * <pre>
 * ByteArrayMinecraftOutputStream out = MinecraftOutputStream.newByteArrayOutputStream();
 * out.writeVarInt(10);
 * byte[] data = out.toByteArray();
 * </pre>
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface ByteArrayMinecraftOutputStream extends MinecraftOutputStream {

    @Override
    @NotNull ByteArrayOutputStream toOutputStream();

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeBoolean(boolean data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeByte(byte data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeUByte(@Range(from = 0, to = 0xFF) int data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeShort(short data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeUShort(@Range(from = 0, to = 0xFFFF) int data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeInt(int data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeLong(long data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeFloat(float data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeDouble(double data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeString(@NotNull String data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeChat(@NotNull Component data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeIdentifier(@NotNull Identifier data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeVarInt(int data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeVarLong(long data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeNbtCompound(@NotNull CompoundBinaryTag data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeBlockPosition(@NotNull BlockPosition data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeUuid(@NotNull UUID data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeBytes(byte @NotNull [] data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeLengthPrefixedBytes(byte @NotNull [] data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writePosition(@NotNull Position data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeDirection(@NotNull Direction data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeVelocity(@NotNull Velocity data);

    @Override
    <T> @NotNull ByteArrayMinecraftOutputStream writeNullable(@Nullable T data,
            @NotNull BiConsumer<@NotNull MinecraftOutputStream, @NotNull T> writer);

    @Override
    @NotNull <T> ByteArrayMinecraftOutputStream writeNullable(@Nullable T data,
            @NotNull Consumer<@NotNull T> writer);

    @Override
    @NotNull <T> ByteArrayMinecraftOutputStream writeNullable(@Nullable T data, @NotNull Runnable writer);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeVarIntEnum(@NotNull Enum<?> data);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeByteEnum(@NotNull Enum<?> data);

    @Override
    @NotNull <T> ByteArrayMinecraftOutputStream writeList(@NotNull List<T> data,
            @NotNull BiConsumer<@NotNull MinecraftOutputStream, @NotNull T> writer);

    @Override
    @NotNull <T> ByteArrayMinecraftOutputStream writeList(@NotNull List<T> data,
            @NotNull Consumer<@NotNull T> writer);

    @Override
    @NotNull ByteArrayMinecraftOutputStream writeWritable(@NotNull Writable writable);

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
