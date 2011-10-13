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

/**
 * @author Ian D. Bollinger
 */
public class TextBuffer {
    public static final int DEFAULT_CAPACITY = 100;
    private final StringBuilder stringBuilder;
    private final int capacity;

    TextBuffer(final StringBuilder stringBuilder, final int capacity) {
        this.stringBuilder = stringBuilder;
        this.capacity = capacity;
    }

    public static TextBuffer create() {
        final StringBuilder stringBuilder = new StringBuilder(DEFAULT_CAPACITY);
        return new TextBuffer(stringBuilder, DEFAULT_CAPACITY);
    }

    public int length() {
        return stringBuilder.length();
    }

    public void setTo(final String text) {
        Ensure.notNull(text, "text");
        stringBuilder.replace(0, Integer.MAX_VALUE, text);
    }

    public char characterAt(final int index) {
        if (index < 0 || index >= length()) {
            throw new IndexOutOfBoundsException();
        }
        return stringBuilder.charAt(index);
    }

    public void setCharacterAt(final int index, final char character) {
        if (index < 0 || index >= length()) {
            return;
        }
        stringBuilder.setCharAt(index, character);
    }

    public void deleteCharacterAt(final int index) {
        if (index < 0 || index >= length()) {
            return;
        }
        stringBuilder.deleteCharAt(index);
    }

    public void delete(final int start, final int stop) {
        if (start < 0 || start >= length() || start > stop) {
            return;
        }
        stringBuilder.delete(start, stop);
    }

    public void clear() {
        delete(0, Integer.MAX_VALUE);
    }

    public void insert(final int index, final char character) {
        if (index < 0 || full()) {
            return;
        }
        stringBuilder.insert(index, character);
    }

    public void insert(final int index, final String text) {
        Ensure.notNull(text, "text");
        if (index < 0 || full()) {
            return;
        }
        stringBuilder.insert(index, text);
    }

    public int remainingCapacity() {
        return DEFAULT_CAPACITY - length();
    }

    public String between(final int start, final int stop) {
        if (start < 0 || stop < 0 || start > stop) {
            throw new StringIndexOutOfBoundsException();
        }
        return stringBuilder.substring(start, stop);
    }

    public String before(final int stop) {
        return between(0, stop);
    }

    public void deleteWordBefore(final int index) {
        delete(indexOfWordBefore(index), index);
    }

    public void deleteWordAfter(final int index) {
        delete(index, indexOfWordAfter(index));
    }

    public boolean empty() {
        return length() == 0;
    }

    public boolean full() {
        return length() >= capacity;
    }

    public int indexOfWordAfter(final int index) {
        // TODO: FIX!
        // TODO: bounds checking.
        final int result = stringBuilder.indexOf(" ", index);
        return result == -1 ? stringBuilder.length() : result + 1;
    }

    public int indexOfWordBefore(final int index) {
        // TODO: FIX!
        // TODO: bounds checking.
        return index < 2 ? 0 : stringBuilder.lastIndexOf(" ", index - 2) + 1;
    }

    public int insertBefore(final int index, final String text) {
        Ensure.notNull(text, "text");
        final int allowedLength = Math.min(text.length(),
                remainingCapacity());
        if (allowedLength <= 0) {
            return 0;
        }
        insert(index, text.substring(0, allowedLength));
        return allowedLength;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
