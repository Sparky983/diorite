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

package io.github.sparky983.diorite.net.packet.serverbound.login;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import io.github.sparky983.diorite.io.MinecraftInputStream;
import io.github.sparky983.diorite.io.MinecraftOutputStream;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacket;
import io.github.sparky983.diorite.net.packet.serverbound.ServerBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public class EncryptionResponsePacket implements ServerBoundPacket {

    private final byte[] sharedSecret;
    private final byte[] verifyToken;

    @Contract(pure = true)
    public EncryptionResponsePacket(final byte @NotNull [] sharedSecret, final byte @NotNull [] verifyToken) {

        Preconditions.requireNotNull(sharedSecret, "sharedSecret");
        Preconditions.requireNotNull(verifyToken, "verifyToken");

        this.sharedSecret = sharedSecret;
        this.verifyToken = verifyToken;
    }

    @Contract(mutates = "param")
    public EncryptionResponsePacket(final @NotNull MinecraftInputStream inputStream) {

        this.sharedSecret = inputStream.readLengthPrefixedBytes();
        this.verifyToken = inputStream.readLengthPrefixedBytes();
    }

    @Override
    public void write(final @NotNull MinecraftOutputStream outputStream) {

        outputStream.writeLengthPrefixedBytes(sharedSecret)
                .writeLengthPrefixedBytes(verifyToken);
    }

    @Override
    public int getId() {

        return ServerBoundPacketId.Login.ENCRYPTION_RESPONSE;
    }
}
