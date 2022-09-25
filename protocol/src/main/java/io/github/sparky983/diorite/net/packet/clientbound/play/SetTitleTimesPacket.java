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

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class SetTitleTimesPacket implements ClientBoundPacket {

    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    @Contract(pure = true)
    public SetTitleTimesPacket(final int fadeIn, final int stay, final int fadeOut) {

        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    @Contract(mutates = "param")
    public SetTitleTimesPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.fadeIn = inputStream.readInt();
        this.stay = inputStream.readInt();
        this.fadeOut = inputStream.readInt();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeInt(fadeIn)
                .writeInt(stay)
                .writeInt(fadeOut);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.SET_TITLE_TIMES;
    }

    @Contract(pure = true)
    public int getFadeIn() {

        return fadeIn;
    }

    @Contract(pure = true)
    public int getStay() {

        return stay;
    }

    @Contract(pure = true)
    public int getFadeOut() {

        return fadeOut;
    }
}
