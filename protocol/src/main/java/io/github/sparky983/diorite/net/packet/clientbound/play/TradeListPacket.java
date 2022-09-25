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
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.ItemStack;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class TradeListPacket implements ClientBoundPacket {

    private final int windowId;
    private final List<Trade> trades;
    private final int villagerLevel;
    private final int experience;
    private final boolean isRegularVillager;
    private final boolean canRestock;

    @Contract(pure = true)
    public TradeListPacket(final int windowId,
                           final @NotNull List<@NotNull Trade> trades,
                           final int villagerLevel,
                           final int experience,
                           final boolean isRegularVillager,
                           final boolean canRestock) {

        Preconditions.requireContainsNoNulls(trades, "trades");

        this.windowId = windowId;
        this.trades = List.copyOf(trades);
        this.villagerLevel = villagerLevel;
        this.experience = experience;
        this.isRegularVillager = isRegularVillager;
        this.canRestock = canRestock;
    }

    @Contract(mutates = "param")
    public TradeListPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.windowId = inputStream.readUnsignedByte();
        this.trades = inputStream.readList(Trade::new);
        this.villagerLevel = inputStream.readVarInt();
        this.experience = inputStream.readVarInt();
        this.isRegularVillager = inputStream.readBoolean();
        this.canRestock = inputStream.readBoolean();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeUnsignedByte(windowId)
                .writeList(trades, outputStream::writeWritable)
                .writeVarInt(villagerLevel)
                .writeVarInt(experience)
                .writeBoolean(isRegularVillager)
                .writeBoolean(canRestock);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.TRADE_LIST;
    }

    @Contract(pure = true)
    public int getWindowId() {

        return windowId;
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull Trade> getTrades() {

        return trades;
    }

    @Contract(pure = true)
    public int getVillagerLevel() {

        return villagerLevel;
    }

    @Contract(pure = true)
    public int getExperience() {

        return experience;
    }

    @Contract(pure = true)
    public boolean isRegularVillager() {

        return isRegularVillager;
    }

    @Contract(pure = true)
    public boolean isCanRestock() {

        return canRestock;
    }

    public static final class Trade implements Writable {

        private final ItemStack inputItem1;
        private final ItemStack outputItem;
        private final boolean hasItem2;
        private final @Nullable ItemStack inputItem2;
        private final boolean isDisabled;
        private final int uses;
        private final int maxUses;
        private final int xp;
        private final int specialPrice;
        private final float priceMultiplier;
        private final int demand;

        @Contract(pure = true)
        public Trade(final @Nullable ItemStack inputItem1,
                     final @Nullable ItemStack outputItem,
                     final boolean hasItem2,
                     final @Nullable ItemStack inputItem2,
                     final boolean isDisabled,
                     final int uses,
                     final int maxUses,
                     final int xp,
                     final int specialPrice,
                     final float priceMultiplier,
                     final int demand) {

            this.inputItem1 = inputItem1;
            this.outputItem = outputItem;
            this.hasItem2 = hasItem2;
            this.inputItem2 = inputItem2;
            this.isDisabled = isDisabled;
            this.uses = uses;
            this.maxUses = maxUses;
            this.xp = xp;
            this.specialPrice = specialPrice;
            this.priceMultiplier = priceMultiplier;
            this.demand = demand;
        }

        @Contract(mutates = "param")
        public Trade(final @NotNull StreamIn inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.inputItem1 = ItemStack.readNullable(inputStream);
            this.outputItem = ItemStack.readNullable(inputStream);
            this.hasItem2 = inputStream.readBoolean();

            if (this.hasItem2) {
                this.inputItem2 = ItemStack.readNullable(inputStream);
            } else {
                this.inputItem2 = null;
            }

            this.isDisabled = inputStream.readBoolean();
            this.uses = inputStream.readInt();
            this.maxUses = inputStream.readInt();
            this.xp = inputStream.readInt();
            this.specialPrice = inputStream.readInt();
            this.priceMultiplier = inputStream.readFloat();
            this.demand = inputStream.readInt();
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeNullable(inputItem1, outputStream::writeWritable)
                    .writeNullable(outputItem, outputStream::writeWritable)
                    .writeBoolean(hasItem2)
                    .writeNullable(inputItem2, outputStream::writeWritable)
                    .writeBoolean(isDisabled)
                    .writeInt(uses)
                    .writeInt(maxUses)
                    .writeInt(xp)
                    .writeInt(specialPrice)
                    .writeFloat(priceMultiplier)
                    .writeInt(demand);
        }

        @Contract(pure = true)
        public @Nullable ItemStack getInputItem1() {

            return inputItem1;
        }

        @Contract(pure = true)
        public @Nullable ItemStack getOutputItem() {

            return outputItem;
        }

        @Contract(pure = true)
        public boolean isHasItem2() {

            return hasItem2;
        }

        @Contract(pure = true)
        public @Nullable ItemStack getInputItem2() {

            return inputItem2;
        }

        @Contract(pure = true)
        public boolean isDisabled() {

            return isDisabled;
        }

        @Contract(pure = true)
        public int getUses() {

            return uses;
        }

        @Contract(pure = true)
        public int getMaxUses() {

            return maxUses;
        }

        @Contract(pure = true)
        public int getXp() {

            return xp;
        }

        @Contract(pure = true)
        public int getSpecialPrice() {

            return specialPrice;
        }

        @Contract(pure = true)
        public float getPriceMultiplier() {

            return priceMultiplier;
        }

        @Contract(pure = true)
        public int getDemand() {

            return demand;
        }
    }
}
