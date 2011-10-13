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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ian D. Bollinger
 */
public final class LineWrapper {
    private final GameClient client;
    private final String text;
    private final int width;
    private final Map<String, Integer> widths = new HashMap<String, Integer>();
    private final List<String> result = new ArrayList<String>();

    public LineWrapper(final GameClient client, final String text,
            final int width) {
        this.client = client;
        this.text = text;
        this.width = width;
    }

    public List<String> split() {
        final String[] words = text.split(" ");
        final int spaceWidth = client.textWidthOf(" ");
        for (final String word : words) {
            widths.put(word, client.textWidthOf(word));
        }
        result.clear();
        int index = 0;
        while (index < words.length) {
            index += nextWord(words, spaceWidth, index);
        }
        return result;
    }

    private int nextWord(final String[] words, final int spaceWidth,
            final int index) {
        final String word = words[index];
        if (widths.get(word) > width) {
            breakWord(word);
            return 1;
        }
        return nextLine(words, spaceWidth, index);
    }

    private int nextLine(final String[] words, final int spaceWidth,
            final int index) {
        final StringBuilder builder = new StringBuilder();
        int i;
        int currentWidth = 0;
        for (i = 0; index + i < words.length
                && currentWidth + widths.get(words[index + i]) < width; ++i) {
            currentWidth += widths.get(words[index + i]) + spaceWidth;
            builder.append(words[index + i]).append(' ');
        }
        final String line = builder.toString();
        /*
         * for (int j = 0; fontRenderer.getStringWidth(line) > width; line =
         * line .substring(j)) { for (j = 0;
         * fontRenderer.getStringWidth(line.substring(0, j + 1)) <= width;) {
         * ++j; } if (!line.substring(0, j).trim().isEmpty()) {
         * result.add(line.substring(0, j)); } }
         */
        if (!line.trim().isEmpty()) {
            result.add(line);
        }
        return i;
    }

    private void breakWord(final String word) {
        int length = word.length() - 1;
        while (client.textWidthOf(word.substring(0, length)) > width) {
            --length;
        }
        result.add(word.substring(0, length));
        result.add(word.substring(length));
    }
}
