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

package io.github.sparky983.diorite.net.packet.clientbound.play;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class DisplayScoreboardPacket implements ClientBoundPacket {

    private final static int MAX_SCORE_NAME_LENGTH = 16;

    private final Position position;
    private final String name;

    @Contract(pure = true)
    public DisplayScoreboardPacket(final @NotNull Position position, final @NotNull String name) {

        Preconditions.requireNotNull(position, "position");
        Preconditions.requireRange(name.length(), 0, MAX_SCORE_NAME_LENGTH, "name.length()");

        this.position = position;
        this.name = name;
    }

    @Contract(mutates = "param")
    public DisplayScoreboardPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.position = inputStream.readVarIntEnum(Position.class);
        this.name = inputStream.readString(MAX_SCORE_NAME_LENGTH);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarIntEnum(position)
                .writeString(name);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.DISPLAY_SCOREBOARD;
    }

    @Contract(pure = true)
    public @NotNull Position getPosition() {

        return position;
    }

    @Contract(pure = true)
    public @NotNull String getName() {

        return name;
    }

    public enum Position {

        LIST(false),
        SIDEBAR(false),
        BELOW_NAME(false),
        TEAM_SPECIFIC_SIDEBAR_BLACK(true),
        TEAM_SPECIFIC_SIDEBAR_DARK_BLUE(true),
        TEAM_SPECIFIC_SIDEBAR_DARK_GREEN(true),
        TEAM_SPECIFIC_SIDEBAR_DARK_CYAN(true),
        TEAM_SPECIFIC_SIDEBAR_DARK_RED(true),
        TEAM_SPECIFIC_SIDEBAR_PURPLE(true),
        TEAM_SPECIFIC_SIDEBAR_GOLD(true),
        TEAM_SPECIFIC_SIDEBAR_GRAY(true),
        TEAM_SPECIFIC_SIDEBAR_DARK_GRAY(true),
        TEAM_SPECIFIC_SIDEBAR_BLUE(true),
        TEAM_SPECIFIC_SIDEBAR_GREEN(true),
        TEAM_SPECIFIC_SIDEBAR_CYAN(true),
        TEAM_SPECIFIC_SIDEBAR_RED(true),
        TEAM_SPECIFIC_SIDEBAR_PINK(true),
        TEAM_SPECIFIC_SIDEBAR_YELLOW(true),
        TEAM_SPECIFIC_SIDEBAR_WHITE(true);

        private final boolean isTeamSpecific;

        Position(final boolean isTeamSpecific) {

            this.isTeamSpecific = isTeamSpecific;
        }
    }
}
