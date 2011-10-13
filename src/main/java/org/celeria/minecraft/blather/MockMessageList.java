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

import java.util.AbstractList;

/**
 * @author Ian D. Bollinger
 */
public class MockMessageList<T> extends AbstractList<T> {
    private final ChatWindow chatWindow;

    MockMessageList(final ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }

    public static <T> MockMessageList<T> create(final ChatWindow chatWindow) {
        Ensure.notNull(chatWindow, "chatWindow");
        return new MockMessageList<T>(chatWindow);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T get(@SuppressWarnings("unused") final int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public T remove(@SuppressWarnings("unused") final int index) {
        throw new IndexOutOfBoundsException();
    }

    @SuppressWarnings("unused")
    @Override
    public void add(final int index, final T e) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public void clear() {
        chatWindow.clearMessages();
    }
}
