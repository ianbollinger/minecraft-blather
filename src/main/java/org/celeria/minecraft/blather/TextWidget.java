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

public class TextWidget extends AbstractWidget {
    private AbstractWidget parent;
    private final String text;
    private Color color;
    private GameClient renderer;

    TextWidget(final AbstractWidget parent,
            final Rectangle rectangle, final String text,
            final Color color, final GameClient renderer) {
        super(rectangle);
        this.parent = parent;
        this.text = text;
        this.color = color;
        this.renderer = renderer;
    }

    public void draw() {
        renderer.drawText(text, parent.x() + x(), parent.y() + y(), color);
    }
}
