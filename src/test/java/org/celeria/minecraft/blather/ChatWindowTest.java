package org.celeria.minecraft.blather;

import static org.junit.Assert.*;

import org.celeria.minecraft.blather.ChatWindow;
import org.junit.Before;
import org.junit.Test;

public class ChatWindowTest {
    private ChatWindow chatWindow;

    @Before
    public void setUp() {
        chatWindow = new ChatWindow(null, null, null, null, null, 0, 0);
    }

    @Test
    public void testChatWindow() {
        fail("Not yet implemented");
    }

    @Test
    public void testCreate() {
        fail("Not yet implemented");
    }

    @Test
    public void testDraw() {
        chatWindow.draw(false);
    }

    @Test
    public void testResize() {
        chatWindow.resize(0, 0);
    }

    @Test
    public void testAddMessage() {
        chatWindow.addMessage("");
    }

    @Test
    public void testClearMessages() {
        chatWindow.clearMessages();
    }

    @Test
    public void testScrollToLine() {
        fail("Not yet implemented");
    }

    @Test
    public void testScrollToPreviousLine() {
        fail("Not yet implemented");
    }

    @Test
    public void testScrollToNextLine() {
        fail("Not yet implemented");
    }

    @Test
    public void testScrollToPreviousPage() {
        fail("Not yet implemented");
    }

    @Test
    public void testScrollToNextPage() {
        fail("Not yet implemented");
    }

    @Test
    public void testScrollToFirstLine() {
        fail("Not yet implemented");
    }

    @Test
    public void testScrollToLastLine() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

}
