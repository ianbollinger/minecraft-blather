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

/**
 * @author Ian D. Bollinger
 */
public final class KeyStroke extends AbstractTrigger {
    private static final int SIZE_OF_KEY_CODE = 8;
    private final int naturalKey;

    KeyStroke(final int naturalKey, final Set<ModifierKey> modifierKeys) {
        super(modifierKeys);
        this.naturalKey = naturalKey;
    }

    /**
     * @param naturalKey
     * @param modifierKeys
     * @return
     */
    public static KeyStroke create(final int naturalKey,
            final ModifierKey... modifierKeys) {
        Ensure.nonnegative(naturalKey, "naturalKey");
        Ensure.notNull(modifierKeys, "modifierKeys");
        return new KeyStroke(naturalKey, createModifierKeySetFrom(modifierKeys));
    }

    /**
     * @param naturalKey
     * @param modifierKeys
     * @return
     */
    public static KeyStroke create(final int naturalKey,
            final Set<ModifierKey> modifierKeys) {
        Ensure.nonnegative(naturalKey, "naturalKey");
        Ensure.notNull(modifierKeys, "modifierKeys");
        return new KeyStroke(naturalKey, modifierKeys);
    }

    /**
     * @return
     */
    public int naturalKey() {
        return naturalKey;
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof KeyStroke)) {
            return false;
        }
        final KeyStroke other = (KeyStroke) object;
        return naturalKey == other.naturalKey
                && modifierKeys().equals(other.modifierKeys());
    }

    @Override
    public int hashCode() {
        return naturalKey & modifierKeys().hashCode() << SIZE_OF_KEY_CODE;
    }
}
