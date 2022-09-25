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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.OutputStream;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;

/**
 * Compresses and decompress a stream.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface Compression {

    /**
     * Returns a zlib compression.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    static @NotNull Compression zlib() {

        return ZlibCompression.INSTANCE;
    }

    /**
     * Returns a version of inputStream that apply decompression.
     *
     * @param inputStream The inputStream to decompress.
     * @return The decompressed input stream.
     * @throws NullPointerException if inputStream is {@code null}.
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull
    StreamIn decompressed(@NotNull StreamIn inputStream);

    /**
     * Returns a version of inputStream that apply decompression.
     *
     * @param inputStream The inputStream to decompress.
     * @return The decompressed input stream.
     * @throws NullPointerException if inputStream is {@code null}.
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull
    InputStream decompressed(@NotNull InputStream inputStream);

    /**
     * Returns a version of outputStream with compression applied.
     *
     * @param outputStream The outputStream to compress.
     * @return The compressed outputStream.
     * @throws NullPointerException if outputStream is {@code null}.
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull
    StreamOut compressed(@NotNull StreamOut outputStream);

    /**
     * Returns a version of outputStream with compression applied.
     *
     * @param outputStream The outputStream to compress.
     * @return The compressed outputStream.
     * @throws NullPointerException if outputStream is {@code null}.
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull
    OutputStream compressed(@NotNull OutputStream outputStream);
}
