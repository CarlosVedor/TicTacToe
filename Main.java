package kraken.learnprogramming;

import java.util.Scanner;

public class Main {

    private static final char PLAYER_1 = 'X';
    private static final char PLAYER_2 = 'O';


    private static Scanner scanner = new Scanner(System.in);
    private static char[][] board = new char[3][3];
    private static int currentPlayerXCoordinate;
    private static int currentPlayerYCoordinate;


    public static void main(String[] args) {
        System.out.println("--- Tic Tac Toe ---\n");
        initializeBoard();
        printBoard();

        boolean gameEnded = false;
        boolean player1IsPlaying = true;
        int countNrPlays = 0;
        while (!gameEnded){
            boolean validPlayerMove = false;
            do {
                currentPlayerXCoordinate = userInputForCoordinate("X", player1IsPlaying);
                currentPlayerYCoordinate = userInputForCoordinate("Y", player1IsPlaying);
                if (board[currentPlayerXCoordinate][currentPlayerYCoordinate] == ' '){
                    validPlayerMove = true;
                } else {
                    System.out.println("The coordinate ("+currentPlayerXCoordinate+
                            ","+currentPlayerYCoordinate+") is already filled in");
                }
            } while (!validPlayerMove);

            //the player's move gets registered in the board
            board[currentPlayerXCoordinate][currentPlayerYCoordinate] = player1IsPlaying ? PLAYER_1 : PLAYER_2;
            countNrPlays++;
            printBoard(); //show updated board

            if (countNrPlays >= 5) { //game can only be won if there are at least 3 plays done by a player
                gameEnded = checkBoardForPlayerVictory(player1IsPlaying);
                if (gameEnded){
                    String currentPlayer = player1IsPlaying ? "Player 1" : "Player 2";
                    System.out.println(currentPlayer + " has won the game!");
                }
            }
            player1IsPlaying = !player1IsPlaying;

            if (!gameEnded && countNrPlays == 9) { //board is completely full, no more plays can be done
                gameEnded = true;
                System.out.println("The game ended in a draw.");
            }
        }

    }

    //player wins with 3 of his symbol aligned horizontally, vertically or diagonally
    private static boolean checkBoardForPlayerVictory(boolean player1IsPlaying) {
        int symbolsAligned = 0;
        char symbolToCheck = player1IsPlaying ? PLAYER_1 : PLAYER_2;

        //check horizontally
        for (int cols = 0; cols < 3; cols++) {
            char checkingSymbol = board[cols][currentPlayerYCoordinate];
            if (checkingSymbol == symbolToCheck){
                symbolsAligned++;
            }
        }
        if (symbolsAligned == 3) return true; //3 aligned horizontally

        symbolsAligned = 0; //reset count to check vertically
        //check vertically
        for (int rows = 0; rows < 3; rows++) {
            char checkingSymbol = board[currentPlayerXCoordinate][rows];
            if (checkingSymbol == symbolToCheck){
                symbolsAligned++;
            }
        }
        if (symbolsAligned == 3) return true; //3 aligned vertically

        //check diagonally, (1,1) needs to check both diagonals
        //if current coordinate between (0,2) and (2,0) --> /
        if ((currentPlayerXCoordinate == 0 && currentPlayerYCoordinate == 2) ||
                (currentPlayerXCoordinate == 2 && currentPlayerYCoordinate == 0) ||
                (currentPlayerXCoordinate == 1 && currentPlayerYCoordinate == 1)){
            if(board[2][0] == board[1][1] && board[1][1] == board[0][2]){
                return true; //3 aligned diagonally
            }
        }
        //if current coordinate between (0,0) and (2,2) --> \
        if (currentPlayerXCoordinate == currentPlayerYCoordinate){
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2]){
                return true; //3 aligned diagonally
            }
        }

        return false;
    }

    private static int userInputForCoordinate(String coordinateName, boolean player1IsPlaying){
        String currentPlayer = player1IsPlaying ? "Player 1" : "Player 2";
        while(true) {
            System.out.println(currentPlayer + " please insert coordinate " + coordinateName + ": ");
            if (scanner.hasNextInt()){
                int userInput = scanner.nextInt();
                scanner.nextLine();
                if (userInput < 0 || userInput > 2) {
                    System.out.println("Input must be between 0 and 2 (inclusive)");
                } else {
                    return userInput;
                }
            } else {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }
    }

    private static void initializeBoard() {
        for (int rows = 0; rows < 3; rows++) {
            for (int cols = 0; cols < 3; cols++) {
                board[cols][rows] = ' ';
            }
        }
    }

    private static void printBoard(){
        for (int rows = 0; rows < 3; rows++) {
            for (int cols = 0; cols < 3; cols++) {
                System.out.print(" " + board[cols][rows] + " ");
                if (cols != 2) System.out.print("|");
            }
            System.out.println();
            if (rows != 2) System.out.println("-----------");
        }
    }
}
