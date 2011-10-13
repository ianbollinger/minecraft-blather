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

import java.util.Set;
import org.lwjgl.util.Rectangle;

/**
 * @author Ian D. Bollinger
 */
public class MouseClick extends AbstractTrigger {
    // TODO don't use an integer to represent an enumeration (?).
    private final int button;
    private final Rectangle bounds;

    MouseClick(final Set<ModifierKey> modifierKeys, final int button,
            final Rectangle bounds) {
        super(modifierKeys);
        this.button = button;
        this.bounds = bounds;
    }

    public static MouseClick create(final int button, final int x, final int y,
            final int width, final int height,
            final ModifierKey... modifierKeys) {
        final Rectangle bounds = new Rectangle(x, y, width, height);
        return new MouseClick(createModifierKeySetFrom(modifierKeys), button,
                bounds);
    }

    public static MouseClick create(final int button,
            final Rectangle rectangle, final ModifierKey... modifierKeys) {
        return new MouseClick(createModifierKeySetFrom(modifierKeys), button,
                rectangle);
    }

    public int getButton() {
        return button;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
