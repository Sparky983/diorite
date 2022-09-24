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

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.sparky983.diorite.net.ChannelState;
import io.github.sparky983.diorite.net.Networking;
import io.github.sparky983.diorite.net.annotations.Port;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.login.LoginSuccessPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.KeepAlivePacket;
import io.github.sparky983.diorite.net.packet.serverbound.handshaking.HandshakePacket;
import io.github.sparky983.diorite.net.packet.serverbound.login.LoginStartPacket;
import io.github.sparky983.diorite.net.packet.serverbound.play.ChatMessagePacket;
import io.github.sparky983.diorite.util.Preconditions;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

final class DioriteClientImpl implements DioriteClient {

    private static final ComponentLogger LOGGER = ComponentLogger.logger(DioriteClientImpl.class);

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final String host;
    private final int port;

    private final String name;
    private final int protocolVersion;

    private ClientChannel clientChannel;

    private DioriteClientImpl(final @NotNull String host,
                              final @Port int port,
                              final int protocolVersion,
                              final @NotNull String name) {

        this.host = host;
        this.port = port;
        this.protocolVersion = protocolVersion;
        this.name = name;
    }

    @Override
    public @NotNull String getHost() {

        return host;
    }

    @Override
    public @Port int getPort() {

        return port;
    }

    @Override
    public @NotNull String getName() {

        return name;
    }

    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int getProtocolVersion() {

        return protocolVersion;
    }

    @Override
    public @NotNull ChannelState getState() {

        if (clientChannel == null) {
            return ChannelState.NOT_CONNECTED;
        }

        return clientChannel.getState();
    }

    @Override
    public @NotNull DioriteClient connect() {

        handshake(ChannelState.LOGIN);

        clientChannel.sendPacket(new LoginStartPacket(name))
                .block();

        final LoginSuccessPacket loginSuccess = clientChannel.on(LoginSuccessPacket.class)
                .blockFirst();

        clientChannel.setState(ChannelState.PLAY);

        clientChannel.on(KeepAlivePacket.class)
                .flatMap((packet) ->
                        clientChannel.sendPacket(new io.github.sparky983.diorite.net.packet.serverbound.play.KeepAlivePacket(packet.getKeepAliveId()))
                )
                .subscribe();

        return this;
    }

    private void handshake(final @NotNull ChannelState nextState) {

        if (getState() != ChannelState.NOT_CONNECTED) {
            throw new IllegalStateException("Client is already connected.");
        }

        clientChannel = new ClientChannel(
                new InetSocketAddress(host, port),
                executor,
                Sinks.many().replay().all()
        );

        clientChannel.sendPacket(new HandshakePacket(protocolVersion, host, port, ChannelState.LOGIN))
                .block();

        clientChannel.setState(nextState);
    }

    @Override
    public int ping() {

        // TODO(Sparky983): Implement ping
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public @NotNull String status() {

        // TODO(Sparky983): Implement status
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public @NotNull Mono<Void> chat(final @NotNull String message) {

        return clientChannel.sendPacket(new ChatMessagePacket(message));
    }

    @Override
    public @NotNull Mono<Void> command(final @NotNull String command, final @NotNull String @NotNull ... args) {

        final StringBuilder absoluteCommand = new StringBuilder('/' + command + ' ');

        for (int i = 0; i < args.length; i++) {
            absoluteCommand.append(' ');

            final String arg = args[0];

            if (arg.contains(" ")) {
                absoluteCommand
                        .append('"')
                        .append(arg)
                        .append('"');
                continue;
            }

            absoluteCommand.append(arg);
        }

        return chat(absoluteCommand + String.join(" ", args));
    }

    @Override
    public @NotNull Mono<Void> disconnect() {

        return Mono.fromFuture(CompletableFuture.runAsync(() -> clientChannel.close(), executor));
    }

    @Override
    public void awaitDisconnect() {

        clientChannel.on(ClientBoundPacket.class).blockLast();
    }

    @Override
    public void close() {

        disconnect().block();
    }

    static class BuilderImpl implements Builder {

        private String name;
        private String host;
        private int port;
        private int protocolVersion;

        @Override
        public @NotNull Builder host(final @NotNull String host) {

            Preconditions.requireNotNull(host, "host");
            this.host = host;
            return this;
        }

        @Override
        public @NotNull Builder port(final @Port int port) {

            Preconditions.requireTrue(Networking.isValidPort(port), "[port] is invalid");
            this.port = port;
            return this;
        }

        @Override
        public @NotNull Builder name(final @NotNull String name) {

            Preconditions.requireNotNull(name, "name");
            this.name = name;
            return this;
        }

        @Override
        public @NotNull Builder unsafe_ProtocolVersion(
                final @Range(from = 0, to = Integer.MAX_VALUE) int protocolVersion) {

            Preconditions.requireRange(protocolVersion, 0, Integer.MAX_VALUE, "protocolVersion");
            this.protocolVersion = protocolVersion;
            return this;
        }

        @Override
        public @NotNull DioriteClient build() {

            if (host == null) {
                host = DEFAULT_HOST;
            }

            if (port == 0) {
                port = DEFAULT_PORT;
            }

            if (protocolVersion == 0) {
                protocolVersion = 758;
            }

            if (name == null) {
                name = "diorite_client";
            }

            return new DioriteClientImpl(host, port, protocolVersion, name);
        }
    }
}
