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
 * This is meant to patch over ModLoader's API with something better.
 *
 * @author Ian D. Bollinger
 */
public abstract class AbstractMod extends BaseMod {
    /**
     * Returns this mod's current version.
     *
     * @return the version
     */
    @Override
    public final String Version() {
        return version();
    }

    /**
     * Returns this mod's current version.
     *
     * @return the version
     */
    public abstract String version();

    /**
     * Called by ModLoader after all mods are loaded.
     */
    @Override
    public final void ModsLoaded() {
        enableCallback();
    }

    private void enableCallback() {
        final boolean enable = true;
        final boolean useClock = false;
        ModLoader.SetInGameHook(this, enable, useClock);
    }

    /**
     * Called by ModLoader each tick (if enabled).
     *
     * @param deltaTime
     *            time elapsed since the last call
     * @param game
     *            the Minecraft client
     * @return {@code false} if this callback is to be disabled, {@code true}
     *         otherwise
     */
    @Override
    public final boolean OnTickInGame(
            @SuppressWarnings("unused") final float deltaTime,
            final Minecraft game) {
        Ensure.notNull(game, "game");
        initialize(game);
        return false;
    }

    /**
     * Called after Minecraft has loaded.
     *
     * @param game
     *            the Minecraft client
     */
    protected void initialize(
            @SuppressWarnings("unused") final Minecraft game) {
        // To be optionally overridden.
    }
}
