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

final class ByteArrayStreamOutImpl implements ByteArrayStreamOut {

    private final ByteArrayOutputStream byteArrayOutputStream;
    private final StreamOut delegate;

    ByteArrayStreamOutImpl(final @NotNull ByteArrayOutputStream byteArrayOutputStream) {

        this.byteArrayOutputStream = byteArrayOutputStream;
        this.delegate = StreamOut.from(byteArrayOutputStream);
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
    public @NotNull ByteArrayStreamOut writeBoolean(final boolean data) {

        delegate.writeBoolean(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeByte(final byte data) {

        delegate.writeByte(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeUnsignedByte(final @Range(from = 0, to = 0xFF) int data) {

        delegate.writeUnsignedByte(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeShort(final short data) {

        delegate.writeShort(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeUnsignedShort(final @Range(from = 0, to = 0xFFFF) int data) {

        delegate.writeUnsignedShort(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeInt(final int data) {

        delegate.writeInt(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeLong(final long data) {

        delegate.writeLong(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeFloat(final float data) {

        delegate.writeFloat(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeDouble(final double data) {

        delegate.writeDouble(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeString(final @NotNull String data) {

        delegate.writeString(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeComponent(final @NotNull Component data) {

        delegate.writeComponent(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeIdentifier(final @NotNull Identifier data) {

        delegate.writeIdentifier(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeVarInt(final int data) {

        delegate.writeVarInt(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeVarLong(final long data) {

        delegate.writeVarLong(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeCompoundTag(final @NotNull CompoundBinaryTag data) {

        delegate.writeCompoundTag(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeBlockPosition(final @NotNull BlockPosition data) {

        delegate.writeBlockPosition(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeUuid(final @NotNull UUID data) {

        delegate.writeUuid(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeBytes(final byte @NotNull [] data) {

        delegate.writeBytes(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeByteList(final byte @NotNull [] data) {

        delegate.writeByteList(data);
        return this;
    }

    @Override
    public @NotNull StreamOut writeVarInts(final int @NotNull [] data) {

        delegate.writeVarInts(data);
        return this;
    }

    @Override
    public @NotNull StreamOut writeVarIntList(final int @NotNull [] data) {

        delegate.writeVarIntList(data);
        return this;
    }

    @Override
    public @NotNull StreamOut writeLongs(final long @NotNull [] data) {

        delegate.writeLongs(data);
        return this;
    }

    @Override
    public @NotNull StreamOut writeLongList(final long @NotNull [] data) {

        delegate.writeLongList(data);
        return this;
    }

    @Override
    public @NotNull StreamOut writeVarLongs(final long @NotNull [] data) {

        delegate.writeVarLongs(data);
        return this;
    }

    @Override
    public @NotNull StreamOut writeVarLongList(final long @NotNull [] data) {

        delegate.writeVarLongList(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writePosition(final @NotNull Position data) {

        delegate.writePosition(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeDirection(final @NotNull Direction data) {

        delegate.writeDirection(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeVelocity(final @NotNull Velocity data) {

        delegate.writeVelocity(data);
        return this;
    }

    @Override
    public <T> @NotNull ByteArrayStreamOut writeNullable(final @Nullable T data,
            final @NotNull BiConsumer<@NotNull StreamOut, @NotNull T> writer) {

        delegate.writeNullable(data, writer);
        return this;
    }

    @Override
    public <T> @NotNull ByteArrayStreamOut writeNullable(final @Nullable T data,
            final @NotNull Consumer<@NotNull T> writer) {

        delegate.writeNullable(data, writer);
        return this;
    }

    @Override
    public <T> @NotNull ByteArrayStreamOut writeNullable(final @Nullable T data,
            final @NotNull Runnable writer) {

        delegate.writeNullable(data, writer);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeVarIntEnum(final @NotNull Enum<?> data) {

        delegate.writeVarIntEnum(data);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeByteEnum(final @NotNull Enum<?> data) {

        delegate.writeByteEnum(data);
        return this;
    }

    @Override
    public @NotNull StreamOut writeUnsignedByteEnum(final @NotNull Enum<?> data) {

        delegate.writeUnsignedByteEnum(data);
        return this;
    }

    @Override
    public <T> @NotNull ByteArrayStreamOut writeList(final @NotNull List<T> data,
            final @NotNull BiConsumer<@NotNull StreamOut, T> writer) {

        delegate.writeList(data, writer);
        return this;
    }

    @Override
    public <T> @NotNull ByteArrayStreamOut writeList(final @NotNull List<T> data,
            final @NotNull Consumer<T> writer) {

        delegate.writeList(data, writer);
        return this;
    }

    @Override
    public @NotNull ByteArrayStreamOut writeWritable(final @NotNull Writable writable) {

        delegate.writeWritable(writable);
        return this;
    }

    @Override
    public void close() {

        delegate.close();
    }
}
