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

import java.util.function.Function;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class UpdateScorePacket implements ClientBoundPacket {

    private final static int MAX_ENTITY_NAME_LENGTH = 40;
    private final static int MAX_OBJECTIVE_NAME_LENGTH = 40;

    private final String entityName;
    private final Action action;

    @Contract(pure = true)
    public UpdateScorePacket(final @NotNull String entityName, final @NotNull Action action) {

        Preconditions.requireNotNull(entityName, "entityName");
        Preconditions.requireRange(entityName.length(), 0, MAX_ENTITY_NAME_LENGTH,
                "entityName.length()");
        Preconditions.requireNotNull(action, "action");

        this.entityName = entityName;
        this.action = action;
    }

    @Contract(mutates = "param")
    public UpdateScorePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityName = inputStream.readString(MAX_ENTITY_NAME_LENGTH);
        final ActionType actionType = inputStream.readVarIntEnum(ActionType.class);
        this.action = actionType.create(inputStream);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeString(entityName)
                .writeWritable(action);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.UPDATE_SCORE;
    }

    @Contract(pure = true)
    public @NotNull String getEntityName() {

        return entityName;
    }

    @Contract(pure = true)
    public @NotNull Action getAction() {

        return action;
    }

    public enum ActionType {

        CREATE_OR_UPDATE(CreateOrUpdateAction::new),
        REMOVE(RemoveAction::new);

        private final Function<StreamIn, Action> factory;

        @Contract(pure = true)
        ActionType(final @NotNull Function<@NotNull StreamIn, @NotNull Action> factory) {

            this.factory = factory;
        }

        public @NotNull Action create(final @NotNull StreamIn inputStream) {

            return factory.apply(inputStream);
        }
    }

    public interface Action extends Writable {

        @Contract(pure = true)
        @NotNull String getObjectiveName();

        @Contract(pure = true)
        @NotNull ActionType getType();
    }

    public static final class CreateOrUpdateAction implements Action {

        private final String objectiveName;
        private final int value;

        @Contract(pure = true)
        public CreateOrUpdateAction(final @NotNull String objectiveName, final int value) {

            Preconditions.requireNotNull(objectiveName, "objectiveName");
            Preconditions.requireRange(objectiveName.length(), 0, MAX_OBJECTIVE_NAME_LENGTH,
                    "objectiveName.length()");

            this.objectiveName = objectiveName;
            this.value = value;
        }

        @Contract(mutates = "param")
        public CreateOrUpdateAction(final @NotNull StreamIn inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.objectiveName = inputStream.readString(MAX_OBJECTIVE_NAME_LENGTH);
            this.value = inputStream.readVarInt();
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeString(objectiveName)
                    .writeVarInt(value);
        }

        @Override
        public @NotNull String getObjectiveName() {

            return objectiveName;
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.CREATE_OR_UPDATE;
        }

        @Contract(pure = true)
        public int getValue() {

            return value;
        }
    }

    public static final class RemoveAction implements Action {

        private final String objectiveName;

        @Contract(pure = true)
        public RemoveAction(final @NotNull String objectiveName) {

            Preconditions.requireNotNull(objectiveName, "objectiveName");
            Preconditions.requireRange(objectiveName.length(), 0, MAX_OBJECTIVE_NAME_LENGTH,
                    "objectiveName.length()");

            this.objectiveName = objectiveName;
        }

        @Contract(mutates = "param")
        public RemoveAction(final @NotNull StreamIn inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.objectiveName = inputStream.readString(MAX_OBJECTIVE_NAME_LENGTH);
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeString(objectiveName);
        }

        @Override
        public @NotNull String getObjectiveName() {

            return objectiveName;
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.REMOVE;
        }
    }
}
