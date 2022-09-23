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

package io.github.sparky983.diorite;

import org.jetbrains.annotations.NotNull;

import java.io.EOFException;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.RuntimeIOException;
import io.github.sparky983.diorite.net.packet.Packet;
import io.github.sparky983.diorite.net.packet.format.PacketFormat;
import io.github.sparky983.diorite.util.Preconditions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

final class IncomingPacketListener implements Runnable {

    private final MinecraftInputStream inputStream;
    private PacketFormat packetFormat;

    private final Sinks.Many<Packet> packets;

    public IncomingPacketListener(final @NotNull MinecraftInputStream inputStream, final @NotNull PacketFormat initialPacketFormat) {

        Preconditions.requireNotNull(inputStream, "inputStream");
        Preconditions.requireNotNull(initialPacketFormat, "initialPacketFormat");

        packets = Sinks.many().replay().all();

        this.inputStream = inputStream;
        this.packetFormat = initialPacketFormat;
    }

    public void setPacketFormat(final @NotNull PacketFormat packetFormat) {

        Preconditions.requireNotNull(packetFormat);

        this.packetFormat = packetFormat;
    }

    public <T extends Packet> Flux<T> on(final @NotNull Class<T> packetType) {

        return packets.asFlux()
                .ofType(packetType);
    }

    @Override
    public void run() {

        while (true) {
            try {
                final Packet packet = packetFormat.decode(inputStream);
                packets.emitNext(packet, Sinks.EmitFailureHandler.FAIL_FAST);
            } catch (final RuntimeIOException e) {
                if (e.getCause() instanceof EOFException) {
                    System.err.println(e.getMessage());
                    break;
                }
                throw e;
            }
        }

        packets.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
