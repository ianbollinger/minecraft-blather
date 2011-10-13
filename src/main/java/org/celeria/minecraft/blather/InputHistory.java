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

/**
 * @author Ian D. Bollinger
 */
public final class InputHistory {
    private OverwritingRingBuffer<String> history;
    private int position;

    InputHistory(final OverwritingRingBuffer<String> history,
            final int position) {
        this.history = history;
        this.position = position;
    }

    /**
     * @param capacity
     * @return
     */
    public static InputHistory create(final int capacity) {
        Ensure.nonnegative(capacity, "capacity");
        final OverwritingRingBuffer<String> history = OverwritingRingBuffer
                .create(capacity);
        history.add("");
        final int position = 1;
        return new InputHistory(history, position);
    }

    /**
     * @param message
     */
    public void add(final String message) {
        history.add(message);
        position = history.size();
    }

    /**
     * @return
     */
    public String get() {
        return history.get(position);
    }

    /**
     * @return
     */
    public boolean isCurrent() {
        return position == history.size();
    }

    /**
     *
     */
    public void next() {
        position = Math.min(history.size() - 1, position + 1);
    }

    /**
     *
     */
    public void previous() {
        position = Math.max(0, position - 1);
    }
}
