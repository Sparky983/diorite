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

package io.github.sparky983.diorite.net.packet.format;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.io.compression.Compression;
import io.github.sparky983.diorite.net.Stateful;
import io.github.sparky983.diorite.net.packet.Packet;

/**
 * Represents a packet format. The Minecraft protocol specifies 2 formats: uncompressed and
 * compressed.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface PacketFormat {

    /**
     * Creates a new uncompressed packet format.
     *
     * @param stateful The object representing the channel's current state.
     * @return The created packet format.
     * @throws NullPointerException if stateful is {@code null}.
     * @since 1.0.0
     */
    @Contract(pure = true)
    static @NotNull PacketFormat uncompressed(final @NotNull Stateful stateful) {

        return new UncompressedPacketFormat(stateful);
    }

    /**
     * Creates a new compressed packet format.
     *
     * @param threshold The compression threshold.
     * @param compression The compression to use.
     * @return The created packet format.
     * @since 1.0.0
     */
    @Contract(pure = true)
    static @NotNull PacketFormat compressed(
            final @NotNull Stateful stateful,
            final @Range(from = 1, to = Integer.MAX_VALUE) int threshold,
            final @NotNull Compression compression) {

        return new CompressedPacketFormat(stateful, threshold, compression);
    }

    /**
     * Formats the specified packet.
     * <p>
     * Note that no checks will be made to ensure that the specified packet is able to sent at the
     * current time.
     *
     * @param packet The packet to format.
     * @param outputStream The output stream to format it to.
     * @throws NullPointerException if packet or outputStream are {@code null}.
     * @since 1.0.0
     */
    void encode(@NotNull Packet packet,
                @NotNull StreamOut outputStream);

    /**
     * Decodes the formatted packet from the input stream using the specified decoder to decoder the
     * packet data.
     *
     * @param inputStream The input stream.
     * @return The decoded packet.
     * @throws NullPointerException if decoder or inputStream are {@code null}.
     */
    @NotNull Packet decode(@NotNull StreamIn inputStream);
}
