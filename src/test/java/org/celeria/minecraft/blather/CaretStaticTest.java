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

import org.celeria.minecraft.blather.Caret;
import org.celeria.minecraft.blather.TextBuffer;
import org.junit.Test;

public class CaretStaticTest {
    @Test
    public void testConstructor() {
        final TextBuffer textBuffer = TextBuffer.create();
        final int position = 0;
        final Caret caret = new Caret(textBuffer, position);
        assertEquals(textBuffer, caret.textBuffer());
        assertEquals(position, caret.position());
    }

    @Test
    public void testCreate() {
        final TextBuffer textBuffer = TextBuffer.create();
        final Caret caret = Caret.create(textBuffer);
        assertEquals(textBuffer, caret.textBuffer());
        assertEquals(0, caret.position());
    }   

    @Test(expected = NullPointerException.class)
    public void testCreateWithNull() {
        Caret.create(null);
    }   
}
