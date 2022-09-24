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

package io.github.sparky983.diorite.net.packet.format;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.ByteArrayMinecraftOutputStream;
import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.Stateful;
import io.github.sparky983.diorite.net.packet.Packet;
import io.github.sparky983.diorite.net.packet.PacketDecoder;
import io.github.sparky983.diorite.util.Preconditions;

final class UncompressedPacketFormat implements PacketFormat {

    private final Stateful stateful;

    @Contract(pure = true)
    UncompressedPacketFormat(final @NotNull Stateful stateful) {

        Preconditions.requireNotNull(stateful, "stateful");

        this.stateful = stateful;
    }

    @Override
    public void encode(final @NotNull Packet packet,
                       final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(packet, "packet");
        Preconditions.requireNotNull(outputStream, "outputStream");

        final ByteArrayMinecraftOutputStream byteArrayOutputStream =
                MinecraftOutputStream.createByteArrayOutputStream();

        byteArrayOutputStream.writeVarInt(packet.getId());

        packet.write(byteArrayOutputStream);

        final byte[] data = byteArrayOutputStream.toByteArray();

         synchronized (this) {
             outputStream.writeLengthPrefixedBytes(data);
         }
    }

    @Override
    public @NotNull Packet decode(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        final int length = inputStream.readVarInt();

        final byte[] data = inputStream.readBytes(length);

        final MinecraftInputStream byteArrayInputStream = MinecraftInputStream.createByteArrayInputStream(data);

        final int id = byteArrayInputStream.readVarInt();

        final PacketDecoder<?> decoder = stateful.getPacketRegistry()
                .getPacketDecoder(id)
                .orElseThrow(() -> new DecodeException(
                        "No packet encoder for id 0x" + Integer.toHexString(id), true));

        try {
            return decoder.decode(byteArrayInputStream);
        } catch (final DecodeException e) {
            throw new DecodeException(e, true);
        }
    }
}
