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

import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;

/**
 * A packet.
 * <p>
 * All packets should have a {@link PacketDecoder} constructor.
 * <p>
 * 2 types of packets exist, {@link ServerBoundPacket} and {@link ClientBoundPacket}.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface Packet extends Writable {

    // TODO(Sparky983): Make all packets final

    /**
     * Writes the packet to the outputStream.
     *
     * @param outputStream The output stream to write to.
     * @since 1.0.0
     */
    @Contract(mutates = "param")
    @Override
    void write(@NotNull MinecraftOutputStream outputStream);

    /**
     * Returns the id of the packet.
     *
     * @since 1.0.0
     */
    @Contract(pure = true)
    int getId();
}
