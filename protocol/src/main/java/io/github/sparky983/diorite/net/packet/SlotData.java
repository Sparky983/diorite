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

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.util.Preconditions;

/**
 * An item stack and it's data. A not present item is not representable, use {@code null}.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public final class SlotData implements Writable {

    private final int itemId;
    private final byte itemCount;
    private final CompoundBinaryTag nbt;

    @Contract(pure = true)
    public SlotData(final int itemId, final byte itemCount, final @NotNull CompoundBinaryTag nbt) {

        Preconditions.requireNotNull(nbt, "nbt");

        this.itemId = itemId;
        this.itemCount = itemCount;

        this.nbt = nbt;
    }

    @Contract(mutates = "param")
    public static @NotNull Optional<SlotData> read(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        return inputStream.readOptional(() -> new SlotData(
                inputStream.readVarInt(),
                inputStream.readByte(),
                inputStream.readNbtCompound()
        ));
    }

    @Contract(mutates = "param")
    public static @Nullable SlotData readNullable(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        return inputStream.readOptional(() -> new SlotData(
                inputStream.readVarInt(),
                inputStream.readByte(),
                inputStream.readNbtCompound()
        )).orElse(null);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(itemId)
                .writeByte(itemCount)
                .writeNbtCompound(nbt);
    }
}
