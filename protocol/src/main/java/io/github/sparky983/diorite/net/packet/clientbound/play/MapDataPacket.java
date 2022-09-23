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

import net.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class MapDataPacket implements ClientBoundPacket {

    private final int mapId;
    private final byte scale;
    private final boolean locked;
    private final @Nullable List<@NotNull Icon> icons;
    private final @Nullable MapPatch patch;

    @Contract(pure = true)
    public MapDataPacket(final int mapId,
                         final byte scale,
                         final boolean locked,
                         final @Nullable List<@NotNull Icon> icons,
                         final @Nullable MapPatch patch) {

        if (icons != null) {
            Preconditions.requireContainsNoNulls(icons, "icons");
        }

        this.mapId = mapId;
        this.scale = scale;
        this.locked = locked;
        this.icons = icons;
        this.patch = patch;
    }

    @Contract(mutates = "param")
    public MapDataPacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.mapId = inputStream.readVarInt();
        this.scale = inputStream.readByte();
        this.locked = inputStream.readBoolean();
        this.icons = inputStream.readList(Icon::new);

        final int columns = inputStream.readUByte();

        if (columns > 0) {
            final byte rows = inputStream.readByte();
            final byte x = inputStream.readByte();
            final byte z = inputStream.readByte();
            final byte[] data = inputStream.readLengthPrefixedBytes();
            this.patch = new MapPatch(columns, rows, x, z, data);
        } else {
            this.patch = null;
        }
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(mapId)
                .writeByte(scale)
                .writeBoolean(locked)
                .writeNullable(
                        icons,
                        (icons) -> outputStream.writeList(icons, outputStream::writeWritable)
                );

        if (patch != null) {
            outputStream.writeWritable(patch);
        } else {
            outputStream.writeUByte(0);
        }
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.MAP_DATA;
    }

    @Contract(pure = true)
    public int getMapId() {

        return mapId;
    }

    @Contract(pure = true)
    public byte getScale() {

        return scale;
    }

    @Contract(pure = true)
    public boolean isLocked() {

        return locked;
    }

    @Contract(pure = true)
    public @Nullable List<@NotNull Icon> getIcons() {

        return icons;
    }

    @Contract(pure = true)
    public @Nullable MapPatch getPatch() {

        return patch;
    }

    public static final class Icon implements Writable {

        private final Type type;
        private final byte x;
        private final byte z;
        private final byte direction;
        private final @Nullable Component displayName;

        @Contract(pure = true)
        public Icon(final @NotNull Type type,
                    final byte x,
                    final byte z,
                    final byte direction,
                    final @Nullable Component displayName) {

            Preconditions.requireNotNull(type, "type");

            this.type = type;
            this.x = x;
            this.z = z;
            this.direction = direction;
            this.displayName = displayName;
        }

        @Contract(mutates = "param")
        public Icon(final @NotNull MinecraftInputStream inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.type = inputStream.readVarIntEnum(Type.class);
            this.x = inputStream.readByte();
            this.z = inputStream.readByte();
            this.direction = inputStream.readByte();
            this.displayName = inputStream.readOptional(MinecraftInputStream::readChat).orElse(null);
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeVarInt(type.ordinal())
                    .writeByte(x)
                    .writeByte(z)
                    .writeByte(direction)
                    .writeNullable(displayName, MinecraftOutputStream::writeChat);
        }

        @Contract(pure = true)
        public @NotNull Type getType() {

            return type;
        }

        @Contract(pure = true)
        public byte getX() {

            return x;
        }

        @Contract(pure = true)
        public byte getZ() {

            return z;
        }

        @Contract(pure = true)
        public byte getDirection() {

            return direction;
        }

        @Contract(pure = true)
        public @Nullable Component getDisplayName() {

            return displayName;
        }

        public enum Type {

            WHITE_ARROW,
            GREEN_ARROW,
            RED_ARROW,
            BLUE_ARROW,
            WHITE_CROSS,
            RED_POINTER,
            WHITE_CIRCLE,
            SMALL_WHITE_CIRCLE,
            MANSION,
            TEMPLATE,
            WHITE_BANNER,
            ORANGE_BANNER,
            MAGENTA_BANNER,
            LIGHT_BLUE_BANNER,
            YELLOW_BANNER,
            LIME_BANNER,
            PINK_BANNER,
            GRAY_BANNER,
            LIGHT_GRAY_BANNER,
            CYAN_BANNER,
            PURPLE_BANNER,
            BLUE_BANNER,
            BROWN_BANNER,
            GREEN_BANNER,
            RED_BANNER,
            BLACK_BANNER,
            TREASURE_MARKER
        }
    }

    @ApiStatus.Experimental
    public static final class MapPatch implements Writable {

        private final int columns;
        private final byte rows;
        private final byte x;
        private final byte z;
        private final byte[] colors; // TODO(Sparky983): Create better representation of the colors

        @Contract(pure = true)
        public MapPatch(final int columns,
                        final byte rows,
                        final byte x,
                        final byte z,
                        final byte @NotNull [] colors) {

            Preconditions.requireNotNull(colors, "colors");

            this.columns = columns;
            this.rows = rows;
            this.x = x;
            this.z = z;
            this.colors = colors;
        }

        @Override
        public void write(final @NotNull MinecraftOutputStream outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeVarInt(columns)
                    .writeByte(rows)
                    .writeByte(x)
                    .writeByte(z)
                    .writeBytes(colors);
        }

        @Contract(pure = true)
        public int getColumns() {

            return columns;
        }

        @Contract(pure = true)
        public byte getRows() {

            return rows;
        }

        @Contract(pure = true)
        public byte getX() {

            return x;
        }

        @Contract(pure = true)
        public byte getZ() {

            return z;
        }

        @Contract(pure = true)
        public byte @NotNull [] getColors() {

            return colors;
        }
    }
}
