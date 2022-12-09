package gremlins;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import processing.core.PImage;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    // Test Stone constructer.
    public void testStoneConstructor() {
        Tile s = new Stone(20, 20, null);
        assertNotNull(s);
    }

    @Test
    // Test Brick constructer.
    public void testBrickSetDestroyImg() {
        Brick b = new Brick(20, 20, null);
        assertNotNull(b);
        assertNull(b.getImg());
        b.setDestroyImg(new PImage());
        assertNotNull(b.getImg());
    }

    @Test
    // Test Brick's destroy method.
    public void testBrickDestroy() {
        Brick b = new Brick(20, 20, null);
        assert(b.isDestroyed() == false);
        b.destroy();
        assert(b.isDestroyed() == true);
    }

    @Test
    // Test Brick's count method once it's destroyed.
    public void testBrickDestroyCount() {
        Brick b = new Brick(20, 20, null);
        assert(b.getDestroyCount() == 0);
        b.destroyCountPlus();
        assert(b.getDestroyCount() == 1);
    }

    @Test
    // Test Exit constructer.
    public void testExitConstructor() {
        Exit e = new Exit(20, 20, null);
        assertNotNull(e);
    }

    @Test
    // Test Portal constructer.
    public void testPortalConstructor() {
        Portal p = new Portal(20, 20, null, null);
        assertNotNull(p);
    }
}
