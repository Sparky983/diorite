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

package io.github.sparky983.diorite.net.packet.serverbound.play;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class ClientSettingsPacket implements ServerBoundPacket {

    private static final byte CAPE_ENABLED_BIT = (byte) 0b00000001;
    private static final byte JACKET_ENABLED_BIT = (byte) 0b00000010;
    private static final byte LEFT_SLEEVE_ENABLED_BIT = (byte) 0b00000100;
    private static final byte RIGHT_SLEEVE_ENABLED_BIT = (byte) 0b00001000;
    private static final byte LEFT_PANTS_ENABLED_BIT = (byte) 0b00010000;
    private static final byte RIGHT_PANTS_ENABLED_BIT = (byte) 0b00100000;
    private static final byte HAT_ENABLED_BIT = (byte) 0b01000000;

    private final String locale;
    private final byte viewDistance;
    private final ChatMode chatMode;
    private final boolean chatColors;
    private final byte displayedSkinParts;
    private final MainHand mainHand;
    private final boolean disableTextFiltering;

    @Contract(pure = true)
    public ClientSettingsPacket(
            final @NotNull String locale,
            final byte viewDistance,
            final @NotNull ChatMode chatMode,
            final boolean chatColors,
            final byte displayedSkinParts,
            final @NotNull MainHand mainHand,
            final boolean disableTextFiltering) {

        Preconditions.requireNotNull(locale, "locale");
        Preconditions.requireNotNull(chatMode, "chatMode");
        Preconditions.requireNotNull(mainHand, "mainHand");

        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatMode = chatMode;
        this.chatColors = chatColors;
        this.displayedSkinParts = displayedSkinParts;
        this.mainHand = mainHand;
        this.disableTextFiltering = disableTextFiltering;
    }

    @Contract(mutates = "param")
    public ClientSettingsPacket(final @NotNull StreamIn inputStream) {

        this.locale = inputStream.readString(16);
        this.viewDistance = inputStream.readByte();
        this.chatMode = inputStream.readVarIntEnum(ChatMode.class);
        this.chatColors = inputStream.readBoolean();

        this.displayedSkinParts = inputStream.readByte();
        // An unsigned byte but since it's a bit mask, it won't matter

        this.mainHand = inputStream.readVarIntEnum(MainHand.class);

        this.disableTextFiltering = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        outputStream.writeString(locale)
                .writeByte(viewDistance)
                .writeVarIntEnum(chatMode)
                .writeBoolean(chatColors)
                .writeByte(displayedSkinParts)
                .writeVarIntEnum(mainHand)
                .writeBoolean(disableTextFiltering);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.CLIENT_SETTINGS;
    }

    @Contract(pure = true)
    public @NotNull String getLocale() {

        return locale;
    }

    @Contract(pure = true)
    public byte getViewDistance() {

        return viewDistance;
    }

    @Contract(pure = true)
    public @NotNull ChatMode getChatMode() {

        return chatMode;
    }

    @Contract(pure = true)
    public boolean isChatColors() {

        return chatColors;
    }

    @Contract(pure = true)
    public byte getDisplayedSkinParts() {

        return displayedSkinParts;
    }

    @Contract(pure = true)
    public @NotNull MainHand getMainHand() {

        return mainHand;
    }

    @Contract(pure = true)
    public boolean isDisableTextFiltering() {

        return disableTextFiltering;
    }

    @Contract(pure = true)
    public boolean isCapeEnabled() {

        return (displayedSkinParts & CAPE_ENABLED_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isJacketEnabled() {

        return (displayedSkinParts & JACKET_ENABLED_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isLeftSleeveEnabled() {

        return (displayedSkinParts & LEFT_SLEEVE_ENABLED_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isRightSleeveEnabled() {

        return (displayedSkinParts & RIGHT_SLEEVE_ENABLED_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isLeftPantsEnabled() {

        return (displayedSkinParts & LEFT_PANTS_ENABLED_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isRightPantsEnabled() {

        return (displayedSkinParts & RIGHT_PANTS_ENABLED_BIT) != 0;
    }

    @Contract(pure = true)
    public boolean isHatEnabled() {

        return (displayedSkinParts & HAT_ENABLED_BIT) != 0;
    }

    public enum ChatMode {

        ENABLED, COMMANDS_ONLY, HIDDEN
    }

    public enum MainHand {

        LEFT, RIGHT
    }
}
