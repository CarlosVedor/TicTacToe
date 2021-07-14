package innowave.tech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final int rowCount;
    private final int colCount;

    private char[][] coordinates;
    private ArrayList<Player> players;
    private int totalMovesPlayed;
    private int latestX;
    private int latestY;

    /**
     * Constructor for Board class
     * @param rowCount number of rows the board must have
     * @param colCount number of columns the board must have
     */
    public Board(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.players = new ArrayList<>();
        this.coordinates = new char[colCount][rowCount];
        for (int rows = 0; rows < rowCount; rows++) {
            for (int cols = 0; cols < colCount; cols++) {
                this.coordinates[cols][rows] = ' ';
            }
        }

        this.totalMovesPlayed = 0;
    }

    //region Getters
    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getTotalMovesPlayed() {
        return totalMovesPlayed;
    }
    //endregion

    /**
     * Adds a player to the list of players in the board, unless the operation fails
     * @param playerName the name of the new player to add
     * @param symbol the symbol to represent the new player's moves
     * @return True if the operation was successful, false otherwise
     */
    public boolean addPlayer(String playerName, char symbol){
        boolean operationSuccessful = false;
        if (findPlayer(playerName) == null){
            if (!symbolExists(symbol)){
                players.add(new Player(playerName, symbol));
                operationSuccessful = true;
            } else {
                System.out.println("Board.addPlayer -> Symbol is already taken");
            }
        } else {
            System.out.println("Board.addPlayer -> Player name already exists");
        }

        return operationSuccessful;
    }

    /**
     * Checks if there is already a player with the specified name
     * @param playerName the player's name
     * @return True if there is a player with the specified name, false otherwise
     */
    private Player findPlayer(String playerName){
        for (Player player : players) {
            if (player.getName().equals(playerName)) return player;
        }
        return null;
    }

    /**
     * Checks if the symbol already exists from the list of players
     * @param symbol the symbol to check
     * @return True if there was a symbol found in the list of players, false otherwise
     */
    private boolean symbolExists(char symbol){
        for (Player player : players) {
            if (player.getSymbol() == symbol) return true;
        }
        return false;
    }

    /**
     * Executes the move done by the player, updating board variables, unless it is invalid in which case returns false
     * @param player the player making the move
     * @param x the x coordinate of the move
     * @param y the y coordinate of the move
     * @return Returns true if the move was successful, false otherwise
     */
    public boolean playMove(Player player, int x, int y){
        boolean operationSuccessful = false;
        if (player != null){
            if (validateMove(x, y)) {
                this.totalMovesPlayed++;
                this.coordinates[x][y] = player.getSymbol();
                this.latestX = x;
                this.latestY = y;
                operationSuccessful = true;
            } else {
                System.out.println("Board.playMove -> Coordinates ("+x+","+y+") aren't empty");
            }
        } else {
            System.out.println("Board.playMove -> Invalid player");
        }

        return operationSuccessful;
    }

    /**
     * Validates the coordinates for the move
     * @param x the x coordinate of the move
     * @param y the y coordinate of the move
     * @return True if the move is valid, false otherwise
     */
    private boolean validateMove(int x, int y) {
        return coordinates[x][y] == ' ';
    }

    /**
     * Checks if the player who just played has made the winning move
     * @param player The player
     * @return Returns true if the player has won the game in the latest move. Returns false otherwise.
     */
    public boolean checkPlayerVictory(Player player) {
        if (player != null){
            char checkSymbol = player.getSymbol();

            //check horizontally
            if (checkThreeInARow(checkSymbol, "horizontal")) return true;
            //check vertically
            if (checkThreeInARow(checkSymbol, "vertical")) return true;
            //check diagonally, (top left to bottom right)
            if (checkThreeInARow(checkSymbol, "diagonalDesc")) return true;
                //check diagonally, (bottom left to top right)
            else return checkThreeInARow(checkSymbol, "diagonalAsc");
        }

        return false;
    }

    /**
     * Checks if there are three matching symbols in the board in the specified directional axis
     * according to the neighbouring coordinates of the latest move that was played.
     * @param checkSymbol the symbol to look for in the last move's neighbouring coordinates
     * @param direction the directional axis, can be 'horizontal', 'vertical',
     *                  'diagonalAsc' (bottom left to top right) or 'diagonalDesc' (top left to bottom right)
     *
     * @return Returns true if there are three matching symbols in the neighbouring coordinates of the latest
     * move. Returns false otherwise.
     */
    private boolean checkThreeInARow(char checkSymbol, String direction){
        Map<String, Boolean> conditionsToCheck = new HashMap<>();
        switch (direction) {
            case "horizontal":
                conditionsToCheck.put("previousCoordinateBy1", latestX - 1 >= 0 &&
                        coordinates[latestX-1][latestY] == checkSymbol);
                conditionsToCheck.put("previousCoordinateBy2", latestX - 2 >= 0 &&
                        coordinates[latestX-2][latestY] == checkSymbol);
                conditionsToCheck.put("followingCoordinateBy1", latestX + 1 < colCount &&
                        coordinates[latestX+1][latestY] == checkSymbol);
                conditionsToCheck.put("followingCoordinateBy2", latestX + 2 < colCount &&
                        coordinates[latestX+2][latestY] == checkSymbol);
                break;
            case "vertical":
                conditionsToCheck.put("previousCoordinateBy1", latestY - 1 >= 0 &&
                        coordinates[latestX][latestY-1] == checkSymbol);
                conditionsToCheck.put("previousCoordinateBy2", latestY - 2 >= 0 &&
                        coordinates[latestX][latestY-2] == checkSymbol);
                conditionsToCheck.put("followingCoordinateBy1", latestY + 1 < rowCount &&
                        coordinates[latestX][latestY+1] == checkSymbol);
                conditionsToCheck.put("followingCoordinateBy2", latestY + 2 < rowCount &&
                        coordinates[latestX][latestY+2] == checkSymbol);
                break;
            case "diagonalDesc": //top left to bottom right
                conditionsToCheck.put("previousCoordinateBy1", latestX - 1 >= 0 && latestY - 1 >= 0 &&
                        coordinates[latestX-1][latestY-1] == checkSymbol);
                conditionsToCheck.put("previousCoordinateBy2", latestX - 2 >= 0 && latestY - 2 >= 0 &&
                        coordinates[latestX-2][latestY-2] == checkSymbol);
                conditionsToCheck.put("followingCoordinateBy1", latestX + 1 < colCount && latestY + 1 < rowCount &&
                        coordinates[latestX+1][latestY+1] == checkSymbol);
                conditionsToCheck.put("followingCoordinateBy2", latestX + 2 < colCount && latestY + 2 < rowCount &&
                        coordinates[latestX+2][latestY+2] == checkSymbol);
                break;
            case "diagonalAsc": //bottom left to top right
                conditionsToCheck.put("previousCoordinateBy1", latestX - 1 >= 0 && latestY + 1 < rowCount &&
                        coordinates[latestX-1][latestY+1] == checkSymbol);
                conditionsToCheck.put("previousCoordinateBy2", latestX - 2 >= 0 && latestY + 2 < rowCount &&
                        coordinates[latestX-2][latestY+2] == checkSymbol);
                conditionsToCheck.put("followingCoordinateBy1", latestX + 1 < colCount && latestY - 1 >= 0 &&
                        coordinates[latestX+1][latestY-1] == checkSymbol);
                conditionsToCheck.put("followingCoordinateBy2", latestX + 2 < colCount && latestY - 2 >= 0 &&
                        coordinates[latestX+2][latestY-2] == checkSymbol);
                break;
            default: //do nothing
                break;
        }

        if (!conditionsToCheck.isEmpty()) { //avoid bad call to method, return false in that case

            //At most, we only need to check between two positions before and after the latest move coordinates
            //since we check for 3 of the same symbols aligned in a particular direction

            //previous coordinates to the latest move coordinate
            if (conditionsToCheck.get("previousCoordinateBy1")) {
                if (conditionsToCheck.get("previousCoordinateBy2")) {
                    //3 aligned with the previous 2 coordinates
                    return true;

                } else { //possible that the following coordinate to the latest move matches symbol
                    //3 aligned with the previous and following coordinates
                    return conditionsToCheck.get("followingCoordinateBy1");
                }
            } else { //following coordinates to the latest move coordinate
                //3 aligned with the following 2 coordinates
                return conditionsToCheck.get("followingCoordinateBy1") && conditionsToCheck.get("followingCoordinateBy2");
            }
        } else {
            System.out.println("Board.checkThreeInARow -> Bad call to method, must specify valid direction");
        }

        return false; //there were no 3 aligned matching symbols
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int rows = 0; rows < rowCount; rows++) {
            for (int cols = 0; cols < colCount; cols++) {
                result.append(" ");
                result.append(coordinates[cols][rows]);
                result.append(" ");
                if (cols != colCount - 1) result.append("|");
            }
            result.append("\n");
            if (rows != rowCount - 1) {
                result.append("----".repeat(colCount));
                result.deleteCharAt(result.length()-1);
                result.append("\n");
            }
        }
        return result.toString();
    }
}
