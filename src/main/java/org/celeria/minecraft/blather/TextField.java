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
public class TextField {
    private static final boolean OVERWRITE_MODE_ON_BY_DEFAULT = false;
    private final TextBuffer textBuffer;
    private final Caret caret;
    private final Selection selection;
    private final Clipboard clipboard;
    private boolean overwriteModeOn;

    TextField(final TextBuffer messageBuffer, final Caret caret,
            final Selection selection, final Clipboard clipboard,
            final boolean overwriteModeOn) {
        this.textBuffer = messageBuffer;
        this.caret = caret;
        this.selection = selection;
        this.clipboard = clipboard;
        this.overwriteModeOn = overwriteModeOn;
    }

    public static TextField create() {
        final TextBuffer textBuffer = TextBuffer.create();
        final Caret caret = Caret.create(textBuffer);
        final Selection selection = Selection.create(textBuffer, caret);
        final Clipboard clipboard = Clipboard.create(caret, selection);
        return new TextField(textBuffer, caret, selection, clipboard,
                OVERWRITE_MODE_ON_BY_DEFAULT);
    }

    public Selection getSelection() {
        return selection;
    }

    public Clipboard getClipboard() {
        // TODO: we don't need to know anything about the clipboard anymore.
        return clipboard;
    }

    public boolean hasOverwriteModeOn() {
        return overwriteModeOn;
    }

    public String getText() {
        return textBuffer.toString();
    }

    public void setText(final String text) {
        Ensure.notNull(text, "text");
        textBuffer.setTo(text);
        caret.moveToTextEnd();
    }

    public void toggleOverwriteMode() {
        overwriteModeOn = !overwriteModeOn;
    }

    public boolean hasSelection() {
        return !selection.empty();
    }

    public String getCharacterAfterCaret() {
        return caret.getCharacterAfter();
    }

    public String getTextBeforeCaret() {
        return caret.getTextBefore();
    }

    public String getTextBeforeSelection() {
        return selection.getTextBefore();
    }

    public String getSelectedText() {
        return selection.getText();
    }

    public void expandSelectionTo(final int index) {
        Ensure.nonnegative(index, "index");
        selection.expandTo(index);
    }

    // INSERTION-RELATED METHODS ----------------------------------------------

    public void insertBeforeCaret(final char character) {
        selection.delete();
        if (overwriteModeOn && !caret.atTextEnd()) {
            caret.beforeWhichReplace(character);
        } else {
            caret.beforeWhichInsert(character);
        }
    }

    public void insertBeforeCaret(final String text) {
        Ensure.notNull(text, "text");
        selection.delete();
        caret.beforeWhichInsert(text);
    }

    // DELETION-RELATED METHODS -----------------------------------------------

    public void deleteCharacterBeforeCaret() {
        if (!selection.empty()) {
            selection.delete();
            return;
        }
        caret.deleteCharacterBefore();
    }

    public void deleteCharacterAfterCaret() {
        if (!selection.empty()) {
            selection.delete();
            return;
        }
        caret.deleteCharacterAfter();
    }

    public void deleteWordBeforeCaret() {
        if (!selection.empty()) {
            selection.delete();
            return;
        }
        caret.deleteWordBefore();
    }

    public void deleteWordAfterCaret() {
        if (!selection.empty()) {
            selection.delete();
            return;
        }
        caret.deleteWordAfter();
    }

    public void deleteAll() {
        textBuffer.clear();
    }

    // MOVEMENT-RELATED METHODS -----------------------------------------------

    public void moveCaretTo(final int index) {
        Ensure.nonnegative(index, "index");
        selection.clear();
        caret.moveTo(index);
    }

    // HELPER METHODS ---------------------------------------------------------

    public class MoveCaretToNextWord implements Command {
        @Override
        public void execute() {
            selection.clear();
            caret.moveToNextWord();
        }
    }

    public class MoveCaretToNextCharacter implements Command {
        @Override
        public void execute() {
            selection.clear();
            caret.moveToNextCharacter();
        }
    }

    public class MoveCaretToPreviousWord implements Command {
        @Override
        public void execute() {
            selection.clear();
            caret.moveToPreviousWord();
        }
    }

    public class MoveCaretToPreviousCharacter implements Command {
        @Override
        public void execute() {
            selection.clear();
            caret.moveToPreviousCharacter();
        }
    }

    public class MoveCaretToTextEnd implements Command {
        @Override
        public void execute() {
            selection.clear();
            caret.moveToTextEnd();
        }
    }

    public class MoveCaretToTextStart implements Command {
        @Override
        public void execute() {
            selection.clear();
            caret.moveToTextStart();
        }
    }

    public class DeleteWordBeforeCaret implements Command {
        @Override
        public void execute() {
            deleteWordBeforeCaret();
        }
    }

    public class DeleteCharacterBeforeCaret implements Command {
        @Override
        public void execute() {
            deleteCharacterBeforeCaret();
        }
    }

    public class DeleteWordAfterCaret implements Command {
        @Override
        public void execute() {
            deleteWordAfterCaret();
        }
    }

    public class DeleteCharacterAfterCaret implements Command {
        @Override
        public void execute() {
            deleteCharacterAfterCaret();
        }
    }

    public class ToggleOverwriteMode implements Command {
        @Override
        public void execute() {
            toggleOverwriteMode();
        }
    }
}
