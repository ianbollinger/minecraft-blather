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

// TODO: better name.
/**
 * @author Ian D. Bollinger
 */
public class CaretWidget extends AbstractWidget {
    private final GameClient client;
    private final TextField textField;
    private final Color color;
    private final int blinkRate;

    CaretWidget(final Rectangle rectangle, final GameClient client,
            final TextField textField, final Color color, final int blinkRate) {
        super(rectangle);
        this.client = client;
        this.textField = textField;
        this.color = color;
        this.blinkRate = blinkRate;
    }

    public static CaretWidget create(final Rectangle rectangle,
            final GameClient client, final TextField textField,
            final Color color, final int blinkRate) {
        Ensure.notNull(client, "client");
        Ensure.notNull(textField, "textField");
        Ensure.notNull(rectangle, "rectangle");
        Ensure.notNull(color, "color");
        Ensure.positive(blinkRate, "blinkRate");
        return new CaretWidget(rectangle, client, textField, color, blinkRate);
    }

    public void draw(final InputWidget inputWidget, final int updateCounter) {
        if (blink(updateCounter)) {
            return;
        }
        final String textBeforeCaret = textField.getTextBeforeCaret();
        final int caretStart = client.textWidthOf(textBeforeCaret);
        final int x1 = inputWidget.textX() + caretStart;
        final int y1 = inputWidget.y() + y();
        client.drawRectangle(x1, y1, x1 + trueWidth(), y1 + height() - 1,
                color);
    }

    private boolean blink(final int updateCounter) {
        return updateCounter / blinkRate % 2 == 0;
    }

    // TODO: have this accurately represent its position instead.

    private int trueWidth() {
        if (textField.hasOverwriteModeOn()) {
            final String character = textField.getCharacterAfterCaret();
            return client.textWidthOf(character);
        }
        return width();
    }
}
