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

package io.github.sparky983.diorite.net.packet.serverbound;

import io.github.sparky983.diorite.net.ChannelState;

/**
 * A utility class containing all server bound packets.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public final class ServerBoundPacketId {

    private ServerBoundPacketId() {

    }

    /**
     * Server bound packets for the {@link ChannelState#HANDSHAKING} state.
     *
     * @since 1.0.0
     */
    public static final class Handshaking {

        private Handshaking() {

        }

        public static final int HANDSHAKE = 0x00;
    }

    /**
     * Server bound packets for the {@link ChannelState#STATUS} state.
     *
     * @since 1.0.0
     */
    public static final class Status {

        private Status() {

        }

        public static final int REQUEST = 0x00;
        public static final int PING = 0x01;
    }

    /**
     * Server bound packets for the {@link ChannelState#LOGIN} state.
     *
     * @since 1.0.0
     */
    public static final class Login {

        private Login() {

        }

        public static final int LOGIN_START = 0x00;
        public static final int ENCRYPTION_RESPONSE = 0x01;
        public static final int LOGIN_PLUGIN_RESPONSE = 0x02;
    }

    /**
     * Server bound packets for the {@link ChannelState#PLAY} state.
     *
     * @since 1.0.0
     */
    public static final class Play {

        private Play() {

        }

        public static final int TELEPORT_CONFIRM = 0x00;
        public static final int QUERY_BLOCK_NBT = 0x01;
        // public static final int SET_DIFFICULTY = 0x02;
        // only sent in single-player
        public static final int CHAT_MESSAGE = 0x03;
        public static final int CLIENT_STATUS = 0x04;
        public static final int CLIENT_SETTINGS = 0x05;
        public static final int TAB_COMPLETE = 0x06;
        public static final int CLICK_WINDOW_BUTTON = 0x07;
        public static final int CLICK_WINDOW = 0x08;
        public static final int CLOSE_WINDOW = 0x09;
        public static final int PLUGIN_MESSAGE = 0x0A;
        public static final int EDIT_BOOK = 0x0B;
        public static final int QUERY_ENTITY_NBT = 0x0C;
        public static final int INTERACT_ENTITY = 0x0D;
        public static final int GENERATE_STRUCTURE = 0x0E;
        public static final int KEEP_ALIVE = 0x0F;
        // public static final int LOCK_DIFFICULTY = 0x10;
        // Only sent in single-player
        public static final int PLAYER_POSITION = 0x11;
        public static final int PLAYER_POSITION_AND_ROTATION = 0x12;
        public static final int PLAYER_ROTATION = 0x13;
        public static final int PLAYER_MOVEMENT = 0x14;
        public static final int VEHICLE_MOVE = 0x15;
        public static final int STEER_BOAT = 0x16;
        public static final int PICK_ITEM = 0x17;
        public static final int CRAFT_RECIPE_REQUEST = 0x18;
        public static final int PLAYER_ABILITIES = 0x19;
        public static final int PLAYER_DIGGING = 0x1A;
        public static final int ENTITY_ACTION = 0x1B;
        public static final int STEER_VEHICLE = 0x1C;
        public static final int PONG = 0x1D;
        public static final int SET_RECIPE_BOOK_STATE = 0x1E;
        public static final int SET_DISPLAYED_RECIPE = 0x1F;
        public static final int NAME_ITEM = 0x20;
        public static final int RESOURCE_PACK_STATUS = 0x21;
        public static final int ADVANCEMENT_TAB = 0x22;
        public static final int SELECT_TRADE = 0x23;
        public static final int SET_BEACON_EFFECT = 0x24;
        public static final int HELD_ITEM_CHANGE = 0x25;
        public static final int UPDATE_COMMAND_BLOCK = 0x26;
        public static final int UPDATE_COMMAND_BLOCK_MINECART = 0x27;
        public static final int CREATIVE_INVENTORY_ACTION = 0x28;
        public static final int UPDATE_JIGSAW_BLOCK = 0x29;
        public static final int UPDATE_STRUCTURE_BLOCK = 0x2A;
        public static final int UPDATE_SIGN = 0x2B;
        public static final int ANIMATION = 0x2C;
        public static final int SPECTATE = 0x2D;
        public static final int PLAYER_BLOCK_PLACEMENT = 0x2E;
        public static final int USE_ITEM = 0x2F;
    }
}
