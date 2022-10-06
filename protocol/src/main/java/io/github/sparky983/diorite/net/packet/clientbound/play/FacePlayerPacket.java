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

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Position;

public final class FacePlayerPacket implements ClientBoundPacket {

    private final BodyPart bodyPart;
    private final Position targetPosition;
    private final @Nullable TargetInfo targetInfo;

    @Contract(pure = true)
    public FacePlayerPacket(final @NotNull BodyPart bodyPart,
            final @NotNull Position targetPosition,
            final @Nullable TargetInfo targetInfo) {

        Preconditions.requireNotNull(bodyPart, "bodyPart");
        Preconditions.requireNotNull(targetPosition, "targetPosition");
        Preconditions.requireNotNull(targetInfo, "targetInfo");

        this.bodyPart = bodyPart;
        this.targetPosition = targetPosition;
        this.targetInfo = targetInfo;
    }

    @Contract(mutates = "param")
    public FacePlayerPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.bodyPart = inputStream.readVarIntEnum(BodyPart.class);
        this.targetPosition = inputStream.readPosition();
        this.targetInfo = inputStream.readOptional(TargetInfo::new).orElse(null);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(bodyPart.ordinal())
                .writePosition(targetPosition)
                .writeNullable(targetInfo, StreamOut::writeWritable);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.FACE_PLAYER;
    }

    @Contract(pure = true)
    public @NotNull BodyPart getBodyPart() {

        return bodyPart;
    }

    @Contract(pure = true)
    public @NotNull Position getTargetPosition() {

        return targetPosition;
    }

    @Contract(pure = true)
    public @Nullable TargetInfo getTargetInfo() {

        return targetInfo;
    }

    public enum BodyPart {

        FEET,
        EYES
    }

    public static final class TargetInfo implements Writable {

        private final int entityId;
        private final BodyPart bodyPart;

        @Contract(pure = true)
        public TargetInfo(final int entityId, final @NotNull BodyPart bodyPart) {

            Preconditions.requireNotNull(bodyPart, "bodyPart");

            this.entityId = entityId;
            this.bodyPart = bodyPart;
        }

        @Contract(mutates = "param")
        public TargetInfo(final @NotNull StreamIn inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.entityId = inputStream.readVarInt();
            this.bodyPart = inputStream.readVarIntEnum(BodyPart.class);
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeVarInt(entityId)
                    .writeVarIntEnum(bodyPart);
        }

        @Contract(pure = true)
        public int getEntityId() {

            return entityId;
        }

        @Contract(pure = true)
        public @NotNull BodyPart getBodyPart() {

            return bodyPart;
        }
    }
}
