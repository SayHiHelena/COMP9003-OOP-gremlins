package gremlins;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class PotionTest {

    @Test
    // Test Potion constructer.
    public void testPotionConstructor() {
        Potion p = new Potion(20, 20, null);
        assertNotNull(p);
    }
}
