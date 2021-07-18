package innowave.tech;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class);
        logger.debug("Starting application...");
        Board board = new Board(3, 3);
        Game game = new Game(board);
        Scanner scanner = new Scanner(System.in);
        Player player1 = new Player("James", 'X');
        Player player2 = new Player("Jessy", 'O');

        game.addValidPlayer(player1);
        game.addValidPlayer(player2);

        System.out.println("--- Tic Tac Toe ---\n");
        System.out.println(board);

        while (game.isRunning()){
            if (game.getCurrentPlayer() != null) {
                do {
                    logger.debug("Asking the user for coordinates...");
                    System.out.println(game.getCurrentPlayer().getName() + ", insert coordinates in the format 'x,y': ");
                } while (!game.playMove(scanner.nextLine()));
            } else { //no currentPlayer means no players
                System.out.println("There are no players playing...");
                game.end();
            }

        }
    }
}
