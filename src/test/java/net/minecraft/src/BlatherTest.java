package net.minecraft.src;

import static org.junit.Assert.*;
import org.junit.*;

public class BlatherTest {
    private Blather blather;

    public void setUp() {
        blather = new Blather(null, null, null, null, null, false);
    }

    public void testVersion() {
        Blather.version();
    }

    public void testnitialize() {
        Blather.initialize(null);
    }

    public void testGetInstance() {
        Blather.getInstance(null);
    }

    public void testDraw() {
        blather.draw(0, 0, 0);
    }

    public void testInstall() {
        blather.install();
    }

    public void testProcessInput() {
        blather.processInput(0, 0);
    }
}
