package gremlins;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ProjectileTest {

    @Test
    // Test Fireball constructer.
    public void testFireballConstructor() {
        Projectile fireball = new Fireball(20, 20, 38, null);
        assertNotNull(fireball);
    }

    @Test
    // Test Slime constructer.
    public void testSlimeConstructor() {
        assertNotNull(new Slime(20, 20, 38, null));
        
    }

    @Test
    // Test Projectile move logic.
    public void testProjectileMoveLogic() {
        // move to right logic with direction 0
        Projectile sRight0 = new Slime(20, 20, 0, null);
        assert(sRight0.direction == 0);
        sRight0.logic();
        assert(sRight0.getX() == 24);

        // move to right logic with direction 39
        Projectile sRight = new Slime(20, 20, 39, null);
        assert(sRight.direction == 39);
        sRight.logic();
        assert(sRight.getX() == 24);

        // move to left logic
        Projectile sLeft = new Slime(20, 20, 37, null);
        assert(sLeft.direction == 37);
        sLeft.logic();
        assert(sLeft.getX() == 16);

        // move up logic
        Projectile sUp = new Slime(20, 20, 38, null);
        assert(sUp.direction == 38);
        sUp.logic();
        assert(sUp.getY() == 16);

        // move down logic
        Projectile sDown = new Slime(20, 20, 40, null);
        assert(sDown.direction == 40);
        sDown.logic();
        assert(sDown.getY() == 24);

        // move logic with invalid direction input
        Projectile sInvalid = new Slime(20, 20, 20, null);
        sInvalid.logic();
        assert(sInvalid.getX() == 20 && sInvalid.getY() == 20);

    }

    @Test
    // Test freeze method on a Slime object.
    public void testSlimeFreeze() {
        Slime s = new Slime(20, 20, 38, null);
        s.freeze();
        s.right();
        assert(s.getX() == 20);
        s.unfreeze();
        s.right();
        assert(s.getX() == 24);
    }
}
