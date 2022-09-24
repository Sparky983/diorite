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

package io.github.sparky983.diorite.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("ConstantConditions")
class PreconditionsTest {

    @Test
    void requireTrue_Succeeds_WhenArgumentIsValid() {

        final int n = 100;

        assertDoesNotThrow(() -> Preconditions.requireTrue(n < 1000));
        assertDoesNotThrow(() -> Preconditions.requireTrue(n > 0));
        assertDoesNotThrow(() -> Preconditions.requireTrue(n < 1000, "[n] must be less than 1000"));
        assertDoesNotThrow(() -> Preconditions.requireTrue(n > 0, "[n] must be positive"));
    }

    @Test
    void requireTrue_Fails_WhenArgumentNotValid() {

        final int n = 0;

        assertThrows(IllegalArgumentException.class, () -> Preconditions.requireTrue(n < 0));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.requireTrue(n > 0));

        final Throwable negativeThrown =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> Preconditions.requireTrue(n < 0, "[n] must be negative"));
        assertEquals("[n] must be negative", negativeThrown.getMessage());

        final Throwable positiveThrown =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> Preconditions.requireTrue(n > 0, "[n] must be positive"));
        assertEquals("[n] must be positive", positiveThrown.getMessage());
    }

    static List<Arguments> provideValidRanges() {

        return List.of(
                Arguments.of(0, 0, 0),
                Arguments.of(-1, -1, 10),
                Arguments.of(10, 1, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidRanges")
    void requireRange_Succeeds_WhenNumberIsInRange(final int n, final int from, final int to) {

        assertDoesNotThrow(() -> Preconditions.requireRange(n, from, to));
        assertDoesNotThrow(() -> Preconditions.requireRange(n, from, to, "test"));
    }

    static List<Arguments> provideInvalidRanges() {

        return List.of(
                Arguments.of(-1, 0, 0),
                Arguments.of(11, -1, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRanges")
    void requireRange_Fails_WhenNumberIsNotInRange(final int n, final int from, final int to) {

        assertThrows(IllegalArgumentException.class, () -> Preconditions.requireRange(n, from, to));
        assertThrows(IllegalArgumentException.class, () -> Preconditions.requireRange(n, from, to, "test"));
    }

    @Test
    void requireNotNull_Fails_WhenArgumentIsNull() {

        assertThrows(NullPointerException.class, () -> Preconditions.requireNotNull(null));
        assertThrows(
                NullPointerException.class, () -> Preconditions.requireNotNull(null, "object"));
    }

    @Test
    void requireContainsNoNulls_Fails_WhenArgumentDoesntContainNull() {

        assertThrows(
                NullPointerException.class,
                () -> Preconditions.requireContainsNoNulls(new Object[]{null}, "parameter"));
        assertThrows(
                NullPointerException.class,
                () -> Preconditions.requireContainsNoNulls((Object[]) null, "parameter"));
        assertThrows(
                NullPointerException.class,
                () -> Preconditions.requireContainsNoNulls(new Object[]{null}));
        assertThrows(NullPointerException.class, () -> Preconditions.requireContainsNoNulls((Object[]) null));

        assertThrows(
                NullPointerException.class,
                () -> Preconditions.requireContainsNoNulls(Collections.singletonList(null), "parameter"));
        assertThrows(
                NullPointerException.class,
                () -> Preconditions.requireContainsNoNulls((Iterable<Object>) null, "parameter"));
        assertThrows(
                NullPointerException.class,
                () -> Preconditions.requireContainsNoNulls(new Object[]{null}));
        assertThrows(NullPointerException.class, () -> Preconditions.requireContainsNoNulls((Iterable<Object>) null));
    }
}
