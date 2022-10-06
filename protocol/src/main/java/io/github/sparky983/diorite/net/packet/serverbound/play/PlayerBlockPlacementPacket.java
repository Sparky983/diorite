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

import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.BlockFace;
import io.github.sparky983.diorite.world.BlockPosition;
import io.github.sparky983.diorite.world.Hand;

public final class PlayerBlockPlacementPacket implements ServerBoundPacket {

    private final Hand hand;
    private final BlockPosition location;
    private final BlockFace face;
    private final float cursorX;
    private final float cursorY;
    private final float cursorZ;
    private final boolean isInsideBlock;

    @Contract(pure = true)
    public PlayerBlockPlacementPacket(final @NotNull Hand hand,
            final @NotNull BlockPosition location,
            final @NotNull BlockFace face,
            final float cursorX,
            final float cursorY,
            final float cursorZ,
            final boolean isInsideBlock) {

        Preconditions.requireNotNull(hand, "hand");
        Preconditions.requireNotNull(location, "location");
        Preconditions.requireNotNull(face, "face");
        Preconditions.requireTrue(cursorX >= 0 && cursorX <= 1, "[cursorX] must be between 0-1");
        Preconditions.requireTrue(cursorY >= 0 && cursorY <= 1, "[cursorY] must be between 0-1");
        Preconditions.requireTrue(cursorZ >= 0 && cursorZ <= 1, "[cursorZ] must be between 0-1");

        this.hand = hand;
        this.location = location;
        this.face = face;
        this.cursorX = cursorX;
        this.cursorY = cursorY;
        this.cursorZ = cursorZ;
        this.isInsideBlock = isInsideBlock;
    }

    @Contract(mutates = "param")
    public PlayerBlockPlacementPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.hand = inputStream.readVarIntEnum(Hand.class);
        this.location = inputStream.readBlockPosition();
        this.face = inputStream.readVarIntEnum(BlockFace.class);

        this.cursorX = inputStream.readFloat();
        this.cursorY = inputStream.readFloat();
        this.cursorZ = inputStream.readFloat();

        if (cursorX < 0 || cursorX > 1 ||
                cursorY < 0 || cursorY > 1 ||
                cursorZ < 0 || cursorZ > 1) {
            throw new DecodeException("Illegal cursor position");
        }

        this.isInsideBlock = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarIntEnum(hand)
                .writeBlockPosition(location)
                .writeVarIntEnum(face)
                .writeFloat(cursorX)
                .writeFloat(cursorY)
                .writeFloat(cursorZ)
                .writeBoolean(isInsideBlock);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.PLAYER_BLOCK_PLACEMENT;
    }

    @Contract(pure = true)
    public @NotNull Hand getHand() {

        return hand;
    }

    @Contract(pure = true)
    public @NotNull BlockPosition getLocation() {

        return location;
    }

    @Contract(pure = true)
    public @NotNull BlockFace getFace() {

        return face;
    }

    @Contract(pure = true)
    public float getCursorX() {

        return cursorX;
    }

    @Contract(pure = true)
    public float getCursorY() {

        return cursorY;
    }

    @Contract(pure = true)
    public float getCursorZ() {

        return cursorZ;
    }

    @Contract(pure = true)
    public boolean isInsideBlock() {

        return isInsideBlock;
    }
}
