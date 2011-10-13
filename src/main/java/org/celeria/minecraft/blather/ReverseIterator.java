/*
 * Copyright 2011 Ian D. Bollinger
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

package org.celeria.minecraft.blather;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Ian D. Bollinger
 */
public class ReverseIterator<T> implements Iterator<T> {
    private ListIterator<T> listIterator;

    ReverseIterator(final ListIterator<T> iterator) {
        this.listIterator = iterator;
    }

    /**
     * @param listIterator
     * @return
     */
    public static <T> ReverseIterator<T> create(
            final ListIterator<T> listIterator) {
        Ensure.notNull(listIterator, "listIterator");
        return new ReverseIterator<T>(listIterator);
    }

    ListIterator<T> underlyingListIterator() {
        return listIterator;
    }

    @Override
    public boolean hasNext() {
        return listIterator.hasPrevious();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return listIterator.previous();
    }

    @Override
    public void remove() {
        listIterator.remove();
    }
}
