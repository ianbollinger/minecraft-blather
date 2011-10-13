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

import java.awt.AWTError;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Ian D. Bollinger
 */
public final class Clipboard {
    private final Caret caret;
    private final Selection selection;
    private final java.awt.datatransfer.Clipboard systemClipboard;
    private final Logger log = Logger.getLogger("Blather");

    Clipboard(final Caret caret, final Selection selection,
            final java.awt.datatransfer.Clipboard systemClipboard) {
        this.caret = caret;
        this.selection = selection;
        this.systemClipboard = systemClipboard;
    }

    public static Clipboard create(final Caret caret, final Selection selection) {
        return new Clipboard(caret, selection, getSystemClipboard());
    }

    // TODO: eliminate static.
    private static java.awt.datatransfer.Clipboard getSystemClipboard() {
        // TODO: inject dummy if this fails instead.
        try {
            return Toolkit.getDefaultToolkit().getSystemClipboard();
        } catch (final AWTError e) {
            // Toolkit could not be found.
            throw new IllegalStateException(e);
        } catch (final HeadlessException e) {
            // This should never happen.
            throw new IllegalStateException(e);
        }
    }

    public void cutSelection() {
        copySelection();
        selection.delete();
    }

    public void copySelection() {
        if (selection.empty()) {
            return;
        }
        // TODO: static reference.
        final StringSelection stringSelection = new StringSelection(
                selection.getText());
        setSystemClipboardContentsTo(stringSelection);
    }

    private void setSystemClipboardContentsTo(
            final StringSelection stringSelection) {
        try {
            systemClipboard.setContents(stringSelection, null);
        } catch (final IllegalStateException e) {
            log.severe("System clipboard was not available for writing.");
            return;
        }
    }

    public void paste() {
        final Transferable transferable;
        try {
            transferable = systemClipboard.getContents(null);
        } catch (final IllegalStateException e) {
            log.severe("System clipboard was not available for reading.");
            return;
        }
        if (isString(transferable)) {
            if (!selection.empty()) {
                selection.delete();
            }
            caret.beforeWhichInsert(stringFrom(transferable));
        }
    }

    private String stringFrom(final Transferable transferable) {
        try {
            return (String) transferable
                    .getTransferData(DataFlavor.stringFlavor);
        } catch (final UnsupportedFlavorException e) {
            log.throwing(this.getClass().toString(), "paste", e);
        } catch (final IOException e) {
            log.throwing(this.getClass().toString(), "paste", e);
        }
        return "";
    }

    private boolean isString(final Transferable transferable) {
        return transferable != null
                && transferable.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    public class CopySelection implements Command {
        @Override
        public void execute() {
            copySelection();
        }
    }

    public class CutSelection implements Command {
        @Override
        public void execute() {
            cutSelection();
        }
    }

    public class Paste implements Command {
        @Override
        public void execute() {
            paste();
        }
    }
}
