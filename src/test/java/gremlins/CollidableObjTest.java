package gremlins;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import processing.core.PImage;


public class CollidableObjTest {

    @Test
    // Test CollidableObj constructer.
    public void testCollidableObjConstructor() {
        CollidableObj wiz = new Wizard(20, 20, null);
        assertNotNull(wiz);
        PImage img = wiz.getImg();
        assertNull(img);
    }

    @Test
    // Test collide method with a given target list.
    public void testCollideTargetList() {
        CollidableObj wiz = new Wizard(20, 20, null);
        List<Slime> slimes = new ArrayList<Slime>();
        slimes.add(new Slime(20, 40, 0, null));
        slimes.add(new Slime(40, 40, 0, null));
        slimes.add(new Slime(0, 20, 0, null));
        slimes.add(new Slime(20, 0, 0, null));
        slimes.add(new Slime(38, 20, 0, null));
        assertTrue(wiz.collide(wiz, slimes));
    }

    @Test
    // Test collide method with a given target object.
    public void testCollideTarget() {
        CollidableObj wiz = new Wizard(20, 20, null);
        // not colliding
        Slime slime0 = new Slime(60, 60, 0, null);
        assertFalse(wiz.collide(wiz, slime0));

        //colliding
        Slime slime = new Slime(20, 30, 0, null);
        assertTrue(wiz.collide(wiz, slime));
    }

    @Test
    // Test overlap method
    public void testOverlapTarget() {
        CollidableObj wiz = new Wizard(20, 20, null);

        // overlapping
        Portal overlapPortal = new Portal(20, 20, null, null);
        assertTrue(wiz.overlap(wiz, overlapPortal));

        // not touching at all
        Potion potion0 = new Potion(60, 60, null);
        assertFalse(wiz.overlap(wiz, potion0));

        // not overlapping - same x 
        Potion potionX = new Potion(20, 60, null);
        assertFalse(wiz.overlap(wiz, potionX));

        // not overlapping - same y
        Potion potionY = new Potion(60, 20, null);
        assertFalse(wiz.overlap(wiz, potionY));
    }

    @Test
    // Test touchBoundary method with a specified directional mode.
    public void testTouchBoundaryMode() {
        CollidableObj wiz = new Wizard(20, 20, null);
        List<Slime> slimes = new ArrayList<Slime>();
        slimes.add(new Slime(0, 20, 0, null));
        assertTrue(wiz.touchBoundary(wiz, slimes, "left"));

        slimes.add(new Slime(40, 20, 0, null));
        assertTrue(wiz.touchBoundary(wiz, slimes, "right"));

        slimes.add(new Slime(20, 0, 0, null));
        assertTrue(wiz.touchBoundary(wiz, slimes, "up"));

        slimes.add(new Slime(20, 40, 0, null));
        assertTrue(wiz.touchBoundary(wiz, slimes, "down"));

        slimes.add(new Slime(0, 0, 0, null));
        slimes.add(new Slime(0, 40, 0, null));
        slimes.add(new Slime(40, 0, 0, null));
        slimes.add(new Slime(40, 40, 0, null));

        assertFalse(wiz.touchBoundary(wiz, slimes, "invalid"));
    }

    @Test
    // Test touchBoundary method with no specified directional mode.
    public void testTouchBoundary() {
        CollidableObj wiz = new Wizard(20, 20, null);

        List<Slime> slimes = new ArrayList<Slime>();
        // left
        slimes.add(new Slime(0, 20, 0, null));
        assertTrue(wiz.touchBoundary(wiz, slimes));

        // right
        slimes = new ArrayList<Slime>();
        slimes.add(new Slime(40, 20, 0, null));
        assertTrue(wiz.touchBoundary(wiz, slimes));

        // up
        slimes = new ArrayList<Slime>();
        slimes.add(new Slime(20, 0, 0, null));
        assertTrue(wiz.touchBoundary(wiz, slimes));

        // down
        slimes = new ArrayList<Slime>();
        slimes.add(new Slime(20, 40, 0, null));
        assertTrue(wiz.touchBoundary(wiz, slimes));

        slimes.add(new Slime(20, 40, 0, null));
        slimes.add(new Slime(40, 20, 0, null));
        slimes.add(new Slime(0, 20, 0, null));
        slimes.add(new Slime(20, 0, 0, null));
        assertTrue(wiz.touchBoundary(wiz, slimes));

        // no boundary-touching objects
        CollidableObj newWiz = new Wizard(50, 50, null);
        List<Stone> stoneTiles = new ArrayList<Stone>();
        stoneTiles.add(new Stone(0, 0, null));
        stoneTiles.add(new Stone(0, 100, null));
        stoneTiles.add(new Stone(100, 100, null));
        stoneTiles.add(new Stone(0, 100, null));
        assertFalse(newWiz.touchBoundary(newWiz, stoneTiles));

        // empty target list
        List<Stone> emptyTile = new ArrayList<Stone>();
        assertFalse(newWiz.touchBoundary(newWiz, emptyTile));
  
    }
}
