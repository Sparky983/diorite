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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.net.ChannelState;
import io.github.sparky983.diorite.net.Stateful;
import io.github.sparky983.diorite.net.packet.Packet;
import io.github.sparky983.diorite.net.packet.format.PacketFormat;
import io.github.sparky983.diorite.util.Preconditions;
import reactor.core.publisher.Sinks;

final class PacketListener implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketListener.class);

    private final Sinks.Many<Packet> packets;
    private final Stateful stateful;
    private final StreamIn inputStream;
    private PacketFormat packetFormat;

    @Contract(pure = true)
    public PacketListener(final Sinks.@NotNull Many<Packet> packets,
                          final @NotNull Stateful stateful,
                          final @NotNull StreamIn inputStream,
                          final @NotNull PacketFormat initialPacketFormat) {

        this.packets = packets;
        this.stateful = stateful;
        this.inputStream = inputStream;
        this.packetFormat = initialPacketFormat;
    }

    @Contract(mutates = "this")
    public void setPacketFormat(final @NotNull PacketFormat packetFormat) {

        Preconditions.requireNotNull(packetFormat);

        this.packetFormat = packetFormat;
    }

    @Contract(pure = true)
    public Sinks.@NotNull Many<Packet> getPackets() {

        return packets;
    }

    @Override
    public void run() {

        while (stateful.getState() != ChannelState.DISCONNECTED) {
            try {
                final Packet packet = packetFormat.decode(inputStream);
                packets.emitNext(packet, Sinks.EmitFailureHandler.FAIL_FAST);
            } catch (final DecodeException e) {
                if (!e.isIgnorable()) {
                    e.printStackTrace();
                    stateful.close();
                    break;
                }
                LOGGER.warn("Ignorable error: {}", e.getMessage());
            } catch (final Exception e) {
                e.printStackTrace();
                stateful.close();
                break;
            }
        }
    }
}
