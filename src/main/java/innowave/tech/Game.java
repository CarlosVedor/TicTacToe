package innowave.tech;

import java.util.ArrayList;

public class Game {
    private boolean isRunning;
    private Board board;
    private ArrayList<Player> players;
    private Player currentPlayer;

    /**
     * Constructor for class Game
     * @param board the board in which the game will be played
     */
    public Game(Board board) {
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    //endregion

    /**
     * Adds a valid player to the list of players in the game, unless the operation fails
     * @param newPlayer the new player to add
     * @return True if the operation was successful, false otherwise
     */
    public boolean addValidPlayer(Player newPlayer){
        String feedbackMsg;
        boolean operationSuccessful = false;
        if (newPlayer != null) {
            String playerName = newPlayer.getName();
            char playerSymbol = newPlayer.getSymbol();
            if (playerSymbol != Board.EMPTY_SYMBOL) {
                if (!findPlayer(playerName)) {
                    if (!symbolExists(newPlayer.getSymbol())) {
                        players.add(newPlayer);
                        if (players.size() == 1){ //first player, set as current player
                            currentPlayer = newPlayer;
                        }
                        feedbackMsg = "Player '" + playerName + "' added with symbol '" + playerSymbol + "'";
                        operationSuccessful = true;
                    } else {
                        feedbackMsg = "Game.addPlayer -> Symbol is already taken";
                    }
                } else {
                    feedbackMsg = "Game.addPlayer -> Player name already exists";
                }
            } else {
                feedbackMsg = "Game.addPlayer -> Symbol cannot be the same as the empty symbol";
            }
        } else {
            feedbackMsg = "Game.addPlayer -> Player cannot be null";
        }

        printFeedbackMsg(feedbackMsg);
        return operationSuccessful;
    }

    /**
     * Checks if there is already a player with the specified name
     * @param playerName the player's name
     * @return True if there is a player with the specified name, false otherwise
     */
    private boolean findPlayer(String playerName){
        return players.stream().anyMatch(p -> p.getName().equals(playerName));
    }

    /**
     * Checks if the symbol already exists from the list of players
     * @param symbol the symbol to check
     * @return True if there was a symbol found in the list of players, false otherwise
     */
    private boolean symbolExists(char symbol){
        return players.stream().anyMatch(p -> p.getSymbol() == symbol);
    }

    /**
     * Executes the next move of the game, for the current player and
     * the input specified
     * @param userInput the user input
     * @return True if the move was done successfully, false otherwise
     */
    public boolean playMove(String userInput) {
        if (!players.isEmpty() && isRunning) {
            int[] parsedUserInput = validXYFormat(userInput);
            boolean validPlayerMove = board.playMove(currentPlayer, parsedUserInput[0], parsedUserInput[1]);
            if (!validPlayerMove){ //invalid move, move is not performed
                return false;
            }

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

            //change currentPlayer to the following player
            nextPlayerSetup();

        } else { //no players, game ends
            end();
            return false;
        }

        return true;
    }

    /**
     * Sets up the game for the following player
     */
    private void nextPlayerSetup(){
        int indexOfNextPlayer = players.indexOf(currentPlayer) + 1;
        if (indexOfNextPlayer >= players.size()) { //loop around if it's the last player
            indexOfNextPlayer = 0;
        }

        currentPlayer = players.get(indexOfNextPlayer);
    }

    /**
     * Wraps up the game after a player has made a winning move
     */
    private void playerVictory() {
        if(currentPlayer != null)
           printFeedbackMsg(currentPlayer.getName() + " has won the game!");
        end();
    }

    /**
     * Wraps up the game after a draw has been reached
     */
    private void gameDraw() {
        printFeedbackMsg("The game ended in a draw.");
        end();
    }

    /**
     * Prints a feedback message in the console for the user to read
     * @param feedbackMsg the feedback message
     */
    private void printFeedbackMsg(String feedbackMsg){
        System.out.println(feedbackMsg);
    }

    /**
     * Returns a validated int[] in 'x,y' format from the input
     * @param input the input
     * @return The user input, unless invalid in which case returns the array [-1, -1]
     */
    private int[] validXYFormat(String input){
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
                }
            }
        }

        return result;
    }

    /**
     * Ends the game
     */
    public void end(){
        isRunning = false;
    }
}
