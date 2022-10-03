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

package io.github.sparky983.diorite.net.packet.clientbound.login;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class EncryptionRequestPacket implements ClientBoundPacket {

    private static final int MAX_SERVER_ID_LENGTH = 20;

    private final String serverId;
    private final byte[] publicKey;
    private final byte[] verifyToken;

    @Contract(pure = true)
    public EncryptionRequestPacket(final @NotNull String serverId,
                             final byte @NotNull [] publicKey,
                             final byte @NotNull [] verifyToken) {

        Preconditions.requireNotNull(serverId, "serverId");
        Preconditions.requireRange(serverId.length(), 0, MAX_SERVER_ID_LENGTH, "serverId.length()");
        Preconditions.requireNotNull(publicKey, "publicKey");
        Preconditions.requireNotNull(verifyToken, "verifyToken");

        this.serverId = serverId;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }

    @Contract(mutates = "param")
    public EncryptionRequestPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.serverId = inputStream.readString(MAX_SERVER_ID_LENGTH);
        this.publicKey = inputStream.readByteList();
        this.verifyToken = inputStream.readByteList();
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeString(serverId)
                .writeByteList(publicKey)
                .writeByteList(verifyToken);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Login.ENCRYPTION_REQUEST;
    }

    @Contract(pure = true)
    public @NotNull String getServerId() {

        return serverId;
    }

    @Contract(pure = true)
    public byte @NotNull [] getPublicKey() {

        // TODO(Sparky983: think about returning cloned copies of public key and verify token
        return publicKey;
    }

    @Contract(pure = true)
    public byte @NotNull [] getVerifyToken() {

        return verifyToken;
    }
}
