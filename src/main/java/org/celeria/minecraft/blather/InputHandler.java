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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Rectangle;

// TODO: pull out generic bits.
// TODO: filter out disallowed characters from pasting and so on!
// TODO: allowedCharacters would then seem to belong in textBuffer.
// TODO: the fan out complexity is too damn high! (then again, it is counting
// commands.
/**
 * @author Ian D. Bollinger
 */
public class InputHandler {
    private static final int LEFT_MOUSE_BUTTON = 0;
    private final GameClient client;
    private final TextField textField;
    private final InputWidget inputWidget;
    private final InputHistory history;
    private final ChatWindow chatWindow;
    private final String allowedCharacters;
    private final Map<KeyStroke, Command> keyBindings;
    private final Map<MouseClick, Command> clickBindings;
    private int mouseX;
    private int mouseY;

    InputHandler(final GameClient client, final TextField textField,
            final ChatWindow chatWindow, final InputHistory history,
            final InputWidget inputWidget, final String allowedCharacters,
            final Map<KeyStroke, Command> keyBindings,
            final Map<MouseClick, Command> clickBindings) {
        this.client = client;
        this.textField = textField;
        this.chatWindow = chatWindow;
        this.history = history;
        this.inputWidget = inputWidget;
        // TODO: move.
        this.allowedCharacters = allowedCharacters;
        this.keyBindings = keyBindings;
        this.clickBindings = clickBindings;
    }

    public static InputHandler create(final GameClient client,
            final TextField textField, final ChatWindow chatWindow,
            final InputHistory inputHistory, final InputWidget inputWidget,
            final String allowedCharacters) {
        return new InputHandler(client, textField, chatWindow, inputHistory,
                inputWidget, allowedCharacters,
                new HashMap<KeyStroke, Command>(),
                new HashMap<MouseClick, Command>());
    }

    public void processInput(final int displayWidth, final int displayHeight,
            final int width, final int height) {
        mouseX = Mouse.getEventX() * width / displayWidth;
        mouseY = height - Mouse.getEventY() * height / displayHeight - 1;
        while (Mouse.next()) {
            processMouseInput();
        }
        while (Keyboard.next()) {
            processKeyboardInput();
        }
    }

    private void processMouseInput() {
        // TODO: finish bindings for mousewheel.
        final int wheel = Mouse.getEventDWheel();
        if (wheel > 0) {
            chatWindow.scrollToPreviousLine();
        } else if (wheel < 0) {
            chatWindow.scrollToNextLine();
        }
        if (Mouse.getEventButtonState()) {
            pollClickBindings(Mouse.getEventButton());
        }
        if (Mouse.isButtonDown(LEFT_MOUSE_BUTTON)) {
            textField.expandSelectionTo(selectedCharacter());
        }
    }

    private int selectedCharacter() {
        final String message = textField.getText();
        int totalWidth = 0;
        final int promptWidth = inputWidget.getPromptWidth();
        int i;
        for (i = 0; i < message.length(); ++i) {
            totalWidth += inputWidget.getTextWidth(String.valueOf(message
                    .charAt(i)));
            if (totalWidth + promptWidth + inputWidget.x() >= mouseX) {
                break;
            }
        }
        return i;
    }

    private void processKeyboardInput() {
        if (!Keyboard.getEventKeyState()) {
            return;
        }
        pollBindings(Keyboard.getEventKey());
        final char eventCharacter = Keyboard.getEventCharacter();
        if (isCharacterAllowed(eventCharacter)) {
            textField.insertBeforeCaret(eventCharacter);
        }
    }

    private void pollBindings(final int keyCode) {
        final Set<ModifierKey> modifierKeys = getModifierKeys();
        final KeyStroke currentKeyStroke = KeyStroke.create(keyCode,
                modifierKeys);
        for (final Map.Entry<KeyStroke, Command> entry : keyBindings.entrySet()) {
            final KeyStroke boundKeyStroke = entry.getKey();
            if (boundKeyStroke.equals(currentKeyStroke)) {
                entry.getValue().execute();
            }
        }
    }

    private void pollClickBindings(final int button) {
        final Set<ModifierKey> modifierKeys = getModifierKeys();
        for (final Map.Entry<MouseClick, Command> entry : clickBindings
                .entrySet()) {
            final MouseClick mouseClick = entry.getKey();
            // TODO: extract boolean expression.
            if (button == mouseClick.getButton()
                    && mouseClick.modifierKeys().equals(modifierKeys)
                    && mouseClick.getBounds().contains(mouseX, mouseY)) {
                entry.getValue().execute();
            }
        }
    }

    private Set<ModifierKey> getModifierKeys() {
        final List<ModifierKey> list = new ArrayList<ModifierKey>();
        if (ctrlPressed()) {
            list.add(ModifierKey.CTRL);
        }
        if (shiftPressed()) {
            list.add(ModifierKey.SHIFT);
        }
        if (list.isEmpty()) {
            return EnumSet.noneOf(ModifierKey.class);
        }
        return EnumSet.copyOf(list);
    }

    private boolean shiftPressed() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
                | Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    private boolean ctrlPressed() {
        return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                | Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    }

    /*
    private boolean isMacOS() {
        return System.getProperty("os.name").equals("Mac OS X");
    }
    */

    // M1 = COMMAND on MacOS X, CTRL otherwise
    // M2 = SHIFT
    // M3 = OPTION on MacOS X, ALT otherwise
    // M4 = CTRL on MacOS X, nothing otherwise

    public boolean isCharacterAllowed(final char character) {
        return allowedCharacters.indexOf(character) >= 0;
    }

    private void replaceMessageBufferWithHistory() {
        if (!history.isCurrent()) {
            textField.setText(history.get());
        }
    }

    public void createBindings() {
        // TODO: move to file.
        bindKey(textField.new MoveCaretToNextWord(), Keyboard.KEY_RIGHT,
                ModifierKey.CTRL);
        bindKey(textField.new MoveCaretToNextCharacter(), Keyboard.KEY_RIGHT);
        bindKey(textField.new MoveCaretToPreviousWord(), Keyboard.KEY_LEFT,
                ModifierKey.CTRL);
        bindKey(textField.new MoveCaretToPreviousCharacter(), Keyboard.KEY_LEFT);
        bindKey(textField.new MoveCaretToTextStart(), Keyboard.KEY_HOME);
        bindKey(textField.new MoveCaretToTextEnd(), Keyboard.KEY_END);
        bindKey(textField.new DeleteCharacterBeforeCaret(), Keyboard.KEY_BACK);
        bindKey(textField.new DeleteWordBeforeCaret(), Keyboard.KEY_BACK,
                ModifierKey.CTRL);
        bindKey(textField.new DeleteCharacterAfterCaret(), Keyboard.KEY_DELETE);
        bindKey(textField.new DeleteWordAfterCaret(), Keyboard.KEY_DELETE,
                ModifierKey.CTRL);
        bindKey(textField.new ToggleOverwriteMode(), Keyboard.KEY_INSERT);
        bindKey(chatWindow.new ScrollToPreviousPage(), Keyboard.KEY_PRIOR);
        bindKey(chatWindow.new ScrollToNextPage(), Keyboard.KEY_NEXT);
        bindKey(new SendMessage(), Keyboard.KEY_RETURN);
        bindKey(new RetrievePreviousMessageFromHistory(), Keyboard.KEY_UP);
        bindKey(new RetrieveNextMessageFromHistory(), Keyboard.KEY_DOWN);
        bindKey(chatWindow.new ScrollToPreviousLine(), Keyboard.KEY_UP,
                ModifierKey.CTRL);
        bindKey(chatWindow.new ScrollToNextLine(), Keyboard.KEY_DOWN,
                ModifierKey.CTRL);
        bindKey(chatWindow.new ScrollToFirstLine(), Keyboard.KEY_HOME,
                ModifierKey.CTRL);
        bindKey(chatWindow.new ScrollToLastLine(), Keyboard.KEY_END,
                ModifierKey.CTRL);
        bindKey(client.new ToggleFullScreen(), Keyboard.KEY_F11);
        bindKey(inputWidget.new CloseChatInput(), Keyboard.KEY_ESCAPE);

        // TODO: law of demeter violation.
        final Clipboard clipboard = textField.getClipboard();
        bindKey(clipboard.new CopySelection(), Keyboard.KEY_C, ModifierKey.CTRL);
        bindKey(clipboard.new Paste(), Keyboard.KEY_V, ModifierKey.CTRL);
        bindKey(clipboard.new CutSelection(), Keyboard.KEY_X, ModifierKey.CTRL);

        // TODO: law of demeter violation.
        final Selection selection = textField.getSelection();
        bindKey(selection.new ExpandToTextStart(), Keyboard.KEY_HOME,
                ModifierKey.SHIFT);
        bindKey(selection.new ExpandToTextEnd(), Keyboard.KEY_END,
                ModifierKey.SHIFT);
        bindKey(selection.new ExpandToPreviousWord(), Keyboard.KEY_LEFT,
                ModifierKey.SHIFT, ModifierKey.CTRL);
        bindKey(selection.new ExpandToPreviousCharacter(), Keyboard.KEY_LEFT,
                ModifierKey.SHIFT);
        bindKey(selection.new ExpandToAll(), Keyboard.KEY_A, ModifierKey.CTRL);
        bindKey(selection.new ExpandToNextWord(), Keyboard.KEY_RIGHT,
                ModifierKey.SHIFT, ModifierKey.CTRL);
        bindKey(selection.new ExpandToNextCharacter(), Keyboard.KEY_RIGHT,
                ModifierKey.SHIFT);

        bindClick(new MoveCaretToClickedCharacter(), LEFT_MOUSE_BUTTON,
                inputWidget.rectangle());
        bindClick(new ExpandSelectionToClickedCharacter(), LEFT_MOUSE_BUTTON,
                inputWidget.rectangle(), ModifierKey.SHIFT);
        bindClick(new ScrollToLineClicked(), LEFT_MOUSE_BUTTON,
                chatWindow.rectangle());
    }

    private void bindKey(final Command command, final int naturalKey,
            final ModifierKey... modifierKeys) {
        keyBindings.put(KeyStroke.create(naturalKey, modifierKeys), command);
    }

    private void bindClick(final Command command, final int mouseButton,
            final Rectangle rectangle, final ModifierKey... modifierKeys) {
        clickBindings.put(
                MouseClick.create(mouseButton, rectangle, modifierKeys),
                command);
    }

    public class RetrievePreviousMessageFromHistory implements Command {
        @Override
        public void execute() {
            history.previous();
            replaceMessageBufferWithHistory();
        }
    }

    public class RetrieveNextMessageFromHistory implements Command {
        @Override
        public void execute() {
            history.next();
            replaceMessageBufferWithHistory();
        }
    }

    public class SendMessage implements Command {
        @Override
        public void execute() {
            final String trimmedMessage = textField.getText();
            if (!trimmedMessage.isEmpty()) {
                if (!lineIsCommand(trimmedMessage)) {
                    client.sendMessage(trimmedMessage);
                }
                history.add(trimmedMessage);
            }
            textField.deleteAll();
            inputWidget.close();
        }

        private boolean lineIsCommand(final String line) {
            return line.startsWith("/");
        }
    }

    public class MoveCaretToClickedCharacter implements Command {
        @Override
        public void execute() {
            textField.moveCaretTo(selectedCharacter());
        }
    }

    public class ExpandSelectionToClickedCharacter implements Command {
        @Override
        public void execute() {
            textField.expandSelectionTo(selectedCharacter());
        }
    }

    public class ScrollToLineClicked implements Command {
        @Override
        public void execute() {
            chatWindow.scrollToLine(selectedLine());
        }

        private int selectedLine() {
            return (chatWindow.y2() - mouseY + ChatWindow.LINE_HEIGHT)
                    / ChatWindow.LINE_HEIGHT;
        }
    }
}
