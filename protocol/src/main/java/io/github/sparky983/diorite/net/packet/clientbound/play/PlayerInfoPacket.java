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

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.Property;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Gamemode;

public class PlayerInfoPacket implements ClientBoundPacket {

    private final ActionType actionType;
    private final List<Player> players;

    @Contract(pure = true)
    public PlayerInfoPacket(final @NotNull ActionType actionType,
                            final @NotNull List<@NotNull Player> players) {

        Preconditions.requireNotNull(actionType, "actionType");
        Preconditions.requireContainsNoNulls(players, "players");

        this.actionType = actionType;
        this.players = players;
    }

    @Contract(mutates = "param")
    public PlayerInfoPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.actionType = inputStream.readVarIntEnum(ActionType.class);
        this.players = inputStream.readList(() -> new Player(inputStream, actionType));
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarIntEnum(actionType)
                .writeList(players, outputStream::writeWritable);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.PLAYER_INFO;
    }

    public static final class Player implements Writable {

        private final UUID uuid;
        private final Action action;

        @Contract(pure = true)
        public Player(final @NotNull UUID uuid, final @NotNull Action action) {

            Preconditions.requireNotNull(uuid, "uuid");
            Preconditions.requireNotNull(action, "action");

            this.uuid = uuid;
            this.action = action;
        }

        @Contract(mutates = "param")
        public Player(final @NotNull MinecraftInputStream inputStream, final @NotNull ActionType actionType) {

            Preconditions.requireNotNull(inputStream, "inputStream");
            Preconditions.requireNotNull(actionType, "actionType");

            this.uuid = inputStream.readUuid();
            this.action = actionType.read(inputStream);
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeUuid(uuid)
                    .writeWritable(action);
        }

        @Contract(pure = true)
        public @NotNull UUID getUuid() {

            return uuid;
        }

        @Contract(pure = true)
        public @NotNull Action getAction() {

            return action;
        }
    }

    public enum ActionType {

        ADD_PLAYER(AddPlayerAction::new),
        UPDATE_GAME_MODE(UpdateGamemodeAction::new),
        UPDATE_LATENCY(UpdateLatencyAction::new),
        UPDATE_DISPLAY_NAME(UpdateDisplayNameAction::new),
        REMOVE_PLAYER(RemovePlayerAction::new);

        private final Function<MinecraftInputStream, Action> factory;

        @Contract(pure = true)
        ActionType(final @NotNull Function<@NotNull MinecraftInputStream, @NotNull Action> factory) {

            this.factory = factory;
        }

        @Contract(pure = true)
        public @NotNull Action read(final @NotNull MinecraftInputStream inputStream) {

            return factory.apply(inputStream);
        }
    }

    public interface Action extends Writable {

        @NotNull ActionType getType();
    }

    public static final class AddPlayerAction implements Action {

        private final String name;
        private final List<Property> properties;
        private final Gamemode gamemode;
        private final int ping;
        private final @Nullable Component displayName;

        @Contract(pure = true)
        public AddPlayerAction(final @NotNull String name,
                         final @NotNull List<@NotNull Property> properties,
                         final @NotNull Gamemode gamemode,
                         final int ping,
                         final @Nullable Component displayName) {

            Preconditions.requireNotNull(name, "name");
            Preconditions.requireNotNull(properties, "properties");
            Preconditions.requireNotNull(gamemode, "gamemode");

            this.name = name;
            this.properties = properties;
            this.gamemode = gamemode;
            this.ping = ping;
            this.displayName = displayName;
        }

        @Contract(mutates = "param")
        public AddPlayerAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.name = inputStream.readString();
            this.properties = inputStream.readList(Property::new);
            this.gamemode = inputStream.readVarIntEnum(Gamemode.class);
            this.ping = inputStream.readVarInt();
            this.displayName = inputStream.readOptional(MinecraftInputStream::readChat).orElse(null);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.ADD_PLAYER;
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeString(name)
                    .writeList(properties, outputStream::writeWritable)
                    .writeVarIntEnum(gamemode)
                    .writeVarInt(ping)
                    .writeNullable(displayName, MinecraftOutputStream::writeChat);
        }

        @Contract(pure = true)
        public @NotNull String getName() {

            return name;
        }

        @Contract(pure = true)
        public @NotNull List<@NotNull Property> getProperties() {

            return properties;
        }

        @Contract(pure = true)
        public @NotNull Gamemode getGamemode() {

            return gamemode;
        }

        @Contract(pure = true)
        public int getPing() {

            return ping;
        }

        @Contract(pure = true)
        public @Nullable Component getDisplayName() {

            return displayName;
        }
    }

    public static final class UpdateGamemodeAction implements Action {

        private final Gamemode gamemode;

        @Contract(pure = true)
        public UpdateGamemodeAction(final @NotNull Gamemode gamemode) {

            Preconditions.requireNotNull(gamemode, "gamemode");

            this.gamemode = gamemode;
        }

        @Contract(mutates = "param")
        public UpdateGamemodeAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.gamemode = inputStream.readVarIntEnum(Gamemode.class);
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeVarIntEnum(gamemode);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.UPDATE_GAME_MODE;
        }

        @Contract(pure = true)
        public @NotNull Gamemode getGamemode() {

            return gamemode;
        }
    }

    public static final class UpdateLatencyAction implements Action {

        private final int ping;

        @Contract(pure = true)
        public UpdateLatencyAction(final int ping) {

            this.ping = ping;
        }

        @Contract(mutates = "param")
        public UpdateLatencyAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.ping = inputStream.readVarInt();
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeVarInt(ping);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.UPDATE_LATENCY;
        }

        @Contract(pure = true)
        public int getPing() {

            return ping;
        }
    }

    public static final class UpdateDisplayNameAction implements Action {

        private final @Nullable Component displayName;

        @Contract(pure = true)
        public UpdateDisplayNameAction(final @Nullable Component displayName) {

            this.displayName = displayName;
        }

        @Contract(mutates = "param")
        public UpdateDisplayNameAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.displayName = inputStream.readOptional(MinecraftInputStream::readChat).orElse(null);
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeNullable(displayName, MinecraftOutputStream::writeChat);
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.UPDATE_DISPLAY_NAME;
        }

        @Contract(pure = true)
        public @Nullable Component getDisplayName() {

            return displayName;
        }
    }

    public static final class RemovePlayerAction implements Action {

        @Contract(pure = true)
        public RemovePlayerAction() {

        }

        @Contract(pure = true)
        public RemovePlayerAction(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");
        }

        @Override
        public @NotNull ActionType getType() {

            return ActionType.REMOVE_PLAYER;
        }
    }
}
