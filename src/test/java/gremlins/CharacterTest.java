package gremlins;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class CharacterTest {

    @Test
    // Test Wizard constructor
    public void testWizConstructor() {
        Character wiz = new Wizard(20, 20, null);
        assertNotNull(wiz);
    }

    @Test
    // Test Gremlin constructor
    public void testGremlinConstructor() {
        assertNotNull(new Gremlin(20, 20, null));
    }

    @Test
    // Test a character's moving methods
    public void testCharacterMove() {
        Character g = new Gremlin(20, 20, null);
        g.left();
        assert(g.getX() == 19);
        g.right();
        assert(g.getX() == 20);
        g.up();
        assert(g.getY() == 19);
        g.down();
        assert(g.getY() == 20);
    }

    @Test
    // Test a character's teleport method
    public void testCharacterTeleport() {
        Character g = new Gremlin(20, 20, null);
        g.teleport(600, 400);
        assert(g.getX() == 600);
        assert(g.getY() == 400);
    }

    @Test
    // Test set and get cooldown methods
    public void testCharacterCooldown() {
        Character g = new Gremlin(20, 20, null);
        assert(g.getCooldown() == 0);
        g.setCooldown(20.0);
        assert(g.getCooldown() == 20.0);
    }

    @Test
    // Test Gremlins respawn method
    public void testGremlinRespawn() {
        App app = new App();
        app.getLevelConf();
        app.loadGame(true);
        app.setWiz(new Wizard(20, 20, null));
        //Wizard wiz = app.getWiz();
        Background bg = app.getBg();

        for(Gremlin g: bg.getGremlins()) {
            int gremlinX = g.getX();
            int gremlinY = g.getY();
            g.respawn(app);
            assert(gremlinX != g.getX() || gremlinY != g.getY());
        }
    }
    
}
