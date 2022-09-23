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

package io.github.sparky983.diorite.net;

/**
 * Represents the state of a channel.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public enum ChannelState {

    /**
     * Not a real state. Just the state that is returned before the client is connected or after
     * it is disconnected.
     *
     * @since 1.0.0
     */
    NOT_CONNECTED,

    /**
     * The handshaking state.
     *
     * @since 1.0.0
     */
    HANDSHAKING,

    /**
     * The state used for server list ping and status.
     *
     * @since 1.0.0
     */
    STATUS,

    /**
     * The state used during login.
     *
     * @since 1.0.0
     */
    LOGIN,

    /**
     * The state while the player is playing.
     *
     * @since 1.0.0
     */
    PLAY
}
