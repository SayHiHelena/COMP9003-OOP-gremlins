package gremlins;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.event.KeyEvent;

public class WizardTest {

    @Test
    // Test Wizard teleport method.
    public void testTeleport() {
        App app = new App();
        Wizard wiz = new Wizard(20, 20, null);
        wiz.teleport(100,100, app);
        assert(wiz.getX() == 100 && wiz.getY() == 100);
        assertEquals(false, wiz.getTeleportToggle());
    }

    @Test
    // Test set all move toggle method.
    public void testSetAllMoveToggle() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();
        wiz.setAllMoveToggle(true);
        assertTrue(wiz.getLeftToggle());
        assertTrue(wiz.getRightToggle());
        assertTrue(wiz.getUpToggle());
        assertTrue(wiz.getDownToggle());
        assertTrue(wiz.getToLeftTile());
        assertTrue(wiz.getToRightTile());
        assertTrue(wiz.getToUpTile());
        assertTrue(wiz.getToDownTile());
    }

    @Test
    // Test key press to move to available tile on the left.
    public void testSetMoveToggleLeft() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(60, 20, null));
        Wizard wiz = app.getWiz();

        // press left key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        wiz.logic(app);
        assertTrue(wiz.getLeftToggle());
        assertEquals(wiz.getMoveHistory().get(1), 37);
        // keep pressing left key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        assertTrue(wiz.getLeftToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 56 && wiz.getY() == 20);

        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        assertEquals(app.wizLeft, wiz.getImg());

        // release left key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        assertFalse(wiz.getLeftToggle());
        assertTrue(wiz.getToLeftTile());
        assertFalse(wiz.getToRightTile());
        wiz.logic(app);
        assert(wiz.getX() == 54 && wiz.getY() == 20);

        // go to the next tile and turn off the toLeftTile toggle
        for(int i = 0; i < 8; i++) {
            wiz.logic(app);
        }
        assert(wiz.getX() == 40 && wiz.getY() == 20);
        assertFalse(wiz.getToLeftTile());
        
    }

    @Test
    // Test key press to move to the left tile while having a stone tile on the left.
    public void testTouchStoneLeft() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();

        // press left key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        assertTrue(wiz.getLeftToggle());
        assertEquals(wiz.getMoveHistory().get(1), 37);
        // keep pressing left key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        assertTrue(wiz.getLeftToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 20);

        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        assertEquals(app.wizLeft, wiz.getImg());

        // release left key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        assertFalse(wiz.getLeftToggle());
        assertTrue(wiz.getToLeftTile());
        assertFalse(wiz.getToRightTile());
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 20);
    }

    @Test
    // Test key press to move to the left tile while having a brick tile on the left.
    public void testTouchBrickLeft() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(60, 40, null));
        Wizard wiz = app.getWiz();

        // press left key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        assertTrue(wiz.getLeftToggle());
        assertEquals(wiz.getMoveHistory().get(1), 37);
        // keep pressing left key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        assertTrue(wiz.getLeftToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 60 && wiz.getY() == 40);
        
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        assertEquals(app.wizLeft, wiz.getImg());

        // release left key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        assertFalse(wiz.getLeftToggle());
        assertTrue(wiz.getToLeftTile());
        assertFalse(wiz.getToRightTile());
        wiz.logic(app);
        assert(wiz.getX() == 60 && wiz.getY() == 40);
    }

    @Test
    // Test key press to move to the right tile.
    public void testSetMoveToggleRight() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();

        // press right key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        assertTrue(wiz.getRightToggle());
        assertEquals(wiz.getMoveHistory().get(1), 39);
        // keep pressing right key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        assertTrue(wiz.getRightToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 22 && wiz.getY() == 20);
        assertEquals(app.wizRight, wiz.getImg());

        // release right key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        assertFalse(wiz.getRightToggle());
        assertFalse(wiz.getToLeftTile());
        assertTrue(wiz.getToRightTile());
        wiz.logic(app);
        assert(wiz.getX() == 24 && wiz.getY() == 20);

        // go to the next tile and turn off the toRightTile toggle
        for(int i = 0; i < 9; i++) {
            wiz.logic(app);
        }
        assert(wiz.getX() == 40 && wiz.getY() == 20);
        assertFalse(wiz.getToRightTile());
    }

    @Test
    // Test key press to move to the right tile while having a stone tile on the right.
    public void testTouchStoneRight() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(680, 20, null));
        Wizard wiz = app.getWiz();

        // press right key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        assertTrue(wiz.getRightToggle());
        assertEquals(wiz.getMoveHistory().get(1), 39);
        // keep pressing right key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        assertTrue(wiz.getRightToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 680 && wiz.getY() == 20);
        assertEquals(app.wizRight, wiz.getImg());

        // release right key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        assertFalse(wiz.getRightToggle());
        assertFalse(wiz.getToLeftTile());
        assertTrue(wiz.getToRightTile());
        wiz.logic(app);
        assert(wiz.getX() == 680 && wiz.getY() == 20);
        
    }

    @Test
    // Test key press to move to the right tile while having a brick tile on the right.
    public void testTouchBrickRight() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 60, null));
        Wizard wiz = app.getWiz();

        // press right key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        assertTrue(wiz.getRightToggle());
        assertEquals(wiz.getMoveHistory().get(1), 39);
        // keep pressing right key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        assertTrue(wiz.getRightToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 60);
        assertEquals(app.wizRight, wiz.getImg());

        // release right key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        assertFalse(wiz.getRightToggle());
        assertFalse(wiz.getToLeftTile());
        assertTrue(wiz.getToRightTile());
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 60);
        
    }

    @Test
    // Test key press to move to available tile towards up side.
    public void testSetMoveToggleUp() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 60, null));
        Wizard wiz = app.getWiz();

        // press up key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        assertTrue(wiz.getUpToggle());
        assertEquals(wiz.getMoveHistory().get(1), 38);
        // keep pressing up key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        assertTrue(wiz.getUpToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 58);
        
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        assertEquals(app.wizUp, wiz.getImg());

        // release up key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        assertFalse(wiz.getUpToggle());
        assertTrue(wiz.getToUpTile());
        assertFalse(wiz.getToDownTile());
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 56);

        // go to the next tile and turn off the toUpTile toggle
        for(int i = 0; i < 9; i++) {
            wiz.logic(app);
        }
        assert(wiz.getX() == 20 && wiz.getY() == 40);
        assertFalse(wiz.getToUpTile());
    }

    @Test
    // Test key press to move to the up tile while having a stone tile on top.
    public void testTouchStoneUp() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();

        // press up key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        assertTrue(wiz.getUpToggle());
        assertEquals(wiz.getMoveHistory().get(1), 38);
        // keep pressing up key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        assertTrue(wiz.getUpToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 20);

        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        assertEquals(app.wizUp, wiz.getImg());

        // release up key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        assertFalse(wiz.getUpToggle());
        assertTrue(wiz.getToUpTile());
        assertFalse(wiz.getToDownTile());
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 20);
    }

    @Test
    // Test key press to move to the up tile while having a brick tile on top.
    public void testTouchBrickUp() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(100, 40, null));
        Wizard wiz = app.getWiz();

        // press up key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        assertTrue(wiz.getUpToggle());
        assertEquals(wiz.getMoveHistory().get(1), 38);
        // keep pressing up key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        assertTrue(wiz.getUpToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 100 && wiz.getY() == 40);

        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        assertEquals(app.wizUp, wiz.getImg());

        // release up key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        assertFalse(wiz.getUpToggle());
        assertTrue(wiz.getToUpTile());
        assertFalse(wiz.getToDownTile());
        wiz.logic(app);
        assert(wiz.getX() == 100 && wiz.getY() == 40);
    }

    @Test
    // Test key press to move to available tile towards down side.
    public void testSetMoveToggleDown() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();

        // press down key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        assertTrue(wiz.getDownToggle());
        assertEquals(wiz.getMoveHistory().get(1), 40);
        // keep pressing down key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        assertTrue(wiz.getDownToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 22);

        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        assertEquals(app.wizDown, wiz.getImg());

        // release down key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        assertFalse(wiz.getDownToggle());
        assertFalse(wiz.getToUpTile());
        assertTrue(wiz.getToDownTile());
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 24);

        // go to the next tile and turn off the toDownTile toggle
        for(int i = 0; i < 9; i++) {
            wiz.logic(app);
        }
        assert(wiz.getX() == 20 && wiz.getY() == 40);
        assertFalse(wiz.getToDownTile());
    }

    @Test
    // Test key press to move to the down tile while having a stone tile under.
    public void testTouchStoneDown() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 620, null));
        Wizard wiz = app.getWiz();

        // press down key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        assertTrue(wiz.getDownToggle());
        assertEquals(wiz.getMoveHistory().get(1), 40);
        // keep pressing down key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        assertTrue(wiz.getDownToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 620);

        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        assertEquals(app.wizDown, wiz.getImg());

        // release down key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        assertFalse(wiz.getDownToggle());
        assertFalse(wiz.getToUpTile());
        assertTrue(wiz.getToDownTile());
        wiz.logic(app);
        assert(wiz.getX() == 20 && wiz.getY() == 620);
    }

    @Test
    // Test key press to move to the down tile while having a brick tile under.
    public void testTouchBrickDown() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(40, 20, null));
        Wizard wiz = app.getWiz();

        // press down key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        assertTrue(wiz.getDownToggle());
        assertEquals(wiz.getMoveHistory().get(1), 40);
        // keep pressing down key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        assertTrue(wiz.getDownToggle());
        assertEquals(wiz.getMoveHistory().get(0), 0);
        wiz.logic(app);
        assert(wiz.getX() == 40 && wiz.getY() == 20);

        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        assertEquals(app.wizDown, wiz.getImg());
        
        // release down key
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        assertFalse(wiz.getDownToggle());
        assertFalse(wiz.getToUpTile());
        assertTrue(wiz.getToDownTile());
        wiz.logic(app);
        assert(wiz.getX() == 40 && wiz.getY() == 20);
    }

    @Test
    // Test invalid input for setMoveToggle method.
    public void testSetMoveToggleInvalid() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();

        // press invalid key
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 20));
        assertFalse(wiz.getLeftToggle());
        assertFalse(wiz.getRightToggle());
        assertFalse(wiz.getUpToggle());
        assertFalse(wiz.getDownToggle());
        assert(wiz.getX() == 20 && wiz.getY() == 20);
    }

    @Test
    // Test Wizard if fire method can turn off fire toggle.
    public void testFire() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();
        assertEquals(true, wiz.getFireToggle()); 
        wiz.fire(app);
        assertEquals(false, wiz.getFireToggle()); 
    }

    @Test
    // Test fire countdown method after firing.
    public void testFireCooldownFrame() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();

        // press space to fire
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 32));
        assertFalse(wiz.getFireToggle());
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 32));
        for(int i = 0; i <= app.getWizCooldown() * App.FPS; i++) {
            wiz.logic(app);
        }
        assertTrue(wiz.getFireToggle());
        // wait till the cooldown is finished and to fire again
        wiz.fire(app);
        assertEquals(0, wiz.getCooldown());

    }
    
    @Test
    // Test getLivesNum method returning current Wizard object's number of lives
    // and wizard object lose a life when collide with a gremlin or slime.
    public void testGetLivesNum() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        assertEquals(2, app.getNumOfLevels());
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();
        wiz.setNumOfLives(app.getNumOfLevels());
        assertEquals(2, wiz.getLivesNum());
        
        // gremlin located at (20, 200)
        assertEquals(2, wiz.getLivesNum());
        wiz.teleport(20, 182);
        wiz.logic(app);
        assertEquals(1, wiz.getLivesNum());
        wiz.logic(app);
        wiz.teleport(20, 182);
        wiz.logic(app);
        assertEquals(0, wiz.getLivesNum());
        wiz.logic(app);

    }
    
    @Test
    // Test changing level or game state when a Wizard object overlap with a Exit object.
    public void testOverlapExit() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();

        // exit located at (620, 620)
        assertEquals(1, app.getCurrentLevel());
        wiz.teleport(620, 620);
        wiz.logic(app);
        assertEquals(2, app.getCurrentLevel());
        wiz.teleport(620, 620);
        wiz.logic(app);
        assertEquals(0, app.getGameState());
    }

    @Test
    // Test teleport countdown when a Wizard object overlap with a Portal object
    // and the wizard can only teleport once withou leaving the portal.
    public void testCollidePortal() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();

        // portal located at (80, 40)
        assertFalse(wiz.getTeleportToggle());
        wiz.teleport(80, 40);
        wiz.logic(app);
        assertTrue(wiz.getTeleportToggle());
        assertEquals(1.0, wiz.getTeleportCount());
        // teleported after the countdown
        for(int i = 0; i <= Portal.COUNTDOWN; i++) {
            wiz.logic(app);
        }
        assertNotEquals(80, wiz.getX());
        assertNotEquals(40, wiz.getY());
        assertTrue(wiz.getTeleportToggle());

        // touching boundary
        wiz.teleport(60, 40);
        wiz.logic(app);
        assertFalse(wiz.getTeleportToggle());
        assertEquals(0, wiz.getTeleportCount());
       
    }

    @Test
    // Test freeze toggle and countdown when a Wizard object overlap with a Potion object.
    public void testCollidePotion() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Wizard wiz = app.getWiz();
        Background bg = app.getBg();

        for(int i = 0; i <= 8.0 * App.FPS; i++) {
            bg.logic(app);
        }
        assertNotNull(bg.getPotion());

        int potionX = bg.getPotion().get(0).getX();
        int potionY = bg.getPotion().get(0).getY();

        // when the wiz & freeze potion collide
        wiz.teleport(potionX, potionY);
        wiz.logic(app);
        assertEquals(Potion.LASTING * App.FPS, bg.getFreezeCount());
        assertTrue(bg.getFreezeToggle());
        assertNotNull(bg.getPotion());
        assertEquals(0, bg.getPotion().size());

        //freezed
        bg.logic(app);
        for(Gremlin g: bg.getGremlins()) {
            assertEquals(0, g.getSpeed());
        }
        for(Slime s: bg.getSlimes()) {
            assertEquals(0, s.getSpeed());
        }

        // freeze countdown
        for(int i = 0; i <= Potion.LASTING * App.FPS; i++) {
            bg.logic(app);
        }
        assertEquals(Potion.LASTING * App.FPS, bg.getFreezeCount());
        assertFalse(bg.getFreezeToggle());

       
    }
}
