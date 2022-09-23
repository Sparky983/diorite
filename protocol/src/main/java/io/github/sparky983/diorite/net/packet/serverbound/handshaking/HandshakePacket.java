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

package io.github.sparky983.diorite.net.packet.serverbound.handshaking;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.DecodeException;
import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.ChannelState;
import io.github.sparky983.diorite.net.Networking;
import io.github.sparky983.diorite.net.annotations.Port;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class HandshakePacket implements ServerBoundPacket {

    private final int protocolVersion;
    private final String serverAddress;
    private final @Port int serverPort;
    private final ChannelState nextState;

    @Contract(pure = true)
    public HandshakePacket(final int protocolVersion,
                           final @NotNull String serverAddress,
                           final @Port int serverPort,
                           final @NotNull ChannelState nextState) {

        Preconditions.requireNotNull(serverAddress, "serverAddress");
        Preconditions.requireTrue(Networking.isValidPort(serverPort), "[serverPort] is not a valid port");
        Preconditions.requireTrue(nextState == ChannelState.STATUS || nextState == ChannelState.LOGIN, "[nextState] is not a valid state");

        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.nextState = nextState;
    }

    @Contract(mutates = "param")
    public HandshakePacket(final @NotNull MinecraftInputStream inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.protocolVersion = inputStream.readVarInt();
        this.serverAddress = inputStream.readString();
        this.serverPort = inputStream.readUShort();

        final int nextStateId = inputStream.readVarInt();

        if (nextStateId == 1) {
            nextState = ChannelState.STATUS;
        } else if (nextStateId == 2) {
            nextState = ChannelState.LOGIN;
        } else {
            throw new DecodeException("Packet contains invalid next state id");
        }
    }

    @Contract(pure = true)
    public int getProtocolVersion() {

        return protocolVersion;
    }

    @Contract(pure = true)
    public @NotNull String getServerAddress() {

        return serverAddress;
    }

    @Contract(pure = true)
    public @Port int getServerPort() {

        return serverPort;
    }

    @Contract(pure = true)
    public @NotNull ChannelState getNextState() {

        return nextState;
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeVarInt(protocolVersion)
                .writeString(serverAddress)
                .writeUShort(serverPort)
                .writeVarInt(nextState == ChannelState.STATUS ? 1 : 2);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Handshaking.HANDSHAKE;
    }
}
