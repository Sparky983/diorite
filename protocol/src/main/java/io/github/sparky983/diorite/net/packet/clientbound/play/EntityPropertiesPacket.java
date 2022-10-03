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

import java.util.List;
import java.util.UUID;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Identifier;

public final class EntityPropertiesPacket implements ClientBoundPacket {

    private final int entityId;
    private final List<Property> properties;

    @Contract(pure = true)
    public EntityPropertiesPacket(final int entityId, final @NotNull List<@NotNull Property> properties) {

        Preconditions.requireContainsNoNulls(properties, "properties");

        this.entityId = entityId;
        this.properties = List.copyOf(properties);
    }

    @Contract(mutates = "param")
    public EntityPropertiesPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.properties = inputStream.readList(Property::new);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeList(properties, StreamOut::writeWritable);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.ENTITY_PROPERTIES;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull Property> getProperties() {

        return properties;
    }

    public static final class Property implements Writable {

        private final Identifier key;
        private final double value;
        private final List<Modifier> modifiers;

        @Contract(pure = true)
        public Property(final @NotNull Identifier key,
                        final double value,
                        final @NotNull List<@NotNull Modifier> modifiers) {

            Preconditions.requireNotNull(key, "key");
            Preconditions.requireContainsNoNulls(modifiers, "modifiers");

            this.key = key;
            this.value = value;
            this.modifiers = List.copyOf(modifiers);
        }

        @Contract(mutates = "param")
        public Property(final @NotNull StreamIn inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.key = inputStream.readIdentifier();
            this.value = inputStream.readDouble();
            this.modifiers = inputStream.readList(Modifier::new);
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeIdentifier(key)
                    .writeDouble(value)
                    .writeList(modifiers, StreamOut::writeWritable);
        }

        @Contract(pure = true)
        public @NotNull Identifier getKey() {

            return key;
        }

        @Contract(pure = true)
        public double getValue() {

            return value;
        }

        @Contract(pure = true)
        public @NotNull List<@NotNull Modifier> getModifiers() {

            return modifiers;
        }

        public static final class Modifier implements Writable {

            private final UUID uuid;
            private final double amount;
            private final byte operation;

            @Contract(pure = true)
            public Modifier(final @NotNull UUID uuid, final double amount, final byte operation) {

                Preconditions.requireNotNull(uuid, "uuid");

                this.uuid = uuid;
                this.amount = amount;
                this.operation = operation;
            }

            @Contract(mutates = "param")
            public Modifier(final @NotNull StreamIn inputStream) {

                Preconditions.requireNotNull(inputStream, "inputStream");

                this.uuid = inputStream.readUuid();
                this.amount = inputStream.readDouble();
                this.operation = inputStream.readByte();
            }

            @Override
            public void write(final @NotNull StreamOut outputStream) {

                Preconditions.requireNotNull(outputStream, "outputStream");

                outputStream.writeUuid(uuid)
                        .writeDouble(amount)
                        .writeByte(operation);
            }

            @Contract(pure = true)
            public @NotNull UUID getUuid() {

                return uuid;
            }

            @Contract(pure = true)
            public double getAmount() {

                return amount;
            }

            @Contract(pure = true)
            public byte getOperation() {

                return operation;
            }
        }
    }
}
