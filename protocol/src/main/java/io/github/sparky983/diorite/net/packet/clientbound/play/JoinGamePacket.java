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

import net.kyori.adventure.nbt.CompoundBinaryTag;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.List;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Gamemode;
import io.github.sparky983.diorite.world.Identifier;

public class JoinGamePacket implements ClientBoundPacket {

    private final int entityId;
    private final boolean isHardcore;
    private final Gamemode gamemode;
    private final @Nullable Gamemode previousGamemode;
    private final List<Identifier> worldNames;
    private final CompoundBinaryTag dimensionCodec;
    private final CompoundBinaryTag dimension;
    private final Identifier worldName;
    private final int maxPlayers;
    private final int viewDistance;
    private final boolean reducedDebugInfo;
    private final boolean enableRespawnScreen;
    private final boolean isDebug;
    private final boolean isFlat;

    @Contract(pure = true)
    public JoinGamePacket(final int entityId,
                          final boolean isHardcore,
                          final @NotNull Gamemode gamemode,
                          final @Nullable Gamemode previousGamemode,
                          final @NotNull List<@NotNull Identifier> worldNames,
                          final @NotNull CompoundBinaryTag dimensionCodec,
                          final @NotNull CompoundBinaryTag dimension,
                          final @NotNull Identifier worldName,
                          final int maxPlayers,
                          final @Range(from = 2, to = 32) int viewDistance,
                          final boolean reducedDebugInfo,
                          final boolean enableRespawnScreen,
                          final boolean isDebug,
                          final boolean isFlat) {

        Preconditions.requireNotNull(gamemode, "gamemode");
        Preconditions.requireNotNull(worldName, "worldName");
        Preconditions.requireNotNull(dimension, "dimensionCodec");
        Preconditions.requireNotNull(dimension, "dimension");
        Preconditions.requireNotNull(worldNames, "worldNames");
        Preconditions.requireRange(viewDistance, 2, 32, "viewDistance");

        this.entityId = entityId;
        this.isHardcore = isHardcore;
        this.gamemode = gamemode;
        this.previousGamemode = previousGamemode;
        this.worldNames = worldNames;
        this.dimensionCodec = dimensionCodec;
        this.dimension = dimension;
        this.worldName = worldName;
        this.maxPlayers = maxPlayers;
        this.viewDistance = viewDistance;
        this.reducedDebugInfo = reducedDebugInfo;
        this.enableRespawnScreen = enableRespawnScreen;
        this.isDebug = isDebug;
        this.isFlat = isFlat;
    }

    @Contract(mutates = "param")
    public JoinGamePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readInt();
        this.isHardcore = inputStream.readBoolean();
        this.gamemode = inputStream.readUnsignedByteEnum(Gamemode.class);

        // TODO(Sparky983): Make a StreamIn method that does this
        // Maybe make enum classes handle ids rather than ordinal to make it clearer what is being
        // done.
        final byte previousGamemodeId = inputStream.readByte();

        if (previousGamemodeId == -1) {
            this.previousGamemode = null;
        } else {
            this.previousGamemode = Gamemode.values()[previousGamemodeId];
        }

        this.worldNames = inputStream.readList(inputStream::readIdentifier);
        this.dimensionCodec = inputStream.readCompoundTag();
        this.dimension = inputStream.readCompoundTag();
        this.worldName = inputStream.readIdentifier();
        this.maxPlayers = inputStream.readVarInt();
        this.viewDistance = Math.min(32, Math.max(2, inputStream.readVarInt())); // Clamp value to 2-32
        this.reducedDebugInfo = inputStream.readBoolean();
        this.enableRespawnScreen = inputStream.readBoolean();
        this.isDebug = inputStream.readBoolean();
        this.isFlat = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeBoolean(isHardcore)
                .writeUnsignedByteEnum(gamemode);

        // TODO(Sparky983): Make a StreamOut method that does this
        if (previousGamemode == null) {
            outputStream.writeByte((byte) -1);
        } else {
            outputStream.writeByte((byte) previousGamemode.ordinal());
        }

        outputStream.writeList(worldNames, outputStream::writeIdentifier)
                .writeCompoundTag(dimensionCodec)
                .writeCompoundTag(dimension)
                .writeIdentifier(worldName)
                .writeVarInt(maxPlayers)
                .writeVarInt(viewDistance)
                .writeBoolean(reducedDebugInfo)
                .writeBoolean(enableRespawnScreen)
                .writeBoolean(isDebug)
                .writeBoolean(isFlat);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.JOIN_GAME;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public boolean isHardcore() {

        return isHardcore;
    }

    @Contract(pure = true)
    public @NotNull Gamemode getGamemode() {

        return gamemode;
    }

    @Contract(pure = true)
    public @Nullable Gamemode getPreviousGamemode() {

        return previousGamemode;
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull Identifier> getWorldNames() {

        return worldNames;
    }

    @Contract(pure = true)
    public @NotNull CompoundBinaryTag getDimensionCodec() {

        return dimensionCodec;
    }

    @Contract(pure = true)
    public @NotNull CompoundBinaryTag getDimension() {

        return dimension;
    }

    @Contract(pure = true)
    public @NotNull Identifier getWorldName() {

        return worldName;
    }

    @Contract(pure = true)
    public int getMaxPlayers() {

        return maxPlayers;
    }

    @Contract(pure = true)
    public @Range(from = 2, to = 32) int getViewDistance() {

        return viewDistance;
    }

    @Contract(pure = true)
    public boolean isReducedDebugInfo() {

        return reducedDebugInfo;
    }

    @Contract(pure = true)
    public boolean isEnableRespawnScreen() {

        return enableRespawnScreen;
    }

    @Contract(pure = true)
    public boolean isDebug() {

        return isDebug;
    }

    @Contract(pure = true)
    public boolean isFlat() {

        return isFlat;
    }
}
