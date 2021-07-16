package innowave.tech;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PlayerTest {

    private Player player;

    @Before
    public void setup() {
        player = new Player("John", 'X');
    }

    @Test
    public void getName() {
        assertEquals("John", player.getName());
    }

    @Test
    public void getSymbol() {
        assertEquals('X', player.getSymbol());
    }
}