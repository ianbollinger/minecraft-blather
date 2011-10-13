package net.minecraft.src;

import static org.junit.Assert.*;
import net.minecraft.src.Blather;
import net.minecraft.src.mod_Blather;
import org.junit.*;

public class ModBlatherTest {
    private mod_Blather mod;

    @Before
    public void setUp() {
        this.mod = new mod_Blather();
    }

    @Test
    public void testVersion() {
        assertEquals(mod.version(), mod.Version());
    }

    @Test
    public void testVersion2() {
        assertEquals(Blather.version(), mod.version());
    }

    @Test
    public void testModsLoaded() {
        mod.ModsLoaded();
    }

    @Test
    public void testOnTickInGame() {
        assertFalse(mod.OnTickInGame(0, null));
    }

    @Test
    public void initialize() {
        mod.initialize(null);
    }
}
