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
import org.jetbrains.annotations.Nullable;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class EntityMetadataPacket implements ClientBoundPacket {

    private final int entityId;
    private final EntityMetadata metadata;

    @Contract(pure = true)
    public EntityMetadataPacket(final int entityId, final @NotNull EntityMetadata metadata) {

        Preconditions.requireNotNull(metadata, "metadata");

        this.entityId = entityId;
        this.metadata = metadata;
    }

    @Contract(mutates = "param")
    public EntityMetadataPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.entityId = inputStream.readVarInt();
        this.metadata = new EntityMetadata(inputStream);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(entityId)
                .writeWritable(metadata);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.ENTITY_METADATA;
    }

    @Contract(pure = true)
    public int getEntityId() {

        return entityId;
    }

    @Contract(pure = true)
    public @NotNull EntityMetadata getMetadata() {

        return metadata;
    }

    public static final class EntityMetadata implements Writable {

        private final int index;
        private final @Nullable Value value;

        @Contract(pure = true)
        public EntityMetadata(final int index, final @Nullable Value value) {

            if (index != 0xFF) {
                Preconditions.requireNotNull(value, "value");
            }

            this.index = index;
            this.value = value;
        }

        @Contract(mutates = "param")
        public EntityMetadata(final @NotNull StreamIn inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.index = inputStream.readUnsignedByte();

            if (index == 0xFF) {
                this.value = null;
            } else {
                this.value = new Value(inputStream);
            }
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeUnsignedByte(index);

            if (index != 0xFF) {
                assert value != null : "value shouldn't be null when index != 0xFF";
                outputStream.writeWritable(value);
            }
        }

        @Contract(pure = true)
        public int getIndex() {

            return index;
        }

        @Contract(pure = true)
        public @NotNull Value getValue() {

            return value;
        }

        public static final class Value implements Writable {

            private final Type type;
            private final byte[] value; // TODO(Sparky983): parse the actual value with the correct type

            @ApiStatus.Experimental
            @Contract(pure = true)
            public Value(final @NotNull Type type, final byte @NotNull [] value) {

                Preconditions.requireNotNull(type, "type");
                Preconditions.requireNotNull(value, "value");

                this.type = type;
                this.value = value;
            }

            @Contract(mutates = "param")
            public Value(final @NotNull StreamIn inputStream) {

                Preconditions.requireNotNull(inputStream, "inputStream");

                this.type = inputStream.readVarIntEnum(Type.class);
                this.value = inputStream.readAllBytes();
            }

            @Override
            public void write(final @NotNull StreamOut outputStream) {

                Preconditions.requireNotNull(outputStream, "outputStream");

                outputStream.writeVarIntEnum(type)
                        .writeBytes(value);
            }

            @Contract(pure = true)
            public @NotNull Type getType() {

                return type;
            }

            @Contract(pure = true)
            public byte @NotNull [] getValue() {

                return value;
            }

            public enum Type {

                BYTE,
                VAR_INT,
                FLOAT,
                STRING,
                CHAT,
                OPTIONAL_CHAT,
                SLOT,
                BOOLEAN,
                ROTATION,
                POSITION,
                OPTIONAL_POSITION,
                BLOCK_FACE,
                OPTIONAL_UUID,
                OPTIONAL_BLOCK_ID,
                NBT,
                PARTICLE,
                VILLAGER_DATA,
                OPTIONAL_VAR_INT,
                POSE
            }
        }
    }
}
