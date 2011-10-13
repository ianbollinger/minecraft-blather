package org.celeria.minecraft.blather;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.celeria.minecraft.blather.ReverseIterator;
import org.junit.Before;
import org.junit.Test;

public class ReverseIteratorTest {
    private ListIterator<Object> iterator;
    private ReverseIterator<Object> reverseIterator;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        iterator = mock(ListIterator.class);
        reverseIterator = ReverseIterator.<Object>create(iterator);
    }

    @Test
    public void testUnderlyingListIterator() {
        assertEquals(iterator, reverseIterator.underlyingListIterator());
    }

    @Test(expected = NullPointerException.class)
    public void testCreate() {
        ReverseIterator.<Object>create(null);
    }

    @Test
    public void testHasNext() {
        reverseIterator.hasNext();
        verify(iterator).hasPrevious();
    }

    @Test
    public void testNext() {
        final Object value = new Object();
        when(iterator.hasPrevious()).thenReturn(true);
        when(iterator.previous()).thenReturn(value);
        assertEquals(value, reverseIterator.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextFalse() {
        when(iterator.hasPrevious()).thenReturn(false);
        reverseIterator.next();
    }

    @Test
    public void testRemove() {
        reverseIterator.remove();
        verify(iterator).remove();
    }
}
