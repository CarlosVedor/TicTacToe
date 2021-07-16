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
        assertEquals("Initial value should be 0",
                0, board3x3.getTotalMovesPlayed());
        board3x3.playMove(player1, 1, 1);
        board3x3.playMove(player2, 2, 1);
        assertEquals("Two moves were made, totalMovesPlayed should be two",
                2, board3x3.getTotalMovesPlayed());
        board3x3.playMove(player1, 0,0);
        board3x3.playMove(player2, 0,0); //invalid move
        assertEquals("Last move was invalid, shouldn't count towards total",
                3, board3x3.getTotalMovesPlayed());
        board3x3.playMove(player3, 0,1);
        assertEquals("Four moves were made, totalMovesPlayed should be four",
                4, board3x3.getTotalMovesPlayed());

    }

    @Test
    public void playMove_3x3() {
        //3x3 board
        assertFalse("Null player shouldn't return true",
                board3x3.playMove(null, 1, 1));
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
    }

    @Test
    public void playMove_5x6() {
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
    }

    @Test
    public void toString_3x3(){
        playMove_3x3();
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(" "+SYMBOL3+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" \n");
        strBuilder.append("-----------\n");
        strBuilder.append(" "+Board.EMPTY_SYMBOL+" | "+SYMBOL1+" | "+SYMBOL2+" \n");
        strBuilder.append("-----------\n");
        strBuilder.append(" "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" \n");
        assertEquals("3x3 board representation is incorrect", strBuilder.toString(), board3x3.toString());
    }

    @Test
    public void toString_5x6(){
        playMove_5x6();
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(" "+SYMBOL3+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" \n");
        strBuilder.append("-------------------\n");
        strBuilder.append(" "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+SYMBOL1+" \n");
        strBuilder.append("-------------------\n");
        strBuilder.append(" "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" \n");
        strBuilder.append("-------------------\n");
        strBuilder.append(" "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" \n");
        strBuilder.append("-------------------\n");
        strBuilder.append(" "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" \n");
        strBuilder.append("-------------------\n");
        strBuilder.append(" "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+Board.EMPTY_SYMBOL+" | "+SYMBOL2+" \n");
        assertEquals("5x6 board representation is incorrect", strBuilder.toString(), board5x6.toString());
    }

    @Test
    public void checkPlayerVictory_Horizontal_3x3() {
        board3x3.playMove(player1, 1,1);
        board3x3.playMove(player2, 2,0);
        board3x3.playMove(player1, 1,2);
        board3x3.playMove(player2, 1,0);
        board3x3.playMove(player1, 2,1);
        assertFalse("Player 1 should have NOT won with the current board", board3x3.checkPlayerVictory(player1));
        board3x3.playMove(player2, 0,0); //winning move
        assertTrue("Player 2 should've won with the current board", board3x3.checkPlayerVictory(player2));
        assertFalse("Player 1 should have NOT won with the current board", board3x3.checkPlayerVictory(player1));
    }

    @Test
    public void checkPlayerVictory_Vertical_3x3() {
        board3x3.playMove(player1, 1,1);
        board3x3.playMove(player2, 2,0);
        board3x3.playMove(player1, 1,2);
        board3x3.playMove(player2, 0,0);
        board3x3.playMove(player1, 0,2);
        assertFalse("Player 1 should have NOT won with the current board", board3x3.checkPlayerVictory(player1));
        board3x3.playMove(player2, 2,2);
        assertFalse("Player 2 should have NOT won with the current board", board3x3.checkPlayerVictory(player2));
        board3x3.playMove(player1, 1,0); //winning move
        assertTrue("Player 1 should've won with the current board", board3x3.checkPlayerVictory(player1));
    }

    @Test
    public void checkPlayerVictory_DiagonalAsc_3x3() {//Diagonal ascending (bottom left to top right)
        board3x3.playMove(player1, 0,0);
        board3x3.playMove(player2, 2,0);
        board3x3.playMove(player1, 0,1);
        board3x3.playMove(player2, 0,2);
        board3x3.playMove(player1, 0,2); //invalid move
        assertFalse("Player 1 should have NOT won with the current board", board3x3.checkPlayerVictory(player1));
        board3x3.playMove(player1, 2,2);
        assertFalse("Player 1 should have NOT won with the current board", board3x3.checkPlayerVictory(player1));
        board3x3.playMove(player2, 1,1); //winning move
        assertTrue("Player 2 should've won with the current board", board3x3.checkPlayerVictory(player2));
    }

    @Test
    public void checkPlayerVictory_DiagonalDesc_3x3() {//Diagonal descending (top left to bottom right)
        board3x3.playMove(player1, 0,0);
        board3x3.playMove(player2, 2,0);
        board3x3.playMove(player1, 1,1);
        board3x3.playMove(player2, 0,2);
        board3x3.playMove(player1, 0,2); //invalid move
        assertFalse("Player 1 should have NOT won with the current board", board3x3.checkPlayerVictory(player1));
        board3x3.playMove(player1, 2,2); //winning move
        assertTrue("Player 1 should've won with the current board", board3x3.checkPlayerVictory(player1));
    }

    @Test
    public void checkPlayerVictory_Horizontal_5x6() {
        board5x6.playMove(player1, 1,5);
        board5x6.playMove(player2, 1,3);
        board5x6.playMove(player1, 0,5);
        board5x6.playMove(player2, 3,3);
        board5x6.playMove(player1, 4,5); //NOT a winning move, symbols must be directly neighbouring one another
        assertFalse("Player 1 should have NOT won with the current board, must be 3 directly neighbouring symbols",
                board5x6.checkPlayerVictory(player1));
        board5x6.playMove(player2, 2,3); //winning move
        assertTrue("Player 2 should've won with the current board",
                board5x6.checkPlayerVictory(player2));
    }

    @Test
    public void checkPlayerVictory_Vertical_5x6() {
        board5x6.playMove(player1, 4,5);
        board5x6.playMove(player2, 1,2);
        board5x6.playMove(player1, 4,2);
        board5x6.playMove(player2, 1,4);
        board5x6.playMove(player1, 4,4); //NOT a winning move, symbols must be directly neighbouring one another
        assertFalse("Player 1 should have NOT won with the current board, must be 3 directly neighbouring symbols",
                board5x6.checkPlayerVictory(player1));
        board5x6.playMove(player2, 1,3); //winning move
        assertTrue("Player 2 should've won with the current board",
                board5x6.checkPlayerVictory(player2));
    }

    @Test
    public void checkPlayerVictory_DiagonalAsc_5x6() {//Diagonal ascending (bottom left to top right)
        board5x6.playMove(player1, 1,4);
        board5x6.playMove(player2, 1,2);
        board5x6.playMove(player1, 2,3);
        board5x6.playMove(player2, 3,2);
        assertFalse("Player 2 should have NOT won with the current board, must be 3 of their symbols",
                board5x6.checkPlayerVictory(player2));
        board5x6.playMove(player1, 4,1); //NOT a winning move, symbols must be directly neighbouring one another
        assertFalse("Player 1 should have NOT won with the current board, must be 3 directly neighbouring symbols",
                board5x6.checkPlayerVictory(player1));
        board5x6.playMove(player2, 0,1);
        board5x6.playMove(player1, 0,0);
        board5x6.playMove(player2, 1,0); //NOT a winning move, must be 3 of the same symbols next to each other
        assertFalse("Player 2 should have NOT won with the current board, must be 3 directly neighbouring symbols",
                board5x6.checkPlayerVictory(player1));
        board5x6.playMove(player1, 0,5); //winning move
        assertTrue("Player 1 should've won with the current board",
                board5x6.checkPlayerVictory(player1));
    }

    @Test
    public void checkPlayerVictory_DiagonalDesc_5x6() {//Diagonal descending (top left to bottom right)
        board5x6.playMove(player1, 4,5);
        board5x6.playMove(player2, 3,4);
        board5x6.playMove(player1, 2,3);
        assertFalse("Player 1 should have NOT won with the current board, must be 3 of their symbols",
                board5x6.checkPlayerVictory(player1));
        board5x6.playMove(player2, 0,4);
        board5x6.playMove(player1, 1,2); //NOT a winning move, symbols must be directly neighbouring one another
        assertFalse("Player 1 should have NOT won with the current board, must be 3 directly neighbouring symbols",
                board5x6.checkPlayerVictory(player1));
        board5x6.playMove(player2, 1,5); //NOT a winning move, must be 3 of the same symbols next to each other
        assertFalse("Player 2 should have NOT won with the current board, must be 3 directly neighbouring symbols",
                board5x6.checkPlayerVictory(player1));
        board5x6.playMove(player1, 0,1); //winning move
        assertTrue("Player 1 should've won with the current board",
                board5x6.checkPlayerVictory(player1));
    }
}