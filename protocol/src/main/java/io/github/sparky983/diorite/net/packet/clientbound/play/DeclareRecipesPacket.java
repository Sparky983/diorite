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

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Identifier;

public class DeclareRecipesPacket implements ClientBoundPacket {

    private final List<Recipe> recipes;

    @Contract(pure = true)
    public DeclareRecipesPacket(final @NotNull List<@NotNull Recipe> recipes) {

        Preconditions.requireContainsNoNulls(recipes, "recipes");

        this.recipes = List.copyOf(recipes);
    }

    @Contract(mutates = "param")
    public DeclareRecipesPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.recipes = inputStream.readList(Recipe::new);
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeList(recipes, MinecraftOutputStream::writeWritable);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.DECLARE_RECIPES;
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull Recipe> getRecipes() {

        return recipes;
    }

    public static final class Recipe implements Writable {

        private final Identifier type;
        private final Identifier id;
        private final byte[] data; // TODO(Sparky983): parse actual data

        @ApiStatus.Experimental
        @Contract(pure = true)
        public Recipe(final @NotNull Identifier type,
                      final @NotNull Identifier id,
                      final byte @NotNull [] data) {

            Preconditions.requireNotNull(type, "type");
            Preconditions.requireNotNull(id, "id");
            Preconditions.requireNotNull(data, "data");

            this.type = type;
            this.id = id;
            this.data = data;
        }

        @Contract(mutates = "param")
        public Recipe(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.type = inputStream.readIdentifier();
            this.id = inputStream.readIdentifier();
            this.data = inputStream.readAllBytes();
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeIdentifier(type)
                    .writeIdentifier(id)
                    .writeBytes(data);
        }

        @Contract(pure = true)
        public @NotNull Identifier getType() {

            return type;
        }

        @Contract(pure = true)
        public @NotNull Identifier getId() {

            return id;
        }

        @ApiStatus.Experimental
        @Contract(pure = true)
        public byte @NotNull [] getData() {

            return data;
        }
    }
}
