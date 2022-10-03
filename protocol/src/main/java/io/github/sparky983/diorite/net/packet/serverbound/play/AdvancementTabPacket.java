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
import org.jetbrains.annotations.UnknownNullability;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;
import io.github.sparky983.diorite.world.Identifier;

public final class AdvancementTabPacket implements ServerBoundPacket {

    private final Action action;
    private final @UnknownNullability Identifier tab;

    @Contract(pure = true)
    public AdvancementTabPacket(final @NotNull Action action,
                                final @Nullable Identifier tab) {

        Preconditions.requireNotNull(action, "action");

        if (action == Action.OPENED_TAB) {
            Preconditions.requireNotNull(tab, "tab");
        } else {
            Preconditions.requireTrue(tab == null, "[tab] must be null if [action] is not OPENED_TAB");
        }

        this.action = action;
        this.tab = tab;
    }

    @Contract(mutates = "param")
    public AdvancementTabPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.action = inputStream.readVarIntEnum(Action.class);

        if (action == Action.OPENED_TAB) {
            this.tab = inputStream.readIdentifier();
        } else {
            this.tab = null;
        }
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarIntEnum(action);

        if (action == Action.OPENED_TAB) {
            outputStream.writeIdentifier(tab);
        }
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Play.ADVANCEMENT_TAB;
    }

    @Contract(pure = true)
    public @NotNull Action getAction() {

        return action;
    }

    @Contract(pure = true)
    public @UnknownNullability Identifier getTab() {

        return tab;
    }

    public enum Action {

        OPENED_TAB,
        CLOSED_SCREEN
    }
}
