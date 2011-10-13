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

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author Ian D. Bollinger
 */
public abstract class AbstractTrigger {
    private final Set<ModifierKey> modifierKeys;

    /**
     * @param modifierKeys
     */
    public AbstractTrigger(final Set<ModifierKey> modifierKeys) {
        this.modifierKeys = modifierKeys;
    }

    /**
     * @param modifierKeys
     * @return
     */
    static Set<ModifierKey> createModifierKeySetFrom(
            final ModifierKey... modifierKeys) {
        if (modifierKeys.length == 0) {
            return EnumSet.noneOf(ModifierKey.class);
        }
        return EnumSet.copyOf(Arrays.asList(modifierKeys));
    }

    /**
     * @return
     */
    public Set<ModifierKey> modifierKeys() {
        return modifierKeys;
    }
}
