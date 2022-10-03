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

import java.util.ArrayList;
import java.util.List;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.ItemStack;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.EquipmentSlot;

public final class EntityEquipmentPacket implements ClientBoundPacket {

    private final int entityId;
    private final List<Equipment> equipment;

    @Contract(pure = true)
    public EntityEquipmentPacket(final int entityId, final @NotNull List<@NotNull Equipment> equipment) {

        Preconditions.requireNotNull(equipment, "equipment");
        Preconditions.requireRange(equipment.size(), 1, Integer.MAX_VALUE, "equipment.size()");

        this.entityId = entityId;
        this.equipment = equipment;
    }

    @Contract(mutates = "param")
    public EntityEquipmentPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();

        final List<Equipment> equipment = new ArrayList<>();

        while (true) {
            final byte equipmentId = inputStream.readByte();

            final EquipmentSlot equipmentSlot = EquipmentSlot.values()[Math.abs(equipmentId)];

            equipment.add(new Equipment(equipmentSlot, ItemStack.readNullable(inputStream)));

            if (equipmentId > 0) {
                break;
            }
        }

        this.equipment = List.copyOf(equipment);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream);

        outputStream.writeVarInt(entityId);

        for (int i = 0; i < equipment.size(); i++) {
            final Equipment equipment = this.equipment.get(i);
            final byte equipmentId = (byte) (equipment.getEquipmentSlot().ordinal() *
                    (i == this.equipment.size() - 1 ? 1 : -1));
            outputStream.writeByte(equipmentId);
            outputStream.writeNullable(equipment.getItem(), StreamOut::writeWritable);
        }
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.ENTITY_EQUIPMENT;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull Equipment> getEquipment() {

        return equipment;
    }

    public static final class Equipment {

        private final EquipmentSlot equipmentSlot;
        private final @Nullable ItemStack item;

        @Contract(pure = true)
        public Equipment(final @NotNull EquipmentSlot equipmentSlot, final @Nullable ItemStack item) {

            Preconditions.requireNotNull(equipmentSlot, "equipmentSlot");
            Preconditions.requireNotNull(item, "item");

            this.equipmentSlot = equipmentSlot;
            this.item = item;
        }

        @Contract(pure = true)
        public @NotNull EquipmentSlot getEquipmentSlot() {

            return equipmentSlot;
        }

        @Contract(pure = true)
        public @Nullable ItemStack getItem() {

            return item;
        }
    }
}
