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
import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Identifier;

public final class UnlockRecipesPacket implements ClientBoundPacket {

    private final Action action;
    private final boolean craftingBookOpen;
    private final boolean craftingFilterActive;
    private final boolean smeltingBookOpen;
    private final boolean smeltingFilterActive;
    private final boolean blastFurnaceBookOpen;
    private final boolean blastFurnaceFilterActive;
    private final boolean smokerBookOpen;
    private final boolean smokerFilterActive;
    private final List<Identifier> recipes;
    private final @Nullable List<Identifier> toBeDisplayed;

    @Contract(pure = true)
    public UnlockRecipesPacket(final @NotNull Action action,
            final boolean craftingBookOpen,
            final boolean filteringCrafting,
            final boolean smeltingBookOpen,
            final boolean smeltingFilterActive,
            final boolean blastFurnaceBookOpen,
            final boolean blastFurnaceFilterActive,
            final boolean smokerBookOpen,
            final boolean smokerFilterActive,
            final @NotNull List<@NotNull Identifier> recipes,
            final @Nullable List<@NotNull Identifier> toBeDisplayed) {

        Preconditions.requireNotNull(action, "action");
        Preconditions.requireContainsNoNulls(recipes, "recipes");

        if (action == Action.INIT) {
            Preconditions.requireNotNull(toBeDisplayed, "toBeDisplayed");
        } else {
            Preconditions.requireTrue(toBeDisplayed == null,
                    "toBeDisplayed must be null when action is not INIT");
        }

        this.action = action;
        this.craftingBookOpen = craftingBookOpen;
        this.craftingFilterActive = filteringCrafting;
        this.smeltingBookOpen = smeltingBookOpen;
        this.smeltingFilterActive = smeltingFilterActive;
        this.blastFurnaceBookOpen = blastFurnaceBookOpen;
        this.blastFurnaceFilterActive = blastFurnaceFilterActive;
        this.smokerBookOpen = smokerBookOpen;
        this.smokerFilterActive = smokerFilterActive;
        this.recipes = recipes;
        this.toBeDisplayed = toBeDisplayed;
    }

    @Contract(mutates = "param")
    public UnlockRecipesPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.action = inputStream.readVarIntEnum(Action.class);
        this.craftingBookOpen = inputStream.readBoolean();
        this.craftingFilterActive = inputStream.readBoolean();
        this.smeltingBookOpen = inputStream.readBoolean();
        this.smeltingFilterActive = inputStream.readBoolean();
        this.blastFurnaceBookOpen = inputStream.readBoolean();
        this.blastFurnaceFilterActive = inputStream.readBoolean();
        this.smokerBookOpen = inputStream.readBoolean();
        this.smokerFilterActive = inputStream.readBoolean();
        this.recipes = inputStream.readList(StreamIn::readIdentifier);

        if (action == Action.INIT) {
            this.toBeDisplayed = inputStream.readList(StreamIn::readIdentifier);
        } else {
            this.toBeDisplayed = null;
        }
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarIntEnum(action)
                .writeBoolean(craftingBookOpen)
                .writeBoolean(craftingFilterActive)
                .writeBoolean(smeltingBookOpen)
                .writeBoolean(smeltingFilterActive)
                .writeBoolean(blastFurnaceBookOpen)
                .writeBoolean(blastFurnaceFilterActive)
                .writeBoolean(smokerBookOpen)
                .writeBoolean(smokerFilterActive)
                .writeList(recipes, StreamOut::writeIdentifier);

        if (action == Action.INIT) {
            assert toBeDisplayed != null : "[toBeDisplayed] was null when action is init";
            outputStream.writeList(toBeDisplayed, StreamOut::writeIdentifier);
        }
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.UNLOCK_RECIPES;
    }

    @Contract(pure = true)
    public @NotNull Action getAction() {

        return action;
    }

    @Contract(pure = true)
    public boolean isCraftingBookOpen() {

        return craftingBookOpen;
    }

    @Contract(pure = true)
    public boolean isCraftingFilterActive() {

        return craftingFilterActive;
    }

    @Contract(pure = true)
    public boolean isSmeltingBookOpen() {

        return smeltingBookOpen;
    }

    @Contract(pure = true)
    public boolean isSmeltingFilterActive() {

        return smeltingFilterActive;
    }

    @Contract(pure = true)
    public boolean isBlastFurnaceBookOpen() {

        return blastFurnaceBookOpen;
    }

    @Contract(pure = true)
    public boolean isBlastFurnaceFilterActive() {

        return blastFurnaceFilterActive;
    }

    @Contract(pure = true)
    public boolean isSmokerBookOpen() {

        return smokerBookOpen;
    }

    @Contract(pure = true)
    public boolean isSmokerFilterActive() {

        return smokerFilterActive;
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull Identifier> getRecipes() {

        return recipes;
    }

    /**
     * Returns {@code null} when {@code getAction()} is {@code Action.INIT}
     */
    @Contract(pure = true)
    public @Nullable List<@NotNull Identifier> getToBeDisplayed() {

        return toBeDisplayed;
    }

    public enum Action {

        INIT,
        ADD,
        REMOVE
    }
}
