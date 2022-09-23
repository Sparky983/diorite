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

package io.github.sparky983.diorite.io;

/**
 * A utility class holding information related to var ints and longs.
 *
 * @author Sparky983
 */
final class VarInts {

    /**
     * The bit mask used to read the number in a single var int octet.
     */
    public static final int SEGMENT_BITS = 0b01111111;

    /**
     * The bit mask used to read the bit to indicate that the next byte is part of the var int.
     */
    public static final int CONTINUE_BIT = 0b10000000;
}
