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
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.ItemStack;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class ClickWindowPacket implements ServerBoundPacket {

    private final int windowId;
    private final int stateId;
    private final short slot;
    private final byte button;
    private final Mode mode;
    private final @Unmodifiable List<Slot> slots;
    private final @Nullable ItemStack clickedItem;

    @Contract(pure = true)
    public ClickWindowPacket(
            final @Range(from = 0x00, to = 0xFF) int windowId,
            final int stateId,
            final short slot,
            final byte button,
            final @NotNull Mode mode,
            final @NotNull List<Slot> slots,
            final @Nullable ItemStack clickedItem) {

        Preconditions.requireRange(windowId, 0x00, 0xFF, "windowId");
        Preconditions.requireNotNull(mode, "mode");
        Preconditions.requireContainsNoNulls(slots, "slots");
        Preconditions.requireNotNull(clickedItem, "clickedItem");

        this.windowId = windowId;
        this.stateId = stateId;
        this.slot = slot;
        this.button = button;
        this.mode = mode;
        this.slots = List.copyOf(slots);
        this.clickedItem = clickedItem;
    }

    @Contract(mutates = "param")
    public ClickWindowPacket(final @NotNull StreamIn inputStream) {

        this.windowId = inputStream.readUnsignedByte();
        this.stateId = inputStream.readVarInt();
        this.slot = inputStream.readShort();
        this.button = inputStream.readByte();
        this.mode = inputStream.readVarIntEnum(Mode.class);
        this.slots = inputStream.readList(Slot::new);
        this.clickedItem = ItemStack.read(inputStream).orElse(null);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        outputStream.writeUnsignedByte(windowId)
                .writeVarInt(stateId)
                .writeShort(slot)
                .writeByte(button)
                .writeVarIntEnum(mode)
                .writeList(
                        slots,
                        (slot) -> outputStream.writeNullable(
                                slot,
                                () -> outputStream.writeWritable(slot)
                        )
                )
                .writeNullable(clickedItem, StreamOut::writeWritable);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.CLICK_WINDOW;
    }

    @Contract(pure = true)
    public @Range(from = 0x00, to = 0xFF) int getWindowId() {

        return windowId;
    }

    @Contract(pure = true)
    public int getStateId() {

        return stateId;
    }

    @Contract(pure = true)
    public short getSlot() {

        return slot;
    }

    @Contract(pure = true)
    public byte getButton() {

        return button;
    }

    @Contract(pure = true)
    public @NotNull Mode getMode() {

        return mode;
    }

    @Contract(pure = true)
    public @Unmodifiable @NotNull List<Slot> getSlots() {

        return slots;
    }

    @Contract(pure = true)
    public @Nullable ItemStack getClickedItem() {

        return clickedItem;
    }

    /**
     * Information from <a href="https://wiki.vg/Protocol#Login_Start">wiki.bg/Protocol</a>.
     */
    public enum Mode {

        /**
         * <table>
         *     <caption>Clicks</caption>
         *     <tr>
         *         <th>Button</th>
         *         <th>Slot</th>
         *         <th>Trigger</th>
         *     </tr>
         *     <tr>
         *         <td>0</td>
         *         <td>Normal</td>
         *         <td>Left mouse click</td>
         *     </tr>
         *     <tr>
         *         <td>1</td>
         *         <td>Normal</td>
         *         <td>Right mouse click</td>
         *     </tr>
         *     <tr>
         *         <td>1</td>
         *         <td>-9999</td>
         *         <td>Left click outside inventory (drop cursor stack)</td>
         *     </tr>
         *     <tr>
         *         <td>1</td>
         *         <td>-9999</td>
         *         <td>Right click outside inventory (drop cursor single item)</td>
         *     </tr>
         * </table>
         */
        CLICK,

        /**
         * <table>
         *     <caption>Shift clicks</caption>
         *     <tr>
         *         <th>Button</th>
         *         <th>Slot</th>
         *         <th>Trigger</th>
         *     </tr>
         *     <tr>
         *         <td>0</td>
         *         <td>Normal</td>
         *         <td>Shift + left mouse click</td>
         *     </tr>
         *     <tr>
         *         <td>1</td>
         *         <td>Normal</td>
         *         <td>Shift + right mouse click <i>(identical behaviour)</i></td>
         *     </tr>
         * </table>
         */
        SHIFT_CLICK,

        /**
         * <table>
         *     <caption>Num keys</caption>
         *     <tr>
         *         <th>Button</th>
         *         <th>Slot</th>
         *         <th>Trigger</th>
         *     </tr>
         *     <tr>
         *         <td>0</td>
         *         <td>Normal</td>
         *         <td>Number key 1</td>
         *     </tr>
         *     <tr>
         *         <td>1</td>
         *         <td>Normal</td>
         *         <td>Number key 2</td>
         *     </tr>
         *     <tr>
         *         <td>2</td>
         *         <td>Normal</td>
         *         <td>Number key 3</td>
         *     </tr>
         *     <tr>
         *         <td>...</td>
         *         <td>...</td>
         *         <td>...</td>
         *     </tr>
         *     <tr>
         *         <td>8</td>
         *         <td>Normal</td>
         *         <td>Number key 9</td>
         *     </tr>
         * </table>
         */
        NUM_KEY,

        /**
         * <table>
         *     <caption>Middle clicks</caption>
         *     <tr>
         *         <th>Button</th>
         *         <th>Slot</th>
         *         <th>Trigger</th>
         *     </tr>
         *     <tr>
         *         <td>2</td>
         *         <td>Normal</td>
         *         <td>Middle click, only defined for creative players in non-player
         *         inventories</td>
         *     </tr>
         * </table>
         */
        MIDDLE_CLICK,

        /**
         * <table>
         *     <caption>Drops</caption>
         *     <tr>
         *         <th>Button</th>
         *         <th>Slot</th>
         *         <th>Trigger</th>
         *     </tr>
         *     <tr>
         *         <td>0</td>
         *         <td>Normal*</td>
         *         <td>Drop key (Q) (* Clicked item is always empty)</td>
         *     </tr>
         *     <tr>
         *         <td>1</td>
         *         <td>Normal*</td>
         *         <td>Control + Drop key (Q) (* Clicked item is always empty)</td>
         *     </tr>
         * </table>
         */
        DROP,

        /**
         * <table>
         *     <caption>Drags</caption>
         *     <tr>
         *         <th>Button</th>
         *         <th>Slot</th>
         *         <th>Trigger</th>
         *     </tr>
         *     <tr>
         *         <td>0</td>
         *         <td>-999</td>
         *         <td>Starting left mouse drag</td>
         *     </tr>
         *     <tr>
         *         <td>1</td>
         *         <td>Normal</td>
         *         <td>Add slot for left-mouse drag</td>
         *     </tr>
         *     <tr>
         *         <td>2</td>
         *         <td>-999</td>
         *         <td>Ending left-mouse drag</td>
         *     </tr>
         *     <tr>
         *         <td>4</td>
         *         <td>-999</td>
         *         <td>Starting right mouse drag</td>
         *     </tr>
         *     <tr>
         *         <td>5</td>
         *         <td>Normal</td>
         *         <td>Add slot for right-mouse drag</td>
         *     </tr>
         *     <tr>
         *         <td>6</td>
         *         <td>-999</td>
         *         <td>Ending left-mouse drag</td>
         *     </tr>
         *     <tr>
         *         <td>8</td>
         *         <td>-999</td>
         *         <td>
         *             Starting middle mouse drag, only defined for creative players in non-player
         *             inventories. (Note: the vanilla client will still incorrectly send this for
         *             non-creative players - see
         *             <a href="https://web.archive.org/web/20211011071750/https://bugs.mojang.com/browse/MC-46584">MC-46584</a>)
         *         </td>
         *     </tr>
         *     <tr>
         *         <td>9</td>
         *         <td>Normal</td>
         *         <td>
         *             Add slot for middle-mouse drag, only defined for creative players in
         *             non-player inventories. (Note: the vanilla client will still incorrectly send
         *             this for non-creative players - see
         *             <a href="https://web.archive.org/web/20211011071750/https://bugs.mojang.com/browse/MC-46584">MC-46584</a>)
         *         </td>
         *     </tr>
         *     <tr>
         *         <td>10</td>
         *         <td>-999</td>
         *         <td>
         *             Ending middle mouse drag, only defined for creative players in non-player
         *             inventories. (Note: the vanilla client will still incorrectly send this for
         *             non-creative players - see
         *             <a href="https://web.archive.org/web/20211011071750/https://bugs.mojang.com/browse/MC-46584">MC-46584</a>)
         *         </td>
         *     </tr>
         * </table>
         */
        DRAG,

        /**
         * <table>
         *     <caption>Double click</caption>
         *     <tr>
         *         <th>Button</th>
         *         <th>Slot</th>
         *         <th>Trigger</th>
         *     </tr>
         *     <tr>
         *         <td>0</td>
         *         <td>Normal</td>
         *         <td>Double click</td>
         *     </tr>
         * </table>
         */
        DOUBLE_CLICK
    }

    public static final class Slot implements Writable {

        private final short slotNumber;
        private final @Nullable ItemStack slot;

        @Contract(pure = true)
        public Slot(final short slotNumber, final @Nullable ItemStack slot) {

            this.slotNumber = slotNumber;
            this.slot = slot;
        }

        @Contract(mutates = "param")
        public Slot(final @NotNull StreamIn inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.slotNumber = inputStream.readShort();
            this.slot = ItemStack.read(inputStream).orElse(null);
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            Preconditions.requireNotNull(outputStream);

            outputStream.writeShort(slotNumber)
                    .writeNullable(slot, StreamOut::writeWritable);
        }
    }
}
