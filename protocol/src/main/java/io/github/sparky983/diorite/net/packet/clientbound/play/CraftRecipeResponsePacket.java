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
import io.github.sparky983.diorite.world.Identifier;

public final class CraftRecipeResponsePacket implements ClientBoundPacket {

    private final byte windowId;
    private final Identifier recipe;

    @Contract(pure = true)
    public CraftRecipeResponsePacket(final byte windowId, final @NotNull Identifier recipe) {

        Preconditions.requireNotNull(recipe, "recipe");

        this.windowId = windowId;
        this.recipe = recipe;
    }

    @Contract(mutates = "param")
    public CraftRecipeResponsePacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.windowId = inputStream.readByte();
        this.recipe = inputStream.readIdentifier();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeByte(windowId)
                .writeIdentifier(recipe);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.CRAFT_RECIPE_RESPONSE;
    }

    @Contract(pure = true)
    public byte getWindowId() {

        return windowId;
    }

    @Contract(pure = true)
    public @NotNull Identifier getRecipe() {

        return recipe;
    }
}
