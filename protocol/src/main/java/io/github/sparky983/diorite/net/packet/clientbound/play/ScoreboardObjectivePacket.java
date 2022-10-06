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

import net.kyori.adventure.text.Component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class ScoreboardObjectivePacket implements ClientBoundPacket {

    private static final int MAX_OBJECTIVE_NAME_LENGTH = 16;

    private final String objectiveName;
    private final @Nullable Action action;

    @Contract(pure = true)
    public ScoreboardObjectivePacket(final @NotNull String objectiveName,
            final @Nullable Action action) {

        Preconditions.requireNotNull(objectiveName, "objectiveName");
        Preconditions.requireRange(objectiveName.length(), 0, MAX_OBJECTIVE_NAME_LENGTH,
                "objectiveName.length()");

        this.objectiveName = objectiveName;
        this.action = action;
    }

    @Contract(mutates = "param")
    public ScoreboardObjectivePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.objectiveName = inputStream.readString(MAX_OBJECTIVE_NAME_LENGTH);
        this.action = Action.readAction(inputStream);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeString(objectiveName);

        if (action != null) {
            outputStream.writeWritable(action);
        } else {
            outputStream.writeVarIntEnum(ActionType.REMOVE);
        }
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SCOREBOARD_OBJECTIVE;
    }

    @Contract(pure = true)
    public @NotNull String getObjectiveName() {

        return objectiveName;
    }

    @Contract(pure = true)
    public @NotNull ActionType getMode() {

        return action != null ? action.getMode() : ActionType.REMOVE;
    }

    @Contract(pure = true)
    public @Nullable Action getAction() {

        return action;
    }

    public enum ActionType {

        CREATE,
        REMOVE,
        UPDATE
    }

    public static final class Action implements Writable {

        private final ActionType mode;
        private final Component objectiveValue;
        private final Type type;

        @Contract(pure = true)
        public Action(final @NotNull ActionType mode,
                final @NotNull Component objectiveValue,
                final @NotNull Type type) {

            Preconditions.requireNotNull(mode, "mode");
            Preconditions.requireNotNull(objectiveValue, "objectiveValue");
            Preconditions.requireNotNull(type, "type");

            this.mode = mode;
            this.objectiveValue = objectiveValue;
            this.type = type;
        }

        public static @Nullable Action readAction(final @NotNull StreamIn inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            final ActionType actionType = inputStream.readVarIntEnum(ActionType.class);

            if (actionType == ActionType.REMOVE) {
                return null;
            }

            final Component objectiveValue = inputStream.readComponent();
            final Type type = inputStream.readVarIntEnum(Type.class);

            return new Action(
                    actionType,
                    objectiveValue,
                    type
            );
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeVarIntEnum(mode)
                    .writeComponent(objectiveValue)
                    .writeVarIntEnum(type);
        }

        @Contract(pure = true)
        public @NotNull ActionType getMode() {

            return mode;
        }

        @Contract(pure = true)
        public @NotNull Component getObjectiveValue() {

            return objectiveValue;
        }

        @Contract(pure = true)
        public @NotNull Type getType() {

            return type;
        }

        public enum Type {

            INTEGER,
            HEARTS
        }
    }
}
