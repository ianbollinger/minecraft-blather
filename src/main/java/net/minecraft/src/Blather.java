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

import java.util.List;
import net.minecraft.client.Minecraft;
import org.celeria.minecraft.blather.*;
import org.lwjgl.util.Color;

/**
 * @author Ian D. Bollinger
 */
public class Blather {
    // TODO: inject version number.
    static final String VERSION = "0.0.1-SNAPSHOT";
    // TODO: load this from file
    private static final int INPUT_HISTORY_CAPACITY = 10;
    private static Blather instance;
    private final GameClient client;
    private final InputHandler inputHandler;
    private final InputWidget inputWidget;
    private final ChatWindow chatWindow;
    private ReplacementGui replacementGui;
    private final MockMessageList<ChatLine> mockList;
    private boolean installed;

    Blather(final GameClient client, final ChatWindow chatWindow,
            final InputWidget inputWidget, final InputHandler inputHandler,
            final MockMessageList<ChatLine> mockList, final boolean installed) {
        this.client = client;
        this.chatWindow = chatWindow;
        this.inputWidget = inputWidget;
        this.inputHandler = inputHandler;
        this.mockList = mockList;
        this.installed = installed;
    }

    public static void initialize(final Minecraft game) {
        Ensure.notNull(game, "game");
        Blather.getInstance(game).install();
    }

    public static Blather getInstance(final Minecraft game) {
        Ensure.notNull(game, "game");
        if (instance == null) {
            instance = Blather.create(game);
        }
        return instance;
    }

    private static Blather create(final Minecraft game) {
        final boolean installed = false;
        final GameClient client = GameClient.create(game);
        final ChatWindow chatWindow = ChatWindow.create(client);
        final TextField textField = TextField.create();
        final Color backgroundColor = new Color(0, 0, 0, 0x80);
        final Color selectionColor = new Color(0x80, 0x80, 0xFF, 0x80);
        final Color textColor = new Color(0xE0, 0xE0, 0xE0, 0xFF);
        final InputWidget inputWidget = InputWidget.create(client, textField,
                backgroundColor, selectionColor, textColor);
        final InputHistory inputHistory = InputHistory
                .create(INPUT_HISTORY_CAPACITY);
        final InputHandler inputHandler = InputHandler.create(client,
                textField, chatWindow, inputHistory, inputWidget,
                ChatAllowedCharacters.allowedCharacters);
        return new Blather(client, chatWindow, inputWidget, inputHandler,
                MockMessageList.<ChatLine>create(chatWindow), installed);
    }

    public static String version() {
        return VERSION;
    }

    public void draw(final int width, final int height,
            final int updateCounter) {
        install();
        inputWidget.resize(width, height);
        inputWidget.draw(updateCounter);
    }

    public void install() {
        if (installed) {
            return;
        }
        final GuiIngame gui = client.gui();
        installChatWindow(gui);
        // TODO: Eliminate create.
        // (Doing that will require statics, factories, or mutability though.)
        replacementGui = ReplacementGui.create(client, gui, chatWindow);
        client.setGui(replacementGui);
        inputHandler.createBindings();
        installed = true;
    }

    private void installChatWindow(final GuiIngame gui) {
        final Reflection<GuiIngame> reflection = Reflection.create(gui);
        final List<ChatLine> messages = reflection.setField(false ? "f"
                : "chatMessageList", mockList);
        for (final ChatLine message : ReverseIterable
                .<ChatLine> create(messages)) {
            chatWindow.addMessage(message.message);
        }
    }

    public void processInput(final int width, final int height) {
        inputHandler.processInput(client.displayWidth(),
                client.displayHeight(), width, height);
    }
}
