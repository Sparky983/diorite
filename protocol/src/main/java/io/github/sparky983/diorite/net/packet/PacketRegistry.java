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

package io.github.sparky983.diorite.net.packet;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import io.github.sparky983.diorite.util.Preconditions;

/**
 * Contains a registry of packet encoders.
 * <p>
 * The implementation is not thread-safe, however multiple threads shouldn't need to modify data at
 * the same time.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public final class PacketRegistry {

    public static final PacketRegistry EMPTY = builder().build();

    private final Map<Integer, PacketDecoder<?>> packetDecoders;

    @Contract(pure = true)
    private PacketRegistry(final @NotNull Map<Integer, PacketDecoder<?>> packetDecoders) {

        this.packetDecoders = new HashMap<>(packetDecoders);
    }

    @Contract(pure = true)
    public static @NotNull Builder builder() {

        return new PacketRegistry.Builder();
    }

    /**
     * Returns the packet decoder for the specified packet id.
     *
     * @param packetId The packet id.
     * @return The packet decoder.
     * @throws NullPointerException if packetId is {@code null}.
     * @since 1.0.0
     */
    @Contract(pure = true)
    public @NotNull Optional<PacketDecoder<?>> getPacketDecoder(final int packetId) {

        return Optional.ofNullable(packetDecoders.get(packetId));
    }

    public static final class Builder {

        private final Map<Integer, PacketDecoder<?>> packetDecoders = new TreeMap<>();

        /**
         * Registers a packet decoder of the specified id.
         *
         * @param packetId The packet id.
         * @param packetDecoder The decoder.
         * @param <T> The packet type.
         * @return The packet registry instance (for chaining).
         * @throws IllegalStateException if a packet with the specified id has already been
         *         registered.
         * @since 1.0.0
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        public <T extends Packet> Builder registerPacket(
                final int packetId,
                final @NotNull PacketDecoder<T> packetDecoder) {

            Preconditions.requireNotNull(packetDecoder, "packetDecoder");

            final PacketDecoder<?> previousDecoder = packetDecoders.putIfAbsent(packetId,
                    packetDecoder);

            if (previousDecoder != null) {
                throw new IllegalStateException("Decoder with id 0x" + Integer.toHexString(packetId)
                        + " has already been registered");
            }

            return this;
        }

        @Contract(value = "-> new")
        public @NotNull PacketRegistry build() {

            return new PacketRegistry(packetDecoders);
        }
    }
}
