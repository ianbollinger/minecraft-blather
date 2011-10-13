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

import java.util.Collection;
import java.util.Map;

public final class Ensure {
    private Ensure() {
        // Cannot instantiate.
    }

    public static void notNull(final Object object, final String argumentName) {
        final String format = "The argument '%1$s' must not be null.";
        if (object == null) {
            throw new NullPointerException(String.format(format, argumentName));
        }
    }

    public static void notEmpty(final String string,
            final String argumentName) {
        Ensure.notEmpty(string.isEmpty(), string, argumentName);
    }

    public static void notEmpty(final Collection<?> collection,
            final String argumentName) {
        Ensure.notEmpty(collection.isEmpty(), collection, argumentName);
    }

    public static void notEmpty(final Map<?, ?> map,
            final String argumentName) {
        Ensure.notEmpty(map.isEmpty(), map, argumentName);
    }

    public static void notEmpty(final Object[] array,
            final String argumentName) {
        Ensure.notEmpty(array.length == 0, array, argumentName);
    }

    private static void notEmpty(final boolean empty, final Object object,
            final String argumentName) {
        Ensure.notNull(object, argumentName);
        final String format = "The argument '%1$s' must not be empty.";
        Ensure.that(!empty, String.format(format, argumentName));
    }

    public static void noNulls(final Collection<?> collection,
            final String argumentName) {
        Ensure.notNull(collection, argumentName);
        Ensure.noNullElements(collection, argumentName, "elements");
    }

    public static void noNulls(final Map<?, ?> map, final String argumentName) {
        Ensure.notNull(map, argumentName);
        noNullElements(map.keySet(), argumentName, "keys");
        noNullElements(map.values(), argumentName, "values");
    }

    private static <T> void noNullElements(final Collection<T> collection,
            final String argumentName, final String name) {
        if (collection.contains(null)) {
            throw new NullPointerException(noNullsMessage(argumentName, name));
        }
    }

    public static void noNulls(final Object[] array,
            final String argumentName) {
        Ensure.notNull(array, argumentName);
        for (final Object element : array) {
            if (element == null) {
                final String message = noNullsMessage(argumentName, "elements");
                throw new NullPointerException(message);
            }
        }
    }

    public static void positive(final int integer, final String argumentName) {
        final String format = "The argument '%1$s' must be positive, not "
                + "%2$s.";
        final String message = String.format(format, argumentName, integer);
        Ensure.that(integer > 0, message);
    }

    public static void nonnegative(final int integer,
            final String argumentName) {
        final String format = "The argument '%1$s' must be nonnegative, not "
                + "%2$s.";
        Ensure.that(integer >= 0, String.format(format, argumentName, integer));
    }

    public static void notEmptyAndNoNulls(final Collection<?> collection,
            final String name) {
        Ensure.notEmpty(collection, name);
        Ensure.noNulls(collection, name);
    }

    public static void notEmptyAndNoNulls(final Map<?, ?> map, final String name) {
        Ensure.notEmpty(map, name);
        Ensure.noNulls(map, name);
    }

    public static void that(final boolean expression, final String message) {
        Ensure.notNull(message, "message");
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    private static String noNullsMessage(final String argumentName,
            final String itemName) {
        final String format = "The argument '%1$s' must not contain null %2$s.";
        return String.format(format, argumentName, itemName);
    }
}
