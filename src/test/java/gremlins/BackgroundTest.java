package gremlins;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class BackgroundTest {

    @Test
    // Test brick destroying process
    public void testBrickDestroy() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Background bg = app.getBg();

        // create the test Fireball f
        Fireball f = new Fireball(62, 20, 0, null);
        bg.getFireballs().add(f);

        int brickNum = bg.getBrickTiles().size();

        // frame++ to test brick destroying process
        for(int i = 0; i < 18; i++) {
            bg.logic(app);
            if(i == 0) {
                for(Brick b: bg.getBrickTiles()) {
                    if(b.collide(b,f))
                        assertEquals(app.brickDestroy0, b.getImg());
                }
            }
            else if(i == 4) {
                for(Brick b: bg.getBrickTiles()) {
                    if(b.collide(b,f))
                        assertEquals(app.brickDestroy1, b.getImg());
                }
            }
            else if(i == 8) {
                for(Brick b: bg.getBrickTiles()) {
                    if(b.collide(b,f))
                        assertEquals(app.brickDestroy1, b.getImg());
                }
            }
            else if(i == 12) {
                for(Brick b: bg.getBrickTiles()) {
                    if(b.collide(b,f))
                        assertEquals(app.brickDestroy1, b.getImg());
                }
            }
            else if(i == 16) {
                assertEquals(brickNum - 1, bg.getBrickTiles().size());
            }
        }
        
    }

    @Test
    // Test when a fireball collides with a stone
    public void testFireballCollideStonewall() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(680, 20, null));
        Wizard wiz = app.getWiz();
        Background bg = app.getBg();

        wiz.fire(app);

        Fireball f = bg.getFireballs().get(0);
        assertEquals(680, f.getX());
        f.logic();
        assertEquals(684, f.getX());
        bg.logic(app);

        assertEquals(0, bg.getFireballs().size());
    }

    @Test
    // Test when a fireball collides with a gremlin
    public void testFireballCollideGremlin() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        Background bg = app.getBg();

        // create the test Fireball f
        Fireball f = new Fireball(42, 20, 0, null);
        bg.getFireballs().add(f);

        // create the test Gremlin g
        Gremlin g = new Gremlin(60, 20, null);
        bg.getGremlins().add(g);

        // fireball disappear and the gremlin respawn
        bg.logic(app);
        assertEquals(0, bg.getFireballs().size());
        assert(g.getX() != 60 || g.getY() != 20);

    }

    @Test
    // Test when a fireball collides with a slime
    public void testFireballCollideSlime() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        Background bg = app.getBg();

        // create the test Fireball f
        Fireball f = new Fireball(42, 20, 0, null);
        bg.getFireballs().add(f);

        // create the test Slime s
        Slime s = new Slime(60, 20, 0, null);
        bg.getSlimes().add(s);

        // both disappear
        bg.logic(app);
        assertEquals(0, bg.getFireballs().size());
        assertEquals(0, bg.getSlimes().size());
    }

    @Test
    // Test when a slime collides with a stone
    public void testSlimeCollideStonewall() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        Background bg = app.getBg();

        // create the test Slime s
        Slime s = new Slime(60, 18, 0, null);
        bg.getSlimes().add(s);

        // create the test Stone st
        Stone st = new Stone(60, 0, null);
        bg.getStoneTiles().add(st);

        // slime disappear
        bg.logic(app);
        assertEquals(0, bg.getSlimes().size());

    }

    @Test
    // Test when a slime collides with a brickwall
    public void testSlimeCollideBrickwall() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        Background bg = app.getBg();

        // create the test Slime s
        Slime s = new Slime(62, 20, 0, null);
        bg.getSlimes().add(s);

        // create the test Brick b
        Brick b = new Brick(80, 20, null);
        bg.getBrickTiles().add(b);

        // slime disappear
        bg.logic(app);
        assertEquals(0, bg.getSlimes().size());

    }
}