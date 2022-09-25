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

final class ByteArrayStreamInImpl implements ByteArrayStreamIn {

    private final ByteArrayInputStream byteArrayInputStream;
    private final StreamIn delegate;

    @Contract(pure = true)
    ByteArrayStreamInImpl(final @NotNull ByteArrayInputStream byteArrayInputStream) {

        Preconditions.requireNotNull(byteArrayInputStream, "byteArrayInputStream");

        this.byteArrayInputStream = byteArrayInputStream;
        this.delegate = StreamIn.from(byteArrayInputStream);
    }

    @Override
    public @NotNull ByteArrayInputStream toInputStream() {

        return byteArrayInputStream;
    }

    @Override
    public void skip(final int n) {

        delegate.skip(n);
    }

    @Override
    public boolean readBoolean() {

        return delegate.readBoolean();
    }

    @Override
    public byte readByte() {

        return delegate.readByte();
    }

    @Override
    public @Range(from = 0, to = 0xFF) int readUnsignedByte() {

        return delegate.readUnsignedByte();
    }

    @Override
    public short readShort() {

        return delegate.readShort();
    }

    @Override
    public @Range(from = 0, to = 0xFFFF) int readUnsignedShort() {

        return delegate.readUnsignedShort();
    }

    @Override
    public int readInt() {

        return delegate.readInt();
    }

    @Override
    public long readLong() {

        return delegate.readLong();
    }

    @Override
    public float readFloat() {

        return delegate.readFloat();
    }

    @Override
    public double readDouble() {

        return delegate.readDouble();
    }

    @Override
    public @NotNull String readString() {

        return delegate.readString();
    }

    @Override
    public @NotNull String readString(final @Range(from = 1, to = Protocol.MAX_STRING_LENGTH) int maxLength) {

        return delegate.readString(maxLength);
    }

    @Override
    public @NotNull Component readComponent() {

        return delegate.readComponent();
    }

    @Override
    public @NotNull Identifier readIdentifier() {

        return delegate.readIdentifier();
    }

    @Override
    public int readVarInt() {

        return delegate.readVarInt();
    }

    @Override
    public long readVarLong() {

        return delegate.readVarLong();
    }

    @Override
    public @NotNull CompoundBinaryTag readCompoundTag() {

        return delegate.readCompoundTag();
    }

    @Override
    public @NotNull BlockPosition readBlockPosition() {

        return delegate.readBlockPosition();
    }

    @Override
    public @NotNull UUID readUuid() {

        return delegate.readUuid();
    }

    @Override
    public byte @NotNull [] readBytes(final @Range(from = 0, to = Integer.MAX_VALUE) int length) {

        return delegate.readBytes(length);
    }

    @Override
    public byte @NotNull [] readAllBytes() {

        return delegate.readAllBytes();
    }

    @Override
    public byte @NotNull [] readByteList() {

        return delegate.readByteList();
    }

    @Override
    public int @NotNull [] readVarInts(final @Range(from = 0, to = Integer.MAX_VALUE) int length) {

        return delegate.readVarInts(length);
    }

    @Override
    public int @NotNull [] readVarIntList() {

        return delegate.readVarIntList();
    }

    @Override
    public long @NotNull [] readLongs(final @Range(from = 0, to = Integer.MAX_VALUE) int length) {

        return delegate.readLongs(length);
    }

    @Override
    public long @NotNull [] readLongList() {

        return delegate.readLongList();
    }

    @Override
    public long @NotNull [] readVarLongs(final @Range(from = 0, to = Integer.MAX_VALUE) int length) {

        return delegate.readVarLongs(length);
    }

    @Override
    public long @NotNull [] readVarLongList() {

        return delegate.readVarLongList();
    }

    @Override
    public @NotNull Position readPosition() {

        return delegate.readPosition();
    }

    @Override
    public @NotNull Direction readDirection() {

        return delegate.readDirection();
    }

    @Override
    public @NotNull Velocity readVelocity() {

        return delegate.readVelocity();
    }

    @Override
    public <T> @NotNull Optional<T> readOptional(
            final @NotNull Function<@NotNull StreamIn, @NotNull T> reader) {

        return delegate.readOptional(reader);
    }

    @Override
    public <T> @NotNull Optional<T> readOptional(final @NotNull Supplier<@NotNull T> reader) {

        return delegate.readOptional(reader);
    }

    @Override
    public <T extends Enum<T>> @NotNull T readVarIntEnum(final @NotNull Class<T> enumClass) {

        return delegate.readVarIntEnum(enumClass);
    }

    @Override
    public <T extends Enum<T>> @NotNull T readByteEnum(final @NotNull Class<T> enumClass) {

        return delegate.readByteEnum(enumClass);
    }

    @Override
    public <T extends Enum<T>> @NotNull T readUnsignedByteEnum(final @NotNull Class<T> enumClass) {

        return delegate.readUnsignedByteEnum(enumClass);
    }

    @Override
    public <T> @Unmodifiable @NotNull List<@NotNull T> readList(
            final @NotNull Function<@NotNull StreamIn, @NotNull T> reader) {

        return delegate.readList(reader);
    }

    @Override
    public <T> @Unmodifiable @NotNull List<@NotNull T> readList(
            final @NotNull Supplier<@NotNull T> reader) {

        return delegate.readList(reader);
    }

    @Override
    public void close() {

        delegate.close();
    }
}
