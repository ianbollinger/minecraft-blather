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
public class Caret {
    private final TextBuffer textBuffer;
    private int position;

    Caret(final TextBuffer textBuffer, final int position) {
        this.textBuffer = textBuffer;
        this.position = position;
    }

    public static Caret create(final TextBuffer textBuffer) {
        Ensure.notNull(textBuffer, "textBuffer");
        return new Caret(textBuffer, 0);
    }

    public int position() {
        position = Math.max(0, Math.min(textBuffer.length(), position));
        return position;
    }

    public TextBuffer textBuffer() {
        return textBuffer;
    }

    public void moveTo(final int index) {
        position = index;
    }

    public void moveToPreviousCharacter() {
        --position;
    }

    public void moveToNextCharacter() {
        ++position;
    }

    public void moveToPreviousWord() {
        position = textBuffer.indexOfWordBefore(position());
    }

    public void moveToNextWord() {
        position = textBuffer.indexOfWordAfter(position());
    }

    public void moveToTextStart() {
        position = 0;
    }

    public void moveToTextEnd() {
        position = textBuffer.length();
    }

    public boolean atTextStart() {
        return position == 0;
    }

    public boolean atTextEnd() {
        return position() == textBuffer.length();
    }

    public void beforeWhichInsert(final String text) {
        Ensure.notNull(text, "text");
        position += textBuffer.insertBefore(position(), text);
    }

    public void beforeWhichInsert(final char character) {
        textBuffer.insert(position(), character);
        ++position;
    }

    public void beforeWhichReplace(final char character) {
        textBuffer.setCharacterAt(position(), character);
        ++position;
    }

    public String getCharacterAfter() {
        // TODO: figure out what to actually return.
        return String.valueOf(atTextEnd() ? ' ' : textBuffer
                .characterAt(position()));
    }

    public String getTextBefore() {
        return textBuffer.before(position());
    }

    public void deleteCharacterBefore() {
        textBuffer.deleteCharacterAt(position() - 1);
        --position;
    }

    public void deleteCharacterAfter() {
        textBuffer.deleteCharacterAt(position());
    }

    public void deleteWordBefore() {
        textBuffer.deleteWordBefore(position());
    }

    public void deleteWordAfter() {
        textBuffer.deleteWordAfter(position());
    }

    public int indexOfPreviousWord() {
        return textBuffer.indexOfWordBefore(position());
    }

    public int indexOfNextWord() {
        return textBuffer.indexOfWordAfter(position());
    }
}
