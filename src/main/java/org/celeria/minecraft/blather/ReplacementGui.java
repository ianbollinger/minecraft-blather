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

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiIngame;

/**
 * @author Ian D. Bollinger
 */
public class ReplacementGui extends GuiIngame {
    private final GameClient client;
    private final ChatWindow chatWindow;
    private final GuiIngame realGui;

    ReplacementGui(final Minecraft game, final GameClient client,
            final ChatWindow chatWindow, final GuiIngame realGui) {
        super(game);
        this.client = client;
        this.chatWindow = chatWindow;
        this.realGui = realGui;
    }

    /**
     * @param client
     * @param realGui
     * @param chatWindow
     * @return
     */
    public static ReplacementGui create(final GameClient client,
            final GuiIngame realGui, final ChatWindow chatWindow) {
        Ensure.notNull(client, "client");
        Ensure.notNull(realGui, "realGui");
        Ensure.notNull(chatWindow, "chatWindow");
        return new ReplacementGui(client.game(), client, chatWindow,
                realGui);
    }

    /**
     * @param message
     */
    @Override
    public void addChatMessage(final String message) {
        Ensure.notNull(message, "message");
        chatWindow.addMessage(message);
    }

    /**
     * @param message
     */
    @Override
    public void addChatMessageTranslate(final String message) {
        Ensure.notNull(message, "message");
        realGui.addChatMessageTranslate(message);
    }

    /**
     *
     */
    @Override
    public void clearChatMessages() {
        realGui.clearChatMessages();
    }

    /**
     * @param deltaTime
     * @param isGuiOpen
     * @param mouseX
     * @param mouseY
     */
    @Override
    public void renderGameOverlay(final float deltaTime,
            final boolean isGuiOpen, final int mouseX, final int mouseY) {
        client.voodoo();
        chatWindow.resize(client.scaledDisplayWidth(), client.scaledDisplayHeight());
        chatWindow.draw(client.currentScreenIsChat());
        realGui.renderGameOverlay(deltaTime, isGuiOpen, mouseX, mouseY);
    }

    /**
     * @param message
     */
    @Override
    public void setRecordPlayingMessage(final String message) {
        Ensure.notNull(message, "message");
        realGui.setRecordPlayingMessage(message);
    }

    /**
     *
     */
    @Override
    public void updateTick() {
        chatWindow.update();
    }
}
