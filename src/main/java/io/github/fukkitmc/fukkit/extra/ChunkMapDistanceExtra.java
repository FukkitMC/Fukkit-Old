/*
 * Copyright 2020 ramidzkh
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

package io.github.fukkitmc.fukkit.extra;

import io.github.fukkitmc.fukkit.mixins.accessor.TicketAccessor;
import net.minecraft.server.*;

import java.util.Iterator;

public interface ChunkMapDistanceExtra {

    default <T> boolean addTicketAtLevel(TicketType<T> ticketType, ChunkCoordIntPair chunkcoordintpair, int level, T identifier) {
        throw new UnsupportedOperationException();
    }

    default <T> boolean removeTicketAtLevel(TicketType<T> ticketType, ChunkCoordIntPair chunkcoordintpair, int level, T identifier) {
        throw new UnsupportedOperationException();
    }

    default <T> void removeAllTicketsFor(TicketType<T> ticketType, int ticketLevel, T ticketIdentifier) {
        Ticket<T> target = TicketAccessor.create(ticketType, ticketLevel, ticketIdentifier);

        for (Iterator<ArraySetSorted<Ticket<?>>> iterator = ((ChunkMapDistance) this).tickets.values().iterator(); iterator.hasNext(); ) {
            ArraySetSorted<Ticket<?>> tickets = iterator.next();
            tickets.remove(target);

            if (tickets.isEmpty()) {
                iterator.remove();
            }
        }
    }
}
