package innowave.tech;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Tic Tac Toe ---\n");
        Board board = new Board(3, 3);
        Game game = new Game(board);
        Scanner scanner = new Scanner(System.in);

        game.addValidPlayer("James", 'X');
        game.addValidPlayer("Jessy", 'O');

        System.out.println(board);

        while (game.isRunning()){
            if (game.getCurrentPlayer() != null) {
                System.out.println(game.getCurrentPlayer().getName() + ", insert coordinates in the format 'x,y': ");
                game.playMove(scanner.nextLine());
            } else { //no currentPlayer means no players
                System.out.println("There are no players playing...");
                game.end();
            }

        }
    }
}
