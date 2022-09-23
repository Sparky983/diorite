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

final class ByteArrayMinecraftOutputStreamImpl implements ByteArrayMinecraftOutputStream {

    private final ByteArrayOutputStream byteArrayOutputStream;
    private final MinecraftOutputStream delegate;

    ByteArrayMinecraftOutputStreamImpl(final @NotNull ByteArrayOutputStream byteArrayOutputStream) {

        this.byteArrayOutputStream = byteArrayOutputStream;
        this.delegate = MinecraftOutputStream.from(byteArrayOutputStream);
    }

    @Override
    public @NotNull ByteArrayOutputStream toOutputStream() {

        return byteArrayOutputStream;
    }

    @Override
    public byte @NotNull [] toByteArray() {

        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeBoolean(final boolean data) {

        delegate.writeBoolean(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeByte(final byte data) {

        delegate.writeByte(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeUByte(final @Range(from = 0, to = 0xFF) int data) {

        delegate.writeUByte(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeShort(final short data) {

        delegate.writeShort(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeUShort(final @Range(from = 0, to = 0xFFFF) int data) {

        delegate.writeUShort(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeInt(final int data) {

        delegate.writeInt(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeLong(final long data) {

        delegate.writeLong(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeFloat(final float data) {

        delegate.writeFloat(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeDouble(final double data) {

        delegate.writeDouble(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeString(final @NotNull String data) {

        delegate.writeString(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeChat(final @NotNull Component data) {

        delegate.writeChat(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeIdentifier(final @NotNull Identifier data) {

        delegate.writeIdentifier(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeVarInt(final int data) {

        delegate.writeVarInt(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeVarLong(final long data) {

        delegate.writeVarLong(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeNbtCompound(final @NotNull CompoundBinaryTag data) {

        delegate.writeNbtCompound(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeBlockPosition(final @NotNull BlockPosition data) {

        delegate.writeBlockPosition(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeUuid(final @NotNull UUID data) {

        delegate.writeUuid(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeBytes(final byte @NotNull [] data) {

        delegate.writeBytes(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeLengthPrefixedBytes(final byte @NotNull [] data) {

        delegate.writeLengthPrefixedBytes(data);
        return this;
    }

    @Override
    public @NotNull MinecraftOutputStream writeVarInts(final int @NotNull [] data) {

        delegate.writeVarInts(data);
        return this;
    }

    @Override
    public @NotNull MinecraftOutputStream writeLengthPrefixedVarInts(final int @NotNull [] data) {

        delegate.writeLengthPrefixedVarInts(data);
        return this;
    }

    @Override
    public @NotNull MinecraftOutputStream writeLongs(final long @NotNull [] data) {

        delegate.writeLongs(data);
        return this;
    }

    @Override
    public @NotNull MinecraftOutputStream writeLengthPrefixedLongs(final long @NotNull [] data) {

        delegate.writeLengthPrefixedLongs(data);
        return this;
    }

    @Override
    public @NotNull MinecraftOutputStream writeVarLongs(final long @NotNull [] data) {

        delegate.writeVarLongs(data);
        return this;
    }

    @Override
    public @NotNull MinecraftOutputStream writeLengthPrefixedVarLongs(final long @NotNull [] data) {

        delegate.writeLengthPrefixedVarLongs(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writePosition(final @NotNull Position data) {

        delegate.writePosition(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeDirection(final @NotNull Direction data) {

        delegate.writeDirection(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeVelocity(final @NotNull Velocity data) {

        delegate.writeVelocity(data);
        return this;
    }

    @Override
    public <T> @NotNull ByteArrayMinecraftOutputStream writeNullable(final @Nullable T data,
            final @NotNull BiConsumer<@NotNull MinecraftOutputStream, @NotNull T> writer) {

        delegate.writeNullable(data, writer);
        return this;
    }

    @Override
    public @NotNull <T> ByteArrayMinecraftOutputStream writeNullable(final @Nullable T data,
            final @NotNull Consumer<@NotNull T> writer) {

        delegate.writeNullable(data, writer);
        return this;
    }

    @Override
    public @NotNull <T> ByteArrayMinecraftOutputStream writeNullable(final @Nullable T data,
            final @NotNull Runnable writer) {

        delegate.writeNullable(data, writer);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeVarIntEnum(final @NotNull Enum<?> data) {

        delegate.writeVarIntEnum(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeByteEnum(final @NotNull Enum<?> data) {

        delegate.writeByteEnum(data);
        return this;
    }

    @Override
    public @NotNull MinecraftOutputStream writeUByteEnum(final @NotNull Enum<?> data) {

        delegate.writeUByteEnum(data);
        return this;
    }

    @Override
    public @NotNull <T> ByteArrayMinecraftOutputStream writeList(final @NotNull List<T> data,
            final @NotNull BiConsumer<@NotNull MinecraftOutputStream, @NotNull T> writer) {

        delegate.writeList(data, writer);
        return this;
    }

    @Override
    public @NotNull <T> ByteArrayMinecraftOutputStream writeList(final @NotNull List<T> data,
            final @NotNull Consumer<@NotNull T> writer) {

        delegate.writeList(data, writer);
        return this;
    }

    @Override
    public @NotNull ByteArrayMinecraftOutputStream writeWritable(final @NotNull Writable writable) {

        delegate.writeWritable(writable);
        return this;
    }

    @Override
    public void close() {

        delegate.close();
    }
}
