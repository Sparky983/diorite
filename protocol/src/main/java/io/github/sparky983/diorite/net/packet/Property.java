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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.util.Preconditions;

public final class Property implements Writable {

    private final String name;
    private final String value;
    private final @Nullable String signature;

    @Contract(pure = true)
    public Property(final @NotNull String name,
                    final @NotNull String value,
                    final @Nullable String signature) {

        Preconditions.requireNotNull(name, "name");
        Preconditions.requireNotNull(value, "value");

        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    @Contract(mutates = "param")
    public Property(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.name = inputStream.readString();
        this.value = inputStream.readString();
        this.signature = inputStream.readOptional(MinecraftInputStream::readString).orElse(null);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeString(name)
                .writeString(value)
                .writeNullable(signature, MinecraftOutputStream::writeString);
    }

    @Contract(pure = true)
    public @NotNull String getName() {

        return name;
    }

    @Contract(pure = true)
    public @NotNull String getValue() {

        return value;
    }

    @Contract(pure = true)
    public @Nullable String getSignature() {

        return signature;
    }
}
