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

import java.lang.reflect.Field;

/**
 * @author Ian D. Bollinger
 */
public class Reflection<T> {
    private final T instance;

    Reflection(final T instance) {
        this.instance = instance;
    }

    /**
     * @param instance
     * @return
     */
    public static <T> Reflection<T> create(final T instance) {
        Ensure.notNull(instance, "instance");
        return new Reflection<T>(instance);
    }

    /**
     * @param fieldName
     * @param value
     * @return
     */
    public <F> F setField(final String fieldName, final F value) {
        Ensure.notNull(fieldName, "fieldName");
        final Field field = getField(fieldName);
        makeAccessible(field);
        @SuppressWarnings("unchecked")
        final F oldValue = (F) getFieldValue(field);
        setFieldValue(field, value);
        return oldValue;
    }

    private void makeAccessible(final Field field) {
        try {
            field.setAccessible(true);
        } catch (final SecurityException e) {
            throw ReflectionException.causedBy(e);
        }
    }

    private Field getField(final String name) {
        try {
            return instance.getClass().getDeclaredField(name);
        } catch (final SecurityException e) {
            throw ReflectionException.causedBy(e);
        } catch (final NoSuchFieldException e) {
            throw ReflectionException.causedBy(e);
        }
    }

    private Object getFieldValue(final Field field) {
        try {
            return field.get(instance);
        } catch (final IllegalArgumentException e) {
            throw ReflectionException.causedBy(e);
        } catch (final IllegalAccessException e) {
            throw ReflectionException.causedBy(e);
        } catch (final ExceptionInInitializerError e) {
            throw ReflectionException.causedBy(e);
        }
    }

    private void setFieldValue(final Field field, final Object value) {
        try {
            field.set(instance, value);
        } catch (final IllegalArgumentException e) {
            throw ReflectionException.causedBy(e);
        } catch (final IllegalAccessException e) {
            throw ReflectionException.causedBy(e);
        } catch (final ExceptionInInitializerError e) {
            throw ReflectionException.causedBy(e);
        }
    }
}
