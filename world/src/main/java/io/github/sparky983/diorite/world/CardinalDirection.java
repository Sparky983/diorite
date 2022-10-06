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

package io.github.sparky983.diorite.world;

/**
 * The 4 cardinal directions.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public enum CardinalDirection {

    /*
     * Not in order of NESW.
     * Done inorder to match the ids in https://web.archive.org/web/20220303180956/https://wiki
     * .vg/Protocol#Spawn_Painting.
     */

    /**
     * South.
     *
     * @since 1.0.0
     */
    SOUTH,

    /**
     * West.
     *
     * @since 1.0.0
     */
    WEST,

    /**
     * North.
     *
     * @since 1.0.0
     */
    NORTH,

    /**
     * East.
     *
     * @since 1.0.0
     */
    EAST
}
