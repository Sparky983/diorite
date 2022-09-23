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

import io.github.sparky983.diorite.net.packet.PacketRegistry;

/**
 * Something that has a connection state.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface Stateful {

    /**
     * Returns the current state.
     *
     * @since 1.0.0
     */
    @NotNull ChannelState getState();

    /**
     * Sets the current state.
     * <p>
     * Note: this is not thread-safe as the only time the state should change is during handshake
     * and login which is done synchronously.
     *
     * @throws IllegalArgumentException if the specified state is {@link ChannelState#NOT_CONNECTED}.
     * @throws IllegalStateException if the state cannot be changed to the specified state.
     * @since 1.0.0
     */
    void setState(@NotNull ChannelState state);

    /**
     * Returns a packet registry containing all usable packets in the current state.
     * <p>
     * A {@link ChannelState#NOT_CONNECTED} returns an empty packet registry.
     *
     * @since 1.0.0
     */
    @NotNull PacketRegistry getPacketRegistry();
}
