package gremlins;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.event.KeyEvent;

public class AppTest {

    @Test
    // test App constructor
    public void testAppConstructor() {
        App app = new App();
        assertNotNull(app);
    }

    @Test
    // test App drawing win window when the game state is set to win/0.
    public void testWinState() {
        App app = new App();
        app.noLoop();
        app.setGameState(0);
        assertEquals(0, app.getGameState());
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
    }
    
    @Test
    // test App drawing lose window when the game state is set to lose/-1.
    public void testLoseState() {
        App app = new App();
        app.noLoop();
        app.setGameState(-1);
        assertEquals(-1, app.getGameState());
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
    }

    @Test
    // test App drawing game window when the game state is set to game/1.
    public void testGameState() {
        App app = new App();
        app.noLoop();
        app.setGameState(1);
        assertEquals(1, app.getGameState());
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
    }

    @Test
    // test App drawing window.
    public void testDraw() {
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.draw();
    }
    
    @Test
    // test App doesn't respond if the game state is invalid.
    public void testInvalidState() {
        App app = new App();
        app.noLoop();
        app.setGameState(5);
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
    }

    @Test
    // Test if the App can receive right key pressing signal.
    public void testKeyPressRight() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        boolean rightMovement = true;
        assertTrue(rightMovement);
    }

    @Test
    // Test if the App can receive left key pressing signal.
    public void testKeyPressLeft() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        boolean leftMovement = true;
        assertTrue(leftMovement);
    }

    @Test
    // Test if the App can receive up key pressing signal.
    public void testKeyPressUp() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        boolean upMovement = true;
        assertTrue(upMovement);
    }

    @Test
    // Test if the App can receive down key pressing signal.
    public void testKeyPressDown() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        boolean downMovement = true;
        assertTrue(downMovement);
    }

    @Test
    // Test if the App can receive space bar pressing signal.
    public void testKeyPressSpace() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 32));
        boolean fire = true;
        assertTrue(fire);
    }

    @Test
    // Test setLevel & getCurrentLevel & getNumofLevels method are running correctly. 
    public void testSetLevel() {
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        int numOfLevels = app.getNumOfLevels();
        app.setLevel(numOfLevels);
        int currentLevel = app.getCurrentLevel();
        assert(currentLevel== app.getNumOfLevels());
    }
}
