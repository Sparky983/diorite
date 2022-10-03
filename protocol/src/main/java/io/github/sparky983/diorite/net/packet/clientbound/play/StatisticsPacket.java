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

import java.util.List;

import io.github.sparky983.diorite.io.StreamIn;
import io.github.sparky983.diorite.io.StreamOut;
import io.github.sparky983.diorite.io.Writable;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.util.Preconditions;

public final class StatisticsPacket implements ClientBoundPacket {

    private final List<Statistic> statistics;

    @Contract(pure = true)
    public StatisticsPacket(final @NotNull List<@NotNull Statistic> statistics) {

        Preconditions.requireContainsNoNulls(statistics, "statistics");

        this.statistics = List.copyOf(statistics);
    }

    @Contract(mutates = "param")
    public StatisticsPacket(final @NotNull StreamIn inputStream) {

        Preconditions.requireNotNull(inputStream, "inputStream");

        this.statistics = inputStream.readList(Statistic::new);
    }

    @Override
    public void write(final @NotNull StreamOut outputStream) {

        Preconditions.requireNotNull(outputStream, "outputStream");

        outputStream.writeList(statistics, StreamOut::writeWritable);
    }

    @Override
    public int getId() {

        return ClientBoundPacketId.Play.STATISTICS;
    }

    @Contract(pure = true)
    public @NotNull List<@NotNull Statistic> getStatistics() {

        return statistics;
    }

    public static final class Statistic implements Writable {

        private final int categoryId;
        private final int statisticId;
        private final int value;

        @Contract(pure = true)
        public Statistic(final int categoryId, final int statisticId, final int value) {

            this.categoryId = categoryId;
            this.statisticId = statisticId;
            this.value = value;
        }

        @Contract(mutates = "param")
        public Statistic(final @NotNull StreamIn inputStream) {

            Preconditions.requireNotNull(inputStream, "inputStream");

            this.categoryId = inputStream.readVarInt();
            this.statisticId = inputStream.readVarInt();
            this.value = inputStream.readVarInt();
        }

        @Override
        public void write(final @NotNull StreamOut outputStream) {

            Preconditions.requireNotNull(outputStream, "outputStream");

            outputStream.writeVarInt(categoryId)
                    .writeVarInt(statisticId)
                    .writeVarInt(value);
        }
    }
}
