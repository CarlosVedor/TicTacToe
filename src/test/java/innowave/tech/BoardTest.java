package innowave.tech;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board3x3;
    private Board board5x6;

    private Player player1;
    private static final String NAME1 = "John";
    private static final char SYMBOL1 = 'X';
    private Player player2;
    private static final String NAME2 = "Jessica";
    private static final char SYMBOL2 = 'O';
    private Player player3;
    private static final String NAME3 = "Jimmy";
    private static final char SYMBOL3 = '!';

    @Before
    public void setup() {
        board3x3 = new Board(3,3);
        board5x6 = new Board(5,6);
        player1 = new Player(NAME1, SYMBOL1);
        player2 = new Player(NAME2, SYMBOL2);
        player3 = new Player(NAME3, SYMBOL3);
    }

    @Test
    public void getRowCount() {
        assertEquals("Board rows not being correctly initialized", 3, board3x3.getRowCount());
        assertEquals("Board rows not being correctly initialized", 6, board5x6.getRowCount());
    }

    @Test
    public void getColCount() {
        assertEquals("Board columns not being correctly initialized", 3, board3x3.getColCount());
        assertEquals("Board columns not being correctly initialized", 5, board5x6.getColCount());
    }

    @Test
    public void getTotalMovesPlayed() {
        assertEquals("Initial value should be 0", 0, board3x3.getTotalMovesPlayed());
    }

    @Test
    public void playMove() {
        //3x3 board
        assertFalse("Null player shouldn't return true",
                board3x3.playMove(null, 1,1));
        assertTrue("Player 1 should've been able to play in (1,1)",
                board3x3.playMove(player1, 1, 1));
        assertTrue("Player 2 should've been able to play in (2,1)",
                board3x3.playMove(player2, 2, 1));
        assertFalse("Player 1 should have NOT been able to play in (2,1)",
                board3x3.playMove(player1, 2, 1));
        assertFalse("Player 1 should have NOT been able to play in (1,1)",
                board3x3.playMove(player1, 1, 1));
        assertTrue("Player 3 should've been able to play in (0,0)",
                board3x3.playMove(player3, 0, 0));
        assertFalse("Player 1 made a move (3,1) out of boundaries in a 3x3 board",
                board3x3.playMove(player1, 3, 1));
        assertFalse("Player 1 made a move (0,3) out of boundaries in a 3x3 board",
                board3x3.playMove(player1, 0, 3));
        assertFalse("Player 1 made a move (0,-1) out of boundaries in a 3x3 board",
                board3x3.playMove(player1, 0, -1));
        assertFalse("Player 1 made a move (-2,2) out of boundaries in a 3x3 board",
                board3x3.playMove(player1, -2, 2));

        //3x3 board.toString()
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(" "+SYMBOL3+" |   |   \n");
        strBuilder.append("-----------\n");
        strBuilder.append("   | "+SYMBOL1+" | "+SYMBOL2+" \n");
        strBuilder.append("-----------\n");
        strBuilder.append("   |   |   \n");
        assertEquals("3x3 board representation is incorrect", strBuilder.toString(), board3x3.toString());

        //5x6 board
        assertFalse("Null player shouldn't return true",
                board5x6.playMove(null, 4,1));
        assertTrue("Player 1 should've been able to play in (4,1)",
                board5x6.playMove(player1, 4, 1));
        assertTrue("Player 2 should've been able to play in (4,5)",
                board5x6.playMove(player2, 4, 5));
        assertFalse("Player 1 should have NOT been able to play in (4,5)",
                board5x6.playMove(player1, 4, 5));
        assertFalse("Player 1 should have NOT been able to play in (4,1)",
                board5x6.playMove(player1, 4, 1));
        assertTrue("Player 3 should've been able to play in (0,0)",
                board5x6.playMove(player3, 0, 0));
        assertFalse("Player 1 made a move (5,5) out of boundaries in a 5x6 board",
                board5x6.playMove(player1, 5, 5));
        assertFalse("Player 1 made a move (3,6) out of boundaries in a 5x6 board",
                board5x6.playMove(player1, 3, 6));
        assertFalse("Player 1 made a move (0,-5) out of boundaries in a 5x6 board",
                board5x6.playMove(player1, 0, -5));
        assertFalse("Player 1 made a move (-2,2) out of boundaries in a 5x6 board",
                board5x6.playMove(player1, -2, 2));

        //5x6 board.toString()
        strBuilder = new StringBuilder();
        strBuilder.append(" "+SYMBOL3+" |   |   |   |   \n");
        strBuilder.append("-------------------\n");
        strBuilder.append("   |   |   |   | "+SYMBOL1+" \n");
        strBuilder.append("-------------------\n");
        strBuilder.append("   |   |   |   |   \n");
        strBuilder.append("-------------------\n");
        strBuilder.append("   |   |   |   |   \n");
        strBuilder.append("-------------------\n");
        strBuilder.append("   |   |   |   |   \n");
        strBuilder.append("-------------------\n");
        strBuilder.append("   |   |   |   | "+SYMBOL2+" \n");
        assertEquals("5x6 board representation is incorrect", strBuilder.toString(), board5x6.toString());
    }

    @Test
    public void checkPlayerVictory() {//TODO
        fail("Test has yet to be implemented");
    }
}