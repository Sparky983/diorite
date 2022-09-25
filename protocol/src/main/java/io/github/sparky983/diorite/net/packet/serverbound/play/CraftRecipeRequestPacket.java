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
import io.github.sparky983.diorite.world.Identifier;

public class CraftRecipeRequestPacket implements ServerBoundPacket {

    private final byte windowId;
    private final Identifier recipe;
    private final boolean shouldMakeAll;

    @Contract(pure = true)
    public CraftRecipeRequestPacket(final byte windowId,
                                    final @NotNull Identifier recipe,
                                    final boolean shouldMakeAll) {

        Preconditions.requireNotNull(recipe, "recipe");

        this.windowId = windowId;
        this.recipe = recipe;
        this.shouldMakeAll = shouldMakeAll;
    }

    @Contract(mutates = "param")
    public CraftRecipeRequestPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.windowId = inputStream.readByte();
        this.recipe = inputStream.readIdentifier();
        this.shouldMakeAll = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeByte(windowId)
                .writeIdentifier(recipe)
                .writeBoolean(shouldMakeAll);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.CRAFT_RECIPE_REQUEST;
    }

    @Contract(pure = true)
    public byte getWindowId() {

        return windowId;
    }

    @Contract(pure = true)
    public @NotNull Identifier getRecipe() {

        return recipe;
    }

    @Contract(pure = true)
    public boolean isShouldMakeAll() {

        return shouldMakeAll;
    }
}
