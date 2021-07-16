package innowave.tech;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    private Game game3x3;
    private Game game5x6;

    @Before
    public void setup() {
        game3x3 = new Game(new Board(3,3));
        game5x6 = new Game(new Board(5,6));
    }

    @Test
    public void isRunning() {
        assertTrue("Should be initialized as true", game3x3.isRunning());
    }

    @Test
    public void getCurrentPlayer() { //TODO
        fail("Test has yet to be implemented");
    }

    @Test
    public void addValidPlayer() {
        String playerName1 = "Fred";
        char playerSymbol1 = 'X';
        String playerName2 = "Joshua";
        char playerSymbol2 = '@';
        String playerName3 = "Erica";
        char playerSymbol3 = 'E';
        String playerName4 = "Dennis";
        char playerSymbol4 = 'O';

        //3x3 board
        assertTrue("Should be able to add a new player",
                game3x3.addValidPlayer(playerName1, playerSymbol1));
        assertTrue("Should be able to add player with different name and symbol",
                game3x3.addValidPlayer(playerName2, playerSymbol2));
        assertTrue("Should be able to add more than two players",
                game3x3.addValidPlayer(playerName3, playerSymbol3));
        assertFalse("Should NOT be able to add player with repeated name",
                game3x3.addValidPlayer(playerName2, playerSymbol4));
        assertFalse("Should NOT be able to add player with repeated symbol",
                game3x3.addValidPlayer(playerName4, playerSymbol1));

        //5x6 board
        assertTrue("Should be able to add a new player",
                game5x6.addValidPlayer(playerName1, playerSymbol1));
        assertTrue("Should be able to add player with different name and symbol",
                game5x6.addValidPlayer(playerName2, playerSymbol2));
        assertTrue("Should be able to add more than two players",
                game5x6.addValidPlayer(playerName3, playerSymbol3));
        assertFalse("Should NOT be able to add player with repeated name",
                game5x6.addValidPlayer(playerName2, playerSymbol4));
        assertFalse("Should NOT be able to add player with repeated symbol",
                game5x6.addValidPlayer(playerName4, playerSymbol1));
    }

    @Test
    public void playMove(){ //TODO
        fail("Test has yet to be implemented");
    }
}