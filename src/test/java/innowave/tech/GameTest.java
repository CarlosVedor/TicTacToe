package innowave.tech;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    private Game game3x3;
    private Game game5x6;

    private Player player1;
    private Player player2;
    private Player player3;

    @Before
    public void setup() {
        game3x3 = new Game(new Board(3,3));
        game5x6 = new Game(new Board(5,6));

        player1 = new Player("John", 'X');
        player2 = new Player("Jericho", 'O');
        player3 = new Player("Jesus", 'J');
    }

    @Test
    public void isRunning() {
        assertTrue("Should be initialized as true", game3x3.isRunning());
    }

    @Test
    public void getCurrentPlayer_3x3() {
        //3x3 board
        assertNull("Should be initialized as null", game3x3.getCurrentPlayer());
        game3x3.addValidPlayer(player1);
        assertEquals("Current player should be the first player added", player1, game3x3.getCurrentPlayer());
        game3x3.addValidPlayer(player2);
        assertEquals("Current player should still be the first player added", player1, game3x3.getCurrentPlayer());
        game3x3.playMove("1,1"); //player1 plays (1,1) -> currentPlayer changes to player2
        assertEquals("Current player should have changed to player 2", player2, game3x3.getCurrentPlayer());
        game3x3.playMove("2,1"); //player2 plays (2,1) -> currentPlayer changes back to player1
        assertEquals("Current player should have changed to player 1", player1, game3x3.getCurrentPlayer());
        game3x3.playMove("2,1"); //player1 plays (2,1) -> invalid play so the current player stays the same
        assertNotEquals("Current player should have remained as player 1", player2, game3x3.getCurrentPlayer());
        game3x3.playMove("2,2"); //player1 plays (2,2) -> currentPlayer changes to player2
        game3x3.playMove("3,1"); //player2 plays (3,1) -> out of bounds play is invalid so the current player stays the same
        assertNotEquals("Current player should have remained as player 2", player1, game3x3.getCurrentPlayer());
    }

    @Test
    public void getCurrentPlayer_5x6() {
        //5x6 board
        assertNull("Should be initialized as null", game5x6.getCurrentPlayer());
        game5x6.addValidPlayer(player1);
        assertEquals("Current player should be the first player added", player1, game5x6.getCurrentPlayer());
        game5x6.addValidPlayer(player2);
        game5x6.addValidPlayer(player3);
        assertEquals("Current player should still be the first player added", player1, game5x6.getCurrentPlayer());
        game5x6.playMove("1,1"); //player1 plays (1,1) -> currentPlayer changes to player2
        assertEquals("Current player should have changed to player 2", player2, game5x6.getCurrentPlayer());
        game5x6.playMove("2,1"); //player2 plays (2,1) -> currentPlayer changes to player3
        assertEquals("Current player should have changed to player 3", player3, game5x6.getCurrentPlayer());
        game5x6.playMove("2,1"); //player3 plays (2,1) -> invalid play so the current player stays the same
        assertEquals("Current player should have remained as player 3", player3, game5x6.getCurrentPlayer());
        game5x6.playMove("2,2"); //player3 plays (2,2) -> currentPlayer changes to player1
        assertEquals("Current player should have changed to player 1", player1, game5x6.getCurrentPlayer());
        game5x6.playMove("5,4"); //player1 plays (5,4) -> out of bounds play is invalid so the current player stays the same
        assertEquals("Current player should have remained as player 1", player1, game5x6.getCurrentPlayer());
    }

    @Test
    public void addValidPlayer_3x3() {
        //3x3 board
        Player repeatedNamePlayer = new Player(player1.getName(), '@');
        Player repeatedSymbolPlayer = new Player("Jung", player3.getSymbol());
        Player emptySymbolPlayer = new Player("Jenny", Board.EMPTY_SYMBOL);

        assertTrue("Should be able to add a new player",
                game3x3.addValidPlayer(player1));
        assertTrue("Should be able to add player with different name and symbol",
                game3x3.addValidPlayer(player2));
        assertTrue("Should be able to add more than two players",
                game3x3.addValidPlayer(player3));
        assertFalse("Should NOT be able to add player with repeated name",
                game3x3.addValidPlayer(repeatedNamePlayer));
        assertFalse("Should NOT be able to add player with repeated symbol",
                game3x3.addValidPlayer(repeatedSymbolPlayer));
        assertFalse("Should NOT be able to add player with empty symbol",
                game3x3.addValidPlayer(emptySymbolPlayer));
    }

    @Test
    public void addValidPlayer_5x6() {
        //5x6 board
        Player repeatedNamePlayer = new Player(player1.getName(), '@');
        Player repeatedSymbolPlayer = new Player("Jung", player3.getSymbol());
        Player emptySymbolPlayer = new Player("Jenny", Board.EMPTY_SYMBOL);

        assertTrue("Should be able to add a new player",
                game5x6.addValidPlayer(player1));
        assertTrue("Should be able to add player with different name and symbol",
                game5x6.addValidPlayer(player2));
        assertTrue("Should be able to add more than two players",
                game5x6.addValidPlayer(player3));
        assertFalse("Should NOT be able to add player with repeated name",
                game5x6.addValidPlayer(repeatedNamePlayer));
        assertFalse("Should NOT be able to add player with repeated symbol",
                game5x6.addValidPlayer(repeatedSymbolPlayer));
        assertFalse("Should NOT be able to add player with empty symbol",
                game5x6.addValidPlayer(emptySymbolPlayer));
    }

    @Test
    public void playMove_3x3(){
        Game gameWithNoPlayers = new Game(new Board(3,3));
        assertFalse("A game with no players should return false", gameWithNoPlayers.playMove("1,1"));

        game3x3.addValidPlayer(player1);
        game3x3.addValidPlayer(player2);
        assertFalse("Invalid input, empty string", game3x3.playMove(""));
        assertFalse("Invalid input, must have two coordinates", game3x3.playMove("2"));
        assertFalse("Invalid input, must be two numbers", game3x3.playMove("1,a"));
        assertFalse("Invalid input, null string", game3x3.playMove(null));
        assertFalse("Invalid input, space between comma and coordinate", game3x3.playMove("2, 3"));
        assertTrue("Input should be valid", game3x3.playMove("1,1")); //player1 plays (1,1)
        assertFalse("Should be invalid, coordinates are already filled in", game3x3.playMove("1,1"));
        assertFalse("Should be invalid, out of bounds coordinates", game3x3.playMove("3,2"));
        assertTrue("Input should be valid", game3x3.playMove("2,2")); //player2 plays (2,2)
        assertTrue("Input should be valid", game3x3.playMove("2,1")); //player1 plays (2,1)
        assertTrue("Input should be valid", game3x3.playMove("0,0")); //player2 plays (0,0)
        assertTrue("Input should be valid", game3x3.playMove("0,2")); //player1 plays (0,2)
        assertTrue("Input should be valid", game3x3.playMove("2,0")); //player2 plays (2,0)
        assertTrue("Input should be valid", game3x3.playMove("1,0")); //player1 plays (1,0)
        assertTrue("Input should be valid", game3x3.playMove("0,1")); //player2 plays (0,1)
        assertTrue("Input should be valid", game3x3.playMove("1,2")); //player1 plays (1,2), last move ends with draw
        assertFalse("Shouldn't be able to make more moves when game isn't running", game3x3.playMove("1,2"));

    }

    @Test
    public void playMove_5x6(){
        Game gameWithNoPlayers = new Game(new Board(5,6));
        assertFalse("A game with no players should return false", gameWithNoPlayers.playMove("1,1"));

        game5x6.addValidPlayer(player1);
        game5x6.addValidPlayer(player2);
        assertFalse("Invalid input, empty string", game5x6.playMove(""));
        assertFalse("Invalid input, must have two coordinates", game5x6.playMove("2"));
        assertFalse("Invalid input, must be two numbers", game5x6.playMove("1,a"));
        assertFalse("Invalid input, null string", game5x6.playMove(null));
        assertFalse("Invalid input, space between comma and coordinate", game5x6.playMove("2, 3"));
        assertTrue("Input should be valid", game5x6.playMove("1,1")); //player1 plays (1,1)
        assertFalse("Should be invalid, coordinates are already filled in", game5x6.playMove("1,1"));
        assertFalse("Should be invalid, out of bounds coordinates", game5x6.playMove("5,5"));
        assertTrue("Input should be valid", game5x6.playMove("4,5")); //player2 plays (4,5)
        assertTrue("Input should be valid", game5x6.playMove("4,4")); //player1 plays (4,4)
        assertTrue("Input should be valid", game5x6.playMove("3,4")); //player2 plays (3,4)
        assertTrue("Input should be valid", game5x6.playMove("2,2")); //player1 plays (2,2)
        assertTrue("Input should be valid", game5x6.playMove("2,3")); //player2 plays (2,3), winning move, game ends
        assertFalse("Shouldn't be able to make more moves when game isn't running", game5x6.playMove("1,2"));
    }
}