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

import io.github.sparky983.diorite.io.ByteArrayMinecraftInputStream;
import io.github.sparky983.diorite.io.ByteArrayMinecraftOutputStream;
import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.compression.Compression;
import io.github.sparky983.diorite.net.Stateful;
import io.github.sparky983.diorite.net.packet.Packet;
import io.github.sparky983.diorite.util.Preconditions;

final class CompressedPacketFormat implements PacketFormat {

    private final Stateful stateful;
    private final int threshold;
    private final Compression compression;

    @Contract(pure = true)
    CompressedPacketFormat(final @NotNull Stateful stateful,
                           final @Range(from = -1, to = Integer.MAX_VALUE) int threshold,
                           final @NotNull Compression compression) {

        Preconditions.requireNotNull(stateful, "stateful");
        Preconditions.requireRange(threshold, -1, Integer.MAX_VALUE, "stateful");
        Preconditions.requireNotNull(compression, "compression");

        this.stateful = stateful;
        this.threshold = threshold;
        this.compression = compression;
    }

    @Override
    public void encode(final @NotNull Packet packet, final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(packet, "packet");
        Preconditions.requireNotNull(outputStream, "outputStream");

        // contains an uncompressed version of packet id and data fields
        final byte[] uncompressed;

        try (final ByteArrayMinecraftOutputStream uncompressedOutputStream = MinecraftOutputStream.createByteArrayOutputStream()) {
            uncompressedOutputStream.writeVarInt(packet.getId());
            packet.write(uncompressedOutputStream);
            uncompressed = uncompressedOutputStream.toByteArray();
        }

        final byte[] dataLengthAndData;

        if (uncompressed.length < threshold) {
            try (final ByteArrayMinecraftOutputStream dataLengthAndDataOutputStream = MinecraftOutputStream.createByteArrayOutputStream()) {
                dataLengthAndDataOutputStream.writeVarInt(0);
                dataLengthAndDataOutputStream.writeBytes(uncompressed);
                dataLengthAndData = dataLengthAndDataOutputStream.toByteArray();
            }
        } else {
            final ByteArrayMinecraftOutputStream byteArrayCompressedOutputStream = MinecraftOutputStream.createByteArrayOutputStream();
            try (final MinecraftOutputStream compressedOutputStream = compression.compressed(byteArrayCompressedOutputStream)) {
                compressedOutputStream.writeLengthPrefixedBytes(uncompressed);
                dataLengthAndData = byteArrayCompressedOutputStream.toByteArray();
            }
        }

        outputStream.writeLengthPrefixedBytes(dataLengthAndData);
    }

    @Override
    public @NotNull Packet decode(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        try (final ByteArrayMinecraftInputStream packetInputStream = MinecraftInputStream.createByteArrayInputStream(inputStream.readLengthPrefixedBytes())) {
            final int dataLength = packetInputStream.readVarInt();

            final MinecraftInputStream decompressedDataInputStream;

            if (dataLength == 0) {
                decompressedDataInputStream = packetInputStream;
            } else {
                decompressedDataInputStream = compression.decompressed(packetInputStream);
            }

            try {
                final int id = decompressedDataInputStream.readVarInt();

                return stateful.getPacketRegistry()
                        .getPacketDecoder(id)
                        .orElseThrow(() -> new DecodeException("Unknown packet decoder for id 0x" + Integer.toHexString(id), true))
                        .decode(decompressedDataInputStream);
            } finally {
                decompressedDataInputStream.close();
            }
        }
    }
}
