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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.celeria.minecraft.blather.Caret;
import org.celeria.minecraft.blather.TextBuffer;
import org.junit.*;

public class CaretTest {
    private TextBuffer textBuffer;
    private Caret caret;

    @Before
    public void setUp() {
        textBuffer = mock(TextBuffer.class);
        caret = Caret.create(textBuffer);
    }

    /*
    @Test
    public int position() {
        position = Math.max(0, Math.min(textBuffer.length(), position));
        return position;
    }

    @Test
    public void testMoveTo(final int index) {
        caret.moveTo(0);
        position = index;
    }
    */

    @Test
    public void testMoveToPreviousCharacter() {
        final int oldPosition = caret.position();
        caret.moveToPreviousCharacter();
        assertEquals(oldPosition - 1, caret.position());
    }

    @Test
    public void testMoveToNextCharacter() {
        final int oldPosition = caret.position();
        caret.moveToNextCharacter();
        assertEquals(oldPosition + 1, caret.position());
    }

    /*
    @Test
    public void testMoveToPreviousWord() {
        caret.moveToPreviousWord();
        position = textBuffer.indexOfWordBefore(position());
    }

    @Test
    public void testMoveToNextWord() {
        caret.moveToNextWord();
        position = textBuffer.indexOfWordAfter(position());
    }
    */

    @Test
    public void testMoveToTextStart() {
        caret.moveToTextStart();
        assertEquals(0, caret.position());
    }

    @Test
    public void testMoveToTextEnd() {
        caret.moveToTextEnd();
        assertEquals(textBuffer.length(), caret.position());
    }

    /*
    @Test
    public void testAtTextStart() {
        return position == 0;
    }

    @Test
    public void testAtTextEnd() {
        return position() == textBuffer.length();
    }

    // TODO rename?!
    @Test
    public void testInsertBefore() {
        position += textBuffer.insertBefore(position(), text);
    }

    @Test
    public void insertBefore(final char character) {
        textBuffer.insert(position(), character);
        ++position;
    }

    @Test
    public void replaceBefore(final char character) {
        textBuffer.setCharacterAt(position(), character);
        ++position;
    }

    @Test
    public String getCharacterAfter() {
        // TODO: figure out what to actually return.
        return String.valueOf(atTextEnd() ? ' ' : textBuffer
                .characterAt(position()));
    }

    @Test
    public String getTextBefore() {
        return textBuffer.before(position());
    }

    @Test
    public void deleteCharacterBefore() {
        textBuffer.deleteCharacterAt(position() - 1);
        --position;
    }

    @Test
    public void testDeleteCharacterAfter() {
        textBuffer.deleteCharacterAt(position());
    }

    @Test
    public void testDeleteWordBefore() {
        textBuffer.deleteWordBefore(position());
    }

    @Test
    public void testDeleteWordAfter() {
        textBuffer.deleteWordAfter(position());
    }

    @Test
    public void testIndexOfPreviousWord() {
        return textBuffer.indexOfWordBefore(position());
    }

    @Test
    public void testIndexOfNextWord() {
        return textBuffer.indexOfWordAfter(position());
    }
    */
}
