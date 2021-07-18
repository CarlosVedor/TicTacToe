package innowave.tech;

import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Game {
    private boolean isRunning;
    private Board board;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private static final Logger logger = Logger.getLogger(Game.class);

    /**
     * Constructor for class Game
     * @param board the board in which the game will be played
     */
    public Game(Board board) {
        logger.debug("Initializing " + this.getClass().getSimpleName() + " instance");
        this.isRunning = true;
        this.board = board;
        this.players = new ArrayList<>();
    }

    //region Getters
    public boolean isRunning() {
        return isRunning;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    //endregion

    /**
     * Adds a valid player to the list of players in the game, unless the operation fails
     * @param newPlayer the new player to add
     * @return True if the operation was successful, false otherwise
     */
    public boolean addValidPlayer(Player newPlayer){
        logger.debug("addValidPlayer -> Adding a new valid player");
        boolean operationSuccessful = false;
        if (newPlayer != null) {
            String playerName = newPlayer.getName();
            char playerSymbol = newPlayer.getSymbol();
            if (playerSymbol != Board.EMPTY_SYMBOL) {
                if (!findPlayer(playerName)) {
                    logger.debug("addValidPlayer -> No player found, proceeding");
                    if (!symbolExists(newPlayer.getSymbol())) {
                        logger.debug("addValidPlayer -> No symbol found, proceeding");
                        players.add(newPlayer);
                        if (players.size() == 1){ //first player, set as current player
                            logger.debug("addValidPlayer -> Setting player '" + newPlayer.getName() + "' as the current player");
                            currentPlayer = newPlayer;
                        }
                        logger.info("Player '" + playerName + "' added with symbol '" + playerSymbol + "'");
                        operationSuccessful = true;
                    } else {
                        logger.info("addValidPlayer -> Symbol is already taken");
                    }
                } else {
                    logger.info("addValidPlayer -> Player name already exists");
                }
            } else {
                logger.info("addValidPlayer -> Symbol cannot be the same as the empty symbol");
            }
        } else {
            logger.warn("addValidPlayer -> Player cannot be null");
        }

        return operationSuccessful;
    }

    /**
     * Checks if there is already a player with the specified name
     * @param playerName the player's name
     * @return True if there is a player with the specified name, false otherwise
     */
    private boolean findPlayer(String playerName){
        logger.debug("findPlayer -> Searching for a player with name " + playerName);
        return players.stream().anyMatch(p -> p.getName().equals(playerName));
    }

    /**
     * Checks if the symbol already exists from the list of players
     * @param symbol the symbol to check
     * @return True if there was a symbol found in the list of players, false otherwise
     */
    private boolean symbolExists(char symbol){
        logger.debug("symbolExists -> Checking if a player with symbol '" + symbol + "' exists");
        return players.stream().anyMatch(p -> p.getSymbol() == symbol);
    }

    /**
     * Executes the next move of the game, for the current player and
     * the input specified
     * @param userInput the user input
     * @return True if the move was done successfully, false otherwise
     */
    public boolean playMove(String userInput) {
        logger.debug("playMove -> Playing a move with input " + userInput);
        if (!players.isEmpty() && isRunning) {
            int[] parsedUserInput = validXYFormat(userInput);
            boolean validPlayerMove = board.playMove(currentPlayer, parsedUserInput[0], parsedUserInput[1]);
            if (!validPlayerMove){ //invalid move, move is not performed
                logger.info("Invalid move for input " + userInput);
                return false;
            }

            logger.debug("playMove -> Move played, printing updated board");
            System.out.println(board); //show updated board

            //game can only be won if there are at least 3 plays done by a single player
            if (board.getTotalMovesPlayed() >= players.size() * 2 + 1) {
                if (board.checkPlayerVictory(currentPlayer)) {
                    playerVictory();
                } else if (board.getTotalMovesPlayed() == board.getRowCount() * board.getColCount()) {
                    //if board is completely full, no more plays can be done
                    gameDraw();
                }
            }

            if (isRunning) { //change currentPlayer to the following player
                nextPlayerSetup();
            }

        } else { //no players, game ends
            logger.warn("playMove -> No players detected");
            end();
            return false;
        }

        return true;
    }

    /**
     * Sets up the game for the following player
     */
    private void nextPlayerSetup(){
        logger.debug("nextPlayerSetup -> Setting up game for the next player");
        int indexOfNextPlayer = players.indexOf(currentPlayer) + 1;
        if (indexOfNextPlayer >= players.size()) { //loop around if it's the last player
            indexOfNextPlayer = 0;
        }

        currentPlayer = players.get(indexOfNextPlayer);

        if (this.currentPlayer != null) {
            logger.debug("nextPlayerSetup -> Current player is now '" + this.currentPlayer.getName() + "'");
        } else {
            logger.warn("nextPlayerSetup -> Current player is null");
        }
    }

    /**
     * Wraps up the game after a player has made a winning move
     */
    private void playerVictory() {
        if (currentPlayer != null)
            logger.info(this.currentPlayer.getName() + " has won the game!");
        end();
    }

    /**
     * Wraps up the game after a draw has been reached
     */
    private void gameDraw() {
        logger.info("Draw.");
        end();
    }

    /**
     * Returns a validated int[] in 'x,y' format from the input
     * @param input the input
     * @return The user input, unless invalid in which case returns the array [-1, -1]
     */
    private int[] validXYFormat(String input){
        logger.debug("Validating input in x,y format");
        int[] result = {-1, -1}; //invalid to play moves
        if (input != null) {
            String[] splitCoordinates = input.split(",");
            if (splitCoordinates.length == 2){ //must detect only two coordinates
                try {
                    int userInputX = Integer.parseInt(splitCoordinates[0]);
                    int userInputY = Integer.parseInt(splitCoordinates[1]);
                    result[0] = userInputX;
                    result[1] = userInputY;
                } catch (NumberFormatException e) {
                    //could not parse input as int, invalid
                    logger.info("Input could not be converted to integer numbers. " + e.getMessage());
                }
            }
        }

        return result;
    }

    /**
     * Ends the game
     */
    public void end(){
        logger.info("Game ended");
        isRunning = false;
    }
}
