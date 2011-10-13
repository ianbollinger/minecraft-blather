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
public class Selection {
    private final TextBuffer textBuffer;
    private final Caret caret;
    private int length;

    Selection(final TextBuffer textBuffer, final Caret caret,
            final int length) {
        this.textBuffer = textBuffer;
        this.caret = caret;
        this.length = length;
    }

    /**
     * @param textBuffer
     * @param caret
     * @return
     */
    public static Selection create(final TextBuffer textBuffer,
            final Caret caret) {
        Ensure.notNull(textBuffer, "textBuffer");
        Ensure.notNull(caret, "caret");
        return new Selection(textBuffer, caret, 0);
    }

    /**
     *
     */
    public void clear() {
        length = 0;
    }

    /**
     *
     */
    public void delete() {
        if (empty()) {
            return;
        }
        final int selectionStart = start();
        textBuffer.delete(selectionStart, end());
        caret.moveTo(selectionStart);
        clear();
    }

    /**
     * @return
     */
    public boolean empty() {
        return length == 0;
    }

    /**
     * @return
     */
    public int end() {
        final int caretPosition = caret.position();
        return Math.max(caretPosition + length(), caretPosition);
    }

    /**
     * @param index
     */
    public void expandTo(final int index) {
        length = index - caret.position();
    }

    /**
     *
     */
    public void expandToAll() {
        caret.moveToTextStart();
        length = textBuffer.length();
    }

    /**
     *
     */
    public void expandToNextCharacter() {
        ++length;
    }

    /**
     *
     */
    public void expandToNextWord() {
        expandTo(caret.indexOfNextWord());
    }

    /**
     *
     */
    public void expandToPreviousCharacter() {
        --length;
    }

    /**
     *
     */
    public void expandToPreviousWord() {
        expandTo(caret.indexOfPreviousWord());
    }

    /**
     *
     */
    public void expandToTextEnd() {
        expandTo(textBuffer.length());
    }

    /**
     *
     */
    public void expandToTextStart() {
        expandTo(0);
    }

    /**
     * @return
     */
    public String getText() {
        return textBuffer.between(start(), end());
    }

    /**
     * @return
     */
    public String getTextBefore() {
        return textBuffer.before(start());
    }

    /**
     * @return
     */
    public int length() {
        length = Math.max(-caret.position(),
                Math.min(textBuffer.length() - caret.position(), length));
        return length;
    }

    /**
     * @return
     */
    public int start() {
        final int caretPosition = caret.position();
        return Math.min(caretPosition + length(), caretPosition);
    }

    /**
     *
     */
    public class ExpandToAll implements Command {
        @Override
        public void execute() {
            expandToAll();
        }
    }

    /**
     *
     */
    public class ExpandToNextCharacter implements Command {
        @Override
        public void execute() {
            expandToNextCharacter();
        }
    }

    /**
     *
     */
    public class ExpandToNextWord implements Command {
        @Override
        public void execute() {
            expandToNextWord();
        }
    }

    /**
     *
     */
    public class ExpandToPreviousCharacter implements Command {
        @Override
        public void execute() {
            expandToPreviousCharacter();
        }
    }

    /**
     *
     */
    public class ExpandToPreviousWord implements Command {
        @Override
        public void execute() {
            expandToPreviousWord();
        }
    }

    /**
     *
     */
    public class ExpandToTextEnd implements Command {
        @Override
        public void execute() {
            expandToTextEnd();
        }
    }

    /**
     *
     */
    public class ExpandToTextStart implements Command {
        @Override
        public void execute() {
            expandToTextStart();
        }
    }
}
