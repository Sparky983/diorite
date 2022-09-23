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

package io.github.sparky983.diorite.io.compression;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterInputStream;
import java.util.zip.DeflaterOutputStream;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.util.Preconditions;

final class ZlibCompression implements Compression {

    static Compression INSTANCE = new ZlibCompression();

    @Override
    public @NotNull MinecraftInputStream decompressed(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        return MinecraftInputStream.from(decompressed(inputStream.toInputStream()));
    }

    @Override
    public @NotNull InputStream decompressed(final @NotNull InputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        return new DeflaterInputStream(inputStream);
    }

    @Override
    public @NotNull MinecraftOutputStream compressed(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        return MinecraftOutputStream.from(compressed(outputStream.toOutputStream()));
    }

    @Override
    public @NotNull OutputStream compressed(final @NotNull OutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        return new DeflaterOutputStream(outputStream);
    }
}
