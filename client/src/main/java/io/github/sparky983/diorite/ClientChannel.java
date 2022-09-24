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
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.RuntimeIOException;
import io.github.sparky983.diorite.net.Channel;
import io.github.sparky983.diorite.net.ChannelState;
import io.github.sparky983.diorite.net.packet.Packet;
import io.github.sparky983.diorite.net.packet.PacketRegistries;
import io.github.sparky983.diorite.net.packet.PacketRegistry;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.format.PacketFormat;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.util.Preconditions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

final class ClientChannel implements Channel<ClientBoundPacket, ServerBoundPacket> {

    private final Socket client;
    private final MinecraftOutputStream outputStream;
    private final MinecraftInputStream inputStream;

    private final PacketListener packetListener;

    private final ExecutorService executor;

    private final Sinks.Many<Packet> packets;

    private ChannelState state;
    private PacketFormat packetFormat = PacketFormat.uncompressed(this);

    private PacketRegistry packetRegistry = PacketRegistry.EMPTY;

    public ClientChannel(final @NotNull InetSocketAddress address,
                         final @NotNull ExecutorService executor,
                         final Sinks.@NotNull Many<Packet> packets) {

        try {
            client = new Socket(address.getAddress(), address.getPort());
            outputStream = MinecraftOutputStream.from(client.getOutputStream());
            inputStream = MinecraftInputStream.from(client.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }

        state = ChannelState.HANDSHAKING;
        this.executor = executor;
        this.packets = packets;
        this.packetListener = new PacketListener(packets, this, inputStream, packetFormat);
        executor.submit(this.packetListener);
    }

    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int getCompressionThreshold() {

        return 0;
    }

    @Override
    public void setCompression(final @Range(from = 0, to = Integer.MAX_VALUE) int threshold) {

        // TODO(Sparky983): Implement compression
        throw new UnsupportedOperationException("ClientChannel does not support compression");
    }

    @Override
    public @NotNull PacketFormat getPacketFormat() {

        return packetFormat;
    }

    @Override
    public void setPacketFormat(final @NotNull PacketFormat packetFormat) {

        Preconditions.requireNotNull(packetFormat, "packetFormat");

        this.packetFormat = packetFormat;
    }

    @Override
    public @NotNull <T extends ClientBoundPacket> Flux<T> on(final @NotNull Class<T> packetType) {

        return packetListener.getPackets()
                .asFlux()
                .ofType(packetType);
    }

    @Override
    public @NotNull Mono<Void> sendPacket(final @NotNull ServerBoundPacket packet) {

        return Mono.fromFuture(
                CompletableFuture.runAsync(
                        () -> packetFormat.encode(packet, outputStream),
                        executor
                )
        );
    }

    @Override
    public @NotNull ChannelState getState() {

        return state;
    }

    @Override
    public void setState(final @NotNull ChannelState state) {

        Preconditions.requireNotNull(state, "state");

        switch (state) {
            case NOT_CONNECTED:
                throw new IllegalArgumentException("Cannot set state to NOT_CONNECTED");
            case HANDSHAKING:
                this.packetRegistry = PacketRegistries.Client.HANDSHAKING;
                break;
            case STATUS:
                this.packetRegistry = PacketRegistries.Client.STATUS;
                break;
            case LOGIN:
                this.packetRegistry = PacketRegistries.Client.LOGIN;
                break;
            case PLAY:
                this.packetRegistry = PacketRegistries.Client.PLAY;
                break;
        }
        this.state = state;
    }

    @Override
    public @NotNull PacketRegistry getPacketRegistry() {

        return packetRegistry;
    }

    @Override
    public void close() {

        try {
            client.close();
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }
    }
}
