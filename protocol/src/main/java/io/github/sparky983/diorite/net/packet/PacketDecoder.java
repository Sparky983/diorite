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

import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.MinecraftInputStream;

/**
 * Decodes packets.
 *
 * @author Sparky983
 * @param <T> The type of the packet to decode.
 * @since 1.0.0
 */
@FunctionalInterface
public interface PacketDecoder<T extends Packet> {

    /**
     * Decodes the packet from the inputStream.
     *
     * @param inputStream The input stream to decode the packet from.
     * @return The decoded packet.
     */
    @NotNull T decode(@NotNull MinecraftInputStream inputStream);
}
