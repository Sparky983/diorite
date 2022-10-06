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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.sparky983.diorite.net.ChannelState;
import io.github.sparky983.diorite.net.Networking;
import io.github.sparky983.diorite.net.annotations.Port;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.login.LoginSuccessPacket;
import io.github.sparky983.diorite.net.packet.clientbound.login.SetCompressionPacket;
import io.github.sparky983.diorite.net.packet.serverbound.handshaking.HandshakePacket;
import io.github.sparky983.diorite.net.packet.serverbound.login.LoginStartPacket;
import io.github.sparky983.diorite.net.packet.serverbound.play.ChatMessagePacket;
import io.github.sparky983.diorite.net.packet.serverbound.play.KeepAlivePacket;
import io.github.sparky983.diorite.util.Preconditions;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

final class DioriteClientImpl implements DioriteClient {

    private final ExecutorService executor;

    private final String name;
    private final int protocolVersion;

    private final ClientChannel clientChannel;

    private DioriteClientImpl(final @NotNull ClientChannel clientChannel,
            final @NotNull ExecutorService executor,
            final int protocolVersion,
            final @NotNull String name) {

        this.clientChannel = clientChannel;
        this.executor = executor;
        this.protocolVersion = protocolVersion;
        this.name = name;
    }

    @Override
    public @NotNull String getHost() {

        return clientChannel.getHost();
    }

    @Override
    public @Port int getPort() {

        return clientChannel.getPort();
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

        return clientChannel.getState();
    }

    @Override
    public @NotNull Mono<Void> chat(final @NotNull String message) {

        assert getState() == ChannelState.PLAY;

        return clientChannel.sendPacket(new ChatMessagePacket(message));
    }

    @Override
    public @NotNull Mono<Void> command(final @NotNull String command,
            final @NotNull String @NotNull ... args) {

        assert getState() == ChannelState.PLAY;

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

        return Mono.fromFuture(CompletableFuture.runAsync(clientChannel::close, executor));
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

        private String name = "diorite_client";
        private String host = DEFAULT_HOST;
        private int port = DEFAULT_PORT;
        private int protocolVersion = 758;
        private volatile ClientChannel clientChannel;

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

        private void handshake(final @NotNull ExecutorService executor,
                final @NotNull ChannelState nextState) {

            if (clientChannel != null) {
                throw new IllegalStateException("Already connecting");
            }

            clientChannel = new ClientChannel(
                    host,
                    port,
                    executor,
                    Sinks.many().replay().all()
            );

            clientChannel.sendPacket(
                    new HandshakePacket(protocolVersion, host, port, ChannelState.LOGIN)).block();
            clientChannel.setState(nextState);
        }

        private @NotNull DioriteClient connect(final @NotNull ExecutorService executor) {

            handshake(executor, ChannelState.LOGIN);

            clientChannel.sendPacket(new LoginStartPacket(name)).block();

            clientChannel.on(SetCompressionPacket.class)
                    .subscribe((packet) -> clientChannel.setCompression(packet.getThreshold()));

            final LoginSuccessPacket loginSuccess = clientChannel.on(LoginSuccessPacket.class)
                    .blockFirst();

            clientChannel.setState(ChannelState.PLAY);

            clientChannel.on(
                            io.github.sparky983.diorite.net.packet.clientbound.play.KeepAlivePacket.class)
                    .subscribe((packet) ->
                            clientChannel.sendPacket(new KeepAlivePacket(packet.getKeepAliveId()))
                                    .subscribe()
                    );

            return new DioriteClientImpl(
                    clientChannel,
                    executor,
                    758,
                    name
            );
        }

        // TODO(Sparky983): Make connect (blocking) wait for connectAsync, not the other way around

        @Override
        public @NotNull DioriteClient connect() {

            final ExecutorService executorService = Executors.newCachedThreadPool();

            return connect(executorService);
        }

        @Override
        public @NotNull Mono<DioriteClient> connectAsync() {

            final ExecutorService executorS = Executors.newCachedThreadPool();

            return Mono.fromFuture(
                    CompletableFuture.supplyAsync(
                            this::connect,
                            executorS
                    )
            );
        }
    }
}
