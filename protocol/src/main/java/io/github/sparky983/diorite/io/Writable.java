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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Something that can write to a {@link MinecraftOutputStream}.
 *
 * @author Sparky983
 * @since 1.0.0
 */
public interface Writable {

    /**
     * Writes to the outputStream.
     *
     * @param outputStream The output stream to write to.
     * @since 1.0.0
     */
    @Contract(mutates = "param")
    void write(@NotNull MinecraftOutputStream outputStream);
}
