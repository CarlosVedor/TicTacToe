package innowave.tech;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Tic Tac Toe ---\n");
        Board board = new Board(3, 3);
        System.out.println(board);
        Scanner scanner = new Scanner(System.in);

        board.addPlayer("James", 'X');
        board.addPlayer("Jessy", 'O');

        boolean gameEnded = false;
        while (!gameEnded){
            for (Player currentPlayer : board.getPlayers()) {
                boolean validPlayerMove = false;
                do {
                    System.out.println(currentPlayer.getName() + ", insert coordinates in the format 'x,y': ");
                    String[] userInput = scanner.nextLine().split(",");
                    if (userInput.length == 2){
                        try {
                            int userInputX = Integer.parseInt(userInput[0]);
                            int userInputY = Integer.parseInt(userInput[1]);
                            if (userInputX >= 0 && userInputX < board.getColCount() &&
                                    userInputY >= 0 && userInputY < board.getRowCount()) {
                                validPlayerMove = board.playMove(currentPlayer, userInputX, userInputY);
                            } else {
                                System.out.println("Input must be within board boundaries");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, could not convert coordinates to int");
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid input, must input 2 coordinates, in format 'x,y'");
                    }
                } while (!validPlayerMove);

                System.out.println(board); //show updated board

                //game can only be won if there are at least 3 plays done by a single player
                if (board.getTotalMovesPlayed() >= board.getPlayers().size() * 2 + 1) {
                    gameEnded = board.checkPlayerVictory(currentPlayer);
                    if (gameEnded){
                        System.out.println(currentPlayer.getName() + " has won the game!");
                        break; //exit for loop, because the game has ended
                    }
                }

                //if board is completely full, no more plays can be done
                if (board.getTotalMovesPlayed() == board.getRowCount() * board.getColCount()) {
                    gameEnded = true;
                    System.out.println("The game ended in a draw.");
                    break; //exit for loop, because the game has ended
                }
            }
        }
        scanner.close();
    }
}
