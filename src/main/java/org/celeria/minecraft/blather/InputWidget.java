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

import org.lwjgl.util.Color;
import org.lwjgl.util.Rectangle;

/**
 * @author Ian D. Bollinger
 */
public class InputWidget extends AbstractWidget {
    private final GameClient client;
    private final TextField textField;
    private final CaretWidget caret;
    private final String prompt;
    private final Color backgroundColor;
    private final Color selectionColor;
    private final Color textColor;
    private final int textX;
    private final int textY;

    InputWidget(final Rectangle rectangle, final GameClient client,
            final TextField textField, final CaretWidget caret,
            final String prompt, final Color backgroundColor,
            final Color selectionColor, final Color textColor, final int textX,
            final int textY) {
        super(rectangle);
        this.client = client;
        this.textField = textField;
        this.caret = caret;
        this.prompt = prompt;
        this.backgroundColor = backgroundColor;
        this.selectionColor = selectionColor;
        // TODO: make a text widget.
        this.textColor = textColor;
        this.textX = textX;
        this.textY = textY;
    }

    public static InputWidget create(final GameClient client,
            final TextField textField, final Color backgroundColor,
            final Color selectionColor, final Color textColor) {
        // TODO: pull these out
        // TODO: store in properties file.
        final String prompt = "> ";
        final int textX = 2;
        final int textY = 2;
        final Rectangle caretRectangle = new Rectangle(1, 1, 1, 11);
        final Color caretColor = new Color(0xA0, 0xA0, 0xFF, 0xFF);
        final int caretBlinkRate = 6;
        final CaretWidget caret = CaretWidget.create(caretRectangle, client,
                textField, caretColor, caretBlinkRate);
        final Rectangle rectangle = new Rectangle(2, 0, 0, 13);
        return new InputWidget(rectangle, client, textField, caret, prompt,
                backgroundColor, selectionColor, textColor, textX, textY);
    }

    public void draw(final int updateCounter) {
        client.drawRectangle(rectangle(), backgroundColor);
        final String text = prompt + textField.getText();
        client.drawText(text, x() + textX, y() + textY, textColor);
        if (textField.hasSelection()) {
            drawSelection();
        }
        caret.draw(this, updateCounter);
    }

    public int textX() {
        return x() + caret.x() + getPromptWidth();
    }

    public int getPromptWidth() {
        return getTextWidth(prompt);
    }

    public int getTextWidth(final String text) {
        return client.textWidthOf(text);
    }

    public void resize(final int screenWidth, final int screenHeight) {
        final int x = x();
        final int height = height();
        setRectangle(x, screenHeight - height - 2, screenWidth - x - 2, height);
    }

    // TODO: make a selection widget.
    private void drawSelection() {
        final String selectedText = textField.getSelectedText();
        final int selectionWidth = client.textWidthOf(selectedText) + 1;
        final String unselectedText = textField.getTextBeforeSelection();
        final int selectionStart = client.textWidthOf(unselectedText);
        final int selectionX = textX() + selectionStart;
        final int selectionY = y() + caret.y();
        client.drawRectangle(selectionX, selectionY, selectionX
                + selectionWidth, selectionY + caret.height() - 1,
                selectionColor);
    }

    public void close() {
        client.closeCurrentScreen();
    }

    public class CloseChatInput implements Command {
        @Override
        public void execute() {
            close();
        }
    }
}
