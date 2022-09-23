/*
 * Copyright 2022 Sparky983
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

package io.github.sparky983.diorite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import io.github.sparky983.diorite.world.Identifier;

class IdentifierTest {

    static List<Arguments> provideParsableIdentifiers() {

        return List.of(
                Arguments.of("minecraft:zombie", Identifier.of("minecraft", "zombie")),
                Arguments.of("diorite:player", Identifier.of("diorite", "player"))
        );
    }

    static List<String> provideUnparsableIdentifiers() {

        return List.of("diorite=:player", "diorite/:player", "diorite:player=");
    }

    static List<Arguments> provideEncodedIdentifiers() {

        return List.of(
                Arguments.of(Identifier.of("minecraft", "minecraft-zombie"), "minecraft:minecraft-zombie"),
                Arguments.of(Identifier.of("minecraft", "zombie"), "minecraft:zombie"),
                Arguments.of(Identifier.of("diorite", "diorite"), "diorite:diorite")
        );
    }

    @ParameterizedTest
    @MethodSource("provideParsableIdentifiers")
    void decode_ReturnsParsedIdentifier_WhenValid(final String input, final Identifier result) {

        final Identifier parsed = Identifier.parse(input);

        assertEquals(result, parsed);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    // checking for exception, return value is not needed
    @ParameterizedTest
    @MethodSource("provideUnparsableIdentifiers")
    void decode_Fails_WhenInvalidValid(final String input) {

        assertThrows(IllegalArgumentException.class, () -> Identifier.parse(input));
    }

    @ParameterizedTest
    @MethodSource("provideEncodedIdentifiers")
    void encode_ReturnsEncodedIdentifier(final Identifier input, final String result) {

        final String encodedIdentifier = input.toString();

        assertEquals(result, encodedIdentifier);
    }
}
