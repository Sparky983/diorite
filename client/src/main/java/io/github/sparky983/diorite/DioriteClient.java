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

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import io.github.sparky983.diorite.net.ChannelState;
import io.github.sparky983.diorite.net.annotations.Port;
import reactor.core.publisher.Mono;

/**
 * A Minecraft Java Edition client implementation.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface DioriteClient extends AutoCloseable {

    String DEFAULT_HOST = "localhost";
    @Port int DEFAULT_PORT = 25565;

    @Contract(value = "-> new", pure = true)
    static @NotNull Builder builder() {

        return new DioriteClientImpl.BuilderImpl();
    }

    /**
     * Returns the host.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    @NotNull String getHost();

    /**
     * Returns the port.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    @Port int getPort();

    /**
     * Returns the client's state.
     *
     * @since 1.0.0
     */
    @NotNull ChannelState getState();

    /**
     * Returns the client's username.
     *
     * @since 1.0.0
     */
    @NotNull String getName();

    /**
     * Returns the protocol version.
     *
     * @since 1.0.0
     */
    @Range(from = 0, to = Integer.MAX_VALUE) int getProtocolVersion();

    /**
     * Sends the specified chat message.
     *
     * @param message The message to send.
     * @return A completable that completes if that chat was successful.
     * @throws IllegalStateException if the player is not in play state.
     * @since 1.0.0
     */
    @NotNull Mono<Void> chat(@NotNull String message);

    /**
     * Sends the specified command.
     *
     * @param command The command.
     * @param args The command arguments.
     * @return A completable that completes if the command was sent successfully.
     */
    @NotNull Mono<Void> command(@NotNull String command, @NotNull String @NotNull ... args);

    /**
     * Disconnects the client from the server.
     *
     * @throws IllegalStateException if the player is not connected.
     * @since 1.0.0
     */
    @NotNull Mono<Void> disconnect();

    @Override
    void close();

    /**
     * Waits until the connection closes.
     *
     * @since 1.0.0
     */
    @Blocking
    void awaitDisconnect();

    /**
     * A client builder.
     *
     * @since 1.0.0
     */
    interface Builder {

        /**
         * Specifies the server host.
         *
         * @param host The host.
         * @return The builder instance (for chaining).
         * @throws NullPointerException if the specified host is {@code null}.
         * @since 1.0.0
         */
        @Contract(value = "_ -> this")
        @NotNull Builder host(@NotNull String host);

        /**
         * Specifies the server port.
         *
         * @param port The port.
         * @return The builder instance (for chaining).
         * @throws IllegalArgumentException if the specified port is invalid.
         * @since 1.0.0
         */
        @Contract(value = "_ -> this")
        @NotNull Builder port(@Port int port);

        @ApiStatus.Experimental
        @Contract(value = "_ -> this")
        @NotNull Builder name(@NotNull String name);

        /**
         * Sets the protocol version.
         * <p>
         * This is unsafe because packets from unsupported versions will not check the protocol
         * version, only the handshake protocol version is affected.
         *
         * @param protocolVersion The protocol version.
         * @return The builder instance (for chaining).
         * @since 1.0.0
         */
        @ApiStatus.Experimental
        @Contract(value = "_ -> this")
        @NotNull Builder unsafe_ProtocolVersion(@Range(from = 0, to = Integer.MAX_VALUE) int protocolVersion);

        /**
         * Connects the client to the server.
         *
         * @return The client.
         * @since 1.0.0
         */
        @Blocking
        @Contract(value = "-> new")
        @NotNull DioriteClient connect();

        /**
         * Connects the client to the server asynchronously.
         *
         * @return Once connected, the client.
         * @since 1.0.0
         */
        @Contract(value = "-> new")
        @NotNull Mono<DioriteClient> connectAsync();
    }
}
