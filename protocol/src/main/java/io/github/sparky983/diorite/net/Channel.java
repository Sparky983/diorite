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

package io.github.sparky983.diorite.net;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import io.github.sparky983.diorite.net.packet.Packet;
import io.github.sparky983.diorite.net.packet.format.PacketFormat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * A channel is a two-way connection (client to server or server to client).
 *
 * @author Sparky983
 * @param <In> The incoming packet type.
 * @param <Out> The outgoing packet type.
 * @since 1.0.0
 */
public interface Channel<In extends Packet, Out extends Packet> extends Stateful, AutoCloseable {

    /**
     * Returns the compression threshold in bytes. 0 means compression is disabled.
     *
     * @since 1.0.0
     */
    @Range(from = 0, to = Integer.MAX_VALUE) int getCompressionThreshold();

    /**
     * Sets the compression threshold. 0 disables compression.
     *
     * @throws IllegalArgumentException if threshold is less than 0.
     * @since 1.0.0
     */
    void setCompression(@Range(from = 0, to = Integer.MAX_VALUE) int threshold);

    /**
     * Returns the current packet format.
     * <p>
     * The Minecraft protocol specifies two formats: compressed, and uncompressed.
     *
     * @since 1.0.0
     */
    @NotNull PacketFormat getPacketFormat();

    /**
     * Sets the packet format to the one specified.
     *
     * @param packetFormat The packet format.
     * @throws NullPointerException if packetFormat is {@code null}.
     * @since 1.0.0
     */
    void setPacketFormat(@NotNull PacketFormat packetFormat);

    /**
     * Subscribes to the receival of the specified packet.
     *
     * @param packetType The packet class.
     * @param <T> The type of the packet.
     * @return A flowable that
     * @throws NullPointerException if packetType is {@code null}.
     * @since 1.0.0
     */
    <T extends In> @NotNull Flux<T> on(@NotNull Class<T> packetType);

    @NotNull Mono<Void> sendPacket(@NotNull Out packet);

    @Override
    void close();
}
