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

import java.util.UUID;
import java.util.function.Function;

import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class BossBarPacket implements ClientBoundPacket {

    private final UUID bossbarId;
    private final Action action;

    @Contract(pure = true)
    public BossBarPacket(final @NotNull UUID bossbarId, final @NotNull Action action) {

        Preconditions.requireNotNull(bossbarId, "bossbarId");
        Preconditions.requireNotNull(action, "action");

        this.bossbarId = bossbarId;
        this.action = action;
    }

    @Contract(mutates = "param")
    public BossBarPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.bossbarId = inputStream.readUuid();

        final ActionType actionType = inputStream.readVarIntEnum(ActionType.class);

        this.action = actionType.create(inputStream);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeUuid(bossbarId)
                .writeVarIntEnum(action.getType())
                .writeWritable(action);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.BOSS_BAR;
    }

     public interface Action extends Writable {

        @Contract(pure = true)
        @NotNull ActionType getType();
    }

    public static final class AddAction implements Action {

        private final static byte DARKEN_SKY_BIT = 0b00000001;
        private final static byte IS_DRAGON_BAR_BIT= 0x00000002;
        private final static byte CREATE_FOG = 0b00000100;

        private final Component title;
        private final float health;
        private final Color color;
        private final Division division;
        private final byte flags;

        @Contract(pure = true)
        public AddAction(final @NotNull Component title,
                         final float health,
                         final @NotNull Color color,
                         final @NotNull Division division,
                         final byte flags) {

            Preconditions.requireNotNull(title, "title");
            Preconditions.requireNotNull(color, "color");
            Preconditions.requireNotNull(division, "division");

            this.title = title;
            this.health = health;
            this.color = color;
            this.division = division;
            this.flags = flags;
        }

        @Contract(mutates = "param")
        public AddAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.title = inputStream.readChat();
            this.health = inputStream.readFloat();

            if (health < 0) {
                throw new DecodeException("Health cannot be negative");
            }

            this.color = inputStream.readVarIntEnum(Color.class);
            this.division = inputStream.readVarIntEnum(Division.class);
            this.flags = inputStream.readByte();
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeChat(title)
                    .writeFloat(health)
                    .writeVarIntEnum(color)
                    .writeVarIntEnum(division)
                    .writeByte(flags);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.ADD;
        }

        @Contract(pure = true)
        public @NotNull Component getTitle() {

            return title;
        }

        @Contract(pure = true)
        public float getHealth() {

            return health;
        }

        @Contract(pure = true)
        public @NotNull Color getColor() {

            return color;
        }

        @Contract(pure = true)
        public @NotNull Division getDivision() {

            return division;
        }

        @Contract(pure = true)
        public byte getFlags() {

            return flags;
        }

        @Contract(pure = true)
        public boolean isDarkSky() {

            return (flags & DARKEN_SKY_BIT) != 0;
        }

        @Contract(pure = true)
        public boolean isDragonBar() {

            return (flags & IS_DRAGON_BAR_BIT) != 0;
        }

        @Contract(pure = true)
        public boolean isCreateFog() {

            return (flags & CREATE_FOG) != 0;
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

    public static final class UpdateHealthAction implements Action {

        private final float health;

        @Contract(pure = true)
        public UpdateHealthAction(final float health) {

            Preconditions.requireTrue(health >= 0, "[health] must cannot be negative");

            this.health = health;
        }

        @Contract(mutates = "param")
        public UpdateHealthAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.health = inputStream.readFloat();
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeFloat(health);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.UPDATE_HEALTH;
        }

        @Contract(pure = true)
        public float getHealth() {

            return health;
        }
    }

    public static final class UpdateTitleAction implements Action {

        private final Component title;

        @Contract(pure = true)
        public UpdateTitleAction(final @NotNull Component title) {

            Preconditions.requireNotNull(title, "title");

            this.title = title;
        }

        @Contract(mutates = "param")
        public UpdateTitleAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.title = inputStream.readChat();
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeChat(title);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.UPDATE_TITLE;
        }

        @Contract(pure = true)
        public @NotNull Component getTitle() {

            return title;
        }
    }

    public static final class UpdateStyleAction implements Action {

        private final Color color;
        private final Division division;

        @Contract(pure = true)
        public UpdateStyleAction(final @NotNull Color color, final @NotNull Division division) {

            Preconditions.requireNotNull(color, "color");
            Preconditions.requireNotNull(division, "division");

            this.color = color;
            this.division = division;
        }

        @Contract(mutates = "param")
        public UpdateStyleAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.color = inputStream.readVarIntEnum(Color.class);
            this.division = inputStream.readVarIntEnum(Division.class);
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeVarIntEnum(color)
                    .writeVarIntEnum(division);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.UPDATE_STYLE;
        }

        @Contract(pure = true)
        public @NotNull Color getColor() {

            return color;
        }

        @Contract(pure = true)
        public @NotNull Division getDivision() {

            return division;
        }
    }

    public static final class UpdateFlagsAction implements Action {

        private final byte flags;

        @Contract(pure = true)
        public UpdateFlagsAction(final byte flags) {

            this.flags = flags;
        }

        @Contract(mutates = "param")
        public UpdateFlagsAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.flags = inputStream.readByte();
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeByte(flags);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.UPDATE_FLAGS;
        }

        @Contract(pure = true)
        public byte getFlags() {

            return flags;
        }

        @Contract(pure = true)
        public boolean isDarkSky() {

            return (flags & AddAction.DARKEN_SKY_BIT) != 0;
        }

        @Contract(pure = true)
        public boolean isDragonBar() {

            return (flags & AddAction.IS_DRAGON_BAR_BIT) != 0;
        }

        @Contract(pure = true)
        public boolean isCreateFog() {

            return (flags & AddAction.CREATE_FOG) != 0;
        }
    }

    public enum ActionType {

        ADD(AddAction::new),
        REMOVE(RemoveAction::new),
        UPDATE_HEALTH(UpdateHealthAction::new),
        UPDATE_TITLE(UpdateTitleAction::new),
        UPDATE_STYLE(UpdateStyleAction::new),
        UPDATE_FLAGS(UpdateFlagsAction::new);

        private final Function<MinecraftInputStream, Action> factory;

        @Contract(pure = true)
        ActionType(final @NotNull Function<MinecraftInputStream, Action> factory) {

            this.factory = factory;
        }

        @Contract(pure = true)
        public @NotNull Action create(final @NotNull MinecraftInputStream inputStream) {

            return factory.apply(inputStream);
        }
    }

    public enum Color {

        PINK,
        BLUE,
        RED,
        GREEN,
        YELLOW,
        PURPLE,
        WHITE
    }

    public enum Division {

        NONE,
        SIX,
        TEN,
        TWELVE,
        TWENTY
    }
}
