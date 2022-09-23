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

import java.util.List;
import java.util.function.Function;

import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class TeamsPacket implements ClientBoundPacket {

    private static final int MAX_TEAM_NAME_LENGTH = 16;

    private final String teamName;
    private final Action action;

    @Contract(pure = true)
    public TeamsPacket(final @NotNull String teamName, final @NotNull Action action) {

        Preconditions.requireNotNull(teamName, "teamName");
        Preconditions.requireRange(teamName.length(), 0, MAX_TEAM_NAME_LENGTH, "teamName.length()");
        Preconditions.requireNotNull(action, "action");

        this.teamName = teamName;
        final ActionType actionType = action.getType();
        this.action = action;
    }

    @Contract(mutates = "param")
    public TeamsPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.teamName = inputStream.readString(MAX_TEAM_NAME_LENGTH);
        final ActionType actionType = inputStream.readVarIntEnum(ActionType.class);
        this.action = actionType.create(inputStream);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeString(teamName)
                .writeVarIntEnum(getActionType())
                .writeWritable(action);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.TEAMS;
    }

    @Contract(pure = true)
    public @NotNull String getTeamName() {

        return teamName;
    }

    @Contract(pure = true)
    public @NotNull ActionType getActionType() {

        return action.getType();
    }

    @Contract(pure = true)
    public @NotNull Action getAction() {

        return action;
    }

    // TODO(Sparky983): better design for this
    public static final class CreateAction extends UpdateAction {

        static final int MAX_ENTITY_LENGTH = 40;

        private final List<String> entities;

        @Contract(pure = true)
        public CreateAction(final @NotNull Component displayName,
                            final byte flags,
                            final TeamsPacket.UpdateAction.@NotNull NameTagVisibility nameTagVisibility,
                            final TeamsPacket.UpdateAction.@NotNull CollisionRule collisionRule,
                            final TeamsPacket.UpdateAction.@NotNull TeamColor teamColor,
                            final @NotNull Component prefix,
                            final @NotNull Component suffix,
                            final @NotNull List<@NotNull String> entities) {

            super(displayName, flags, nameTagVisibility, collisionRule, teamColor, prefix, suffix);

            Preconditions.requireContainsNoNulls(entities, "entities");
            for (final String entity : entities) {
                Preconditions.requireRange(entity.length(), 0, MAX_ENTITY_LENGTH, "entity.length()");
            }

            this.entities = entities;
        }

        @Contract(mutates = "param")
        public CreateAction(final @NotNull MinecraftInputStream inputStream) {

            super(inputStream);

            this.entities = inputStream.readList(() -> {
                final String entity = inputStream.readString();
                if (entity.length() > MAX_ENTITY_LENGTH) {
                    throw new DecodeException("entity.length() cannot be longer than " + MAX_ENTITY_LENGTH);
                }
                return entity;
            });
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            super.write(outputStream);
            outputStream.writeList(entities, MinecraftOutputStream::writeString);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.CREATE;
        }

        @Contract(pure = true)
        public @NotNull List<@NotNull String> getEntities() {

            return entities;
        }
    }

    public static final class RemoveAction implements Action {

        @Contract(pure = true)
        public RemoveAction() {

        }

        @Contract(pure = true)
        public RemoveAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.REMOVE;
        }
    }

    public static class UpdateAction implements Action {

        private static final byte ALLOW_FRIENDLY_FIRE_BIT = 0b00000001;
        private static final byte CAN_SEE_FRIENDLY_INVISIBLE_BIT = 0b00000010;

        private static final int MAX_NAME_TAG_VISIBILITY_LENGTH = 32;
        private static final int MAX_COLLISION_RULE_LENGTH = 32;

        private final Component displayName;
        private final byte flags;
        private final NameTagVisibility nameTagVisibility;
        private final CollisionRule collisionRule;
        private final TeamColor teamColor;
        private final Component prefix;
        private final Component suffix;

        @Contract(pure = true)
        public UpdateAction(final @NotNull Component displayName,
                final byte flags,
                final @NotNull NameTagVisibility nameTagVisibility,
                final @NotNull CollisionRule collisionRule,
                final @NotNull TeamColor teamColor,
                final @NotNull Component prefix,
                final @NotNull Component suffix) {

            Preconditions.requireNotNull(displayName, "displayName");
            Preconditions.requireNotNull(nameTagVisibility, "nameTagVisibility");
            Preconditions.requireNotNull(collisionRule, "collisionRule");
            Preconditions.requireNotNull(teamColor, "teamColor");
            Preconditions.requireNotNull(prefix, "prefix");

            this.displayName = displayName;
            this.flags = flags;
            this.nameTagVisibility = nameTagVisibility;
            this.collisionRule = collisionRule;
            this.teamColor = teamColor;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        @Contract(mutates = "param")
        public UpdateAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.displayName = inputStream.readChat();
            this.flags = inputStream.readByte();
            this.nameTagVisibility = NameTagVisibility.valueOf(inputStream.readString(MAX_NAME_TAG_VISIBILITY_LENGTH));
            this.collisionRule = CollisionRule.valueOf(inputStream.readString(MAX_COLLISION_RULE_LENGTH));
            this.teamColor = inputStream.readVarIntEnum(TeamColor.class);
            this.prefix = inputStream.readChat();
            this.suffix = inputStream.readChat();
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeChat(displayName)
                    .writeByte(flags)
                    .writeString(nameTagVisibility.name())
                    .writeString(collisionRule.name())
                    .writeVarIntEnum(teamColor)
                    .writeChat(prefix)
                    .writeChat(suffix);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.UPDATE;
        }

        @Contract(pure = true)
        public @NotNull Component getDisplayName() {

            return displayName;
        }

        @Contract(pure = true)
        public byte getFlags() {

            return flags;
        }

        @Contract(pure = true)
        public boolean allowsFriendlyFire() {

            return (flags & ALLOW_FRIENDLY_FIRE_BIT) != 0;
        }

        @Contract(pure = true)
        public boolean canSeeFriendlyInvisible() {

            return (flags & CAN_SEE_FRIENDLY_INVISIBLE_BIT) != 0;
        }

        @Contract(pure = true)
        public @NotNull NameTagVisibility getNameTagVisibility() {

            return nameTagVisibility;
        }

        @Contract(pure = true)
        public @NotNull CollisionRule getCollisionRule() {

            return collisionRule;
        }

        @Contract(pure = true)
        public @NotNull TeamColor getTeamColor() {

            return teamColor;
        }

        @Contract(pure = true)
        public @NotNull Component getPrefix() {

            return prefix;
        }

        @Contract(pure = true)
        public @NotNull Component getSuffix() {

            return suffix;
        }

        public enum NameTagVisibility {

            always,
            hideForOtherTeams,
            hideForOwnTeam,
            never
        }

        public enum CollisionRule {

            always,
            pushOtherTeams,
            pushOwnTeam,
            never
        }

        public enum TeamColor {

            BLACK,
            DARK_BLUE,
            DARK_GREEN,
            DARK_CYAN,
            DARK_RED,
            PURPLE,
            GOLD,
            GRAY,
            DARK_GRAY,
            BLUE,
            GREEN,
            CYAN,
            RED,
            PINK,
            YELLOW,
            WHITE,
            OBFUSCATED,
            BOLD,
            STRIKETHROUGH,
            UNDERLINED,
            ITALIC,
            RESET
        }
    }

    public static final class AddEntities implements Action {

        private final List<String> entities;

        @Contract(pure = true)
        public AddEntities(final @NotNull List<@NotNull String> entities) {

            Preconditions.requireContainsNoNulls(entities, "entities");
            for (final String entity : entities) {
                Preconditions.requireRange(entity.length(), 0, TeamsPacket.CreateAction.MAX_ENTITY_LENGTH, "entity.length()");
            }

            this.entities = entities;
        }

        @Contract(mutates = "param")
        public AddEntities(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.entities = inputStream.readList(() -> {
                final String entity = inputStream.readString();
                if (entity.length() > TeamsPacket.CreateAction.MAX_ENTITY_LENGTH) {
                    throw new DecodeException("entity.length() cannot be longer than " + TeamsPacket.CreateAction.MAX_ENTITY_LENGTH);
                }
                return entity;
            });
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeList(entities, MinecraftOutputStream::writeString);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.ADD_ENTITIES;
        }

        @Contract(pure = true)
        public @NotNull List<@NotNull String> getEntities() {

            return entities;
        }
    }

    public static final class RemoveEntities implements Action {

        private final List<String> entities;

        @Contract(pure = true)
        public RemoveEntities(final @NotNull List<@NotNull String> entities) {

            Preconditions.requireContainsNoNulls(entities, "entities");
            for (final String entity : entities) {
                Preconditions.requireRange(entity.length(), 0, TeamsPacket.CreateAction.MAX_ENTITY_LENGTH, "entity.length()");
            }

            this.entities = entities;
        }

        @Contract(mutates = "param")
        public RemoveEntities(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.entities = inputStream.readList(() -> {
                final String entity = inputStream.readString();
                if (entity.length() > TeamsPacket.CreateAction.MAX_ENTITY_LENGTH) {
                    throw new DecodeException("entity.length() cannot be longer than " + TeamsPacket.CreateAction.MAX_ENTITY_LENGTH);
                }
                return entity;
            });
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeList(entities, MinecraftOutputStream::writeString);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.REMOVE_ENTITIES;
        }

        @Contract(pure = true)
        public @NotNull List<@NotNull String> getEntities() {

            return entities;
        }
    }

    public enum ActionType {

        CREATE(CreateAction::new),
        REMOVE(RemoveAction::new),
        UPDATE(UpdateAction::new),
        ADD_ENTITIES(AddEntities::new),
        REMOVE_ENTITIES(RemoveEntities::new);

        private final Function<MinecraftInputStream, Action> factory;

        @Contract(pure = true)
        ActionType(final @NotNull Function<@NotNull MinecraftInputStream, @NotNull Action> factory) {

            this.factory = factory;
        }

        @Contract(mutates = "param")
        public @NotNull Action create(final @NotNull MinecraftInputStream inputStream) {

            return factory.apply(inputStream);
        }
    }

    public interface Action extends Writable {

        @Contract(pure = true)
        @NotNull ActionType getType();
    }
}
