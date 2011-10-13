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

package net.minecraft.src;

import org.celeria.minecraft.blather.Ensure;
import net.minecraft.client.Minecraft;

/**
 * Optional ModLoader class for Blather. With ModLoader installed, this class
 * with initialize Blather at the first opportunity, rather than on demand.
 *
 * @author Ian D. Bollinger
 */
public class mod_Blather extends AbstractMod {
    @Override
    public String version() {
        return Blather.version();
    }

    @Override
    protected void initialize(final Minecraft game) {
        Ensure.notNull(game, "game");
        Blather.initialize(game);
    }
}
