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

package io.github.sparky983.diorite.net.packet;

import net.kyori.adventure.nbt.CompoundBinaryTag;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.util.Preconditions;

/**
 * An item stack and it's data. A not present item is not representable, use {@code null}.
 *
 * @author Sparky983
 * @since 1.0.0
 */
// TODO(Sparky983): move to world module
public final class ItemStack implements Writable {

    private final int itemId;
    private final byte itemCount;
    private final CompoundBinaryTag nbt;

    @Contract(pure = true)
    public ItemStack(final int itemId, final byte itemCount, final @NotNull CompoundBinaryTag nbt) {

        Preconditions.requireNotNull(nbt, "nbt");

        this.itemId = itemId;
        this.itemCount = itemCount;

        this.nbt = nbt;
    }

    @Contract(mutates = "param")
    public static @NotNull Optional<ItemStack> read(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        return inputStream.readOptional(() -> new ItemStack(
                inputStream.readVarInt(),
                inputStream.readByte(),
                inputStream.readCompoundTag()
        ));
    }

    @Contract(mutates = "param")
    public static @Nullable ItemStack readNullable(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        return inputStream.readOptional(() -> {
            final int id = inputStream.readVarInt();
            final byte count = inputStream.readByte();
            final CompoundBinaryTag nbt = inputStream.readCompoundTag();

            return new ItemStack(id, count, nbt);
        }).orElse(null);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(itemId)
                .writeByte(itemCount)
                .writeCompoundTag(nbt);
    }
}
