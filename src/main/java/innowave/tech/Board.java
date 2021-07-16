package innowave.tech;

public class Board {
    public static final char EMPTY_SYMBOL = ' ';

    private final int rowCount;
    private final int colCount;

    private char[][] coordinates;
    private int totalMovesPlayed;
    private int latestX;
    private int latestY;

    /**
     * Constructor for Board class
     * @param colCount number of columns the board must have
     * @param rowCount number of rows the board must have
     */
    public Board(int colCount, int rowCount) {
        this.colCount = colCount;
        this.rowCount = rowCount;
        this.coordinates = new char[colCount][rowCount];
        for (int rows = 0; rows < rowCount; rows++) {
            for (int cols = 0; cols < colCount; cols++) {
                this.coordinates[cols][rows] = EMPTY_SYMBOL;
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

    public int getTotalMovesPlayed() {
        return totalMovesPlayed;
    }
    //endregion

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
                System.out.println("Board.playMove -> Coordinates aren't valid");
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
        return x >= 0 && x < colCount && y >= 0 && y < rowCount
                && coordinates[x][y] == EMPTY_SYMBOL;
    }

    /**
     * Checks if the player who just played has made the winning move
     * @param player The player
     * @return Returns true if the player has won the game in the latest move. Returns false otherwise.
     */
    public boolean checkPlayerVictory(Player player) {
        if (player != null &&
                player.getSymbol() == coordinates[latestX][latestY]){// makes sure this player is the one who did the latest move
            char checkSymbol = player.getSymbol();

            //check horizontally
            if (checkThreeInARowHorizontally(checkSymbol)) return true;
            //check vertically
            if (checkThreeInARowVertically(checkSymbol)) return true;
            //check diagonally ascending (bottom left to top right)
            if (checkThreeInARowDiagonallyAscending(checkSymbol)) return true;
            //check diagonally descending (top left to bottom right)
            else return checkThreeInARowDiagonallyDescending(checkSymbol);
        }

        return false;
    }

    /**
     * Checks if there are three matching symbols in the board horizontally
     * according to the neighbouring coordinates of the latest move that was played.
     * @param checkSymbol the symbol to look for in the last move's neighbouring coordinates
     *
     * @return Returns true if there are three matching symbols in the neighbouring coordinates of the latest
     * move. Returns false otherwise.
     */
    private boolean checkThreeInARowHorizontally(char checkSymbol){
        //At most, we only need to check between two positions before and after the latest move coordinates
        //since we check for 3 of the same symbols aligned in a particular direction

        //previous coordinates to the latest move coordinate
        if (latestX - 1 >= 0 && coordinates[latestX-1][latestY] == checkSymbol) {
            if (latestX - 2 >= 0 && coordinates[latestX-2][latestY] == checkSymbol) {
                //3 aligned with the previous 2 coordinates
                return true;

            } else { //possible that the following coordinate to the latest move matches symbol
                //3 aligned with the previous and following coordinates
                return latestX + 1 < colCount && coordinates[latestX+1][latestY] == checkSymbol;
            }
        } else { //following coordinates to the latest move coordinate
            //3 aligned with the following 2 coordinates
            return latestX + 1 < colCount && coordinates[latestX+1][latestY] == checkSymbol &&
                    latestX + 2 < colCount && coordinates[latestX+2][latestY] == checkSymbol;
        }
    }

    /**
     * Checks if there are three matching symbols in the board vertically
     * according to the neighbouring coordinates of the latest move that was played.
     * @param checkSymbol the symbol to look for in the last move's neighbouring coordinates
     *
     * @return Returns true if there are three matching symbols in the neighbouring coordinates of the latest
     * move. Returns false otherwise.
     */
    private boolean checkThreeInARowVertically(char checkSymbol){
        //At most, we only need to check between two positions before and after the latest move coordinates
        //since we check for 3 of the same symbols aligned in a particular direction

        //previous coordinates to the latest move coordinate
        if (latestY - 1 >= 0 && coordinates[latestX][latestY-1] == checkSymbol) {
            if (latestY - 2 >= 0 && coordinates[latestX][latestY-2] == checkSymbol) {
                //3 aligned with the previous 2 coordinates
                return true;

            } else { //possible that the following coordinate to the latest move matches symbol
                //3 aligned with the previous and following coordinates
                return latestY + 1 < rowCount && coordinates[latestX][latestY+1] == checkSymbol;
            }
        } else { //following coordinates to the latest move coordinate
            //3 aligned with the following 2 coordinates
            return latestY + 1 < rowCount && coordinates[latestX][latestY+1] == checkSymbol &&
                    latestY + 2 < rowCount && coordinates[latestX][latestY+2] == checkSymbol;
        }
    }

    /**
     * Checks if there are three matching symbols in the board diagonally ascending (bottom left to top right)
     * according to the neighbouring coordinates of the latest move that was played.
     * @param checkSymbol the symbol to look for in the last move's neighbouring coordinates
     *
     * @return Returns true if there are three matching symbols in the neighbouring coordinates of the latest
     * move. Returns false otherwise.
     */
    private boolean checkThreeInARowDiagonallyAscending(char checkSymbol){
        //At most, we only need to check between two positions before and after the latest move coordinates
        //since we check for 3 of the same symbols aligned in a particular direction

        //previous coordinates to the latest move coordinate
        if (latestX - 1 >= 0 && latestY + 1 < rowCount && coordinates[latestX-1][latestY+1] == checkSymbol) {
            if (latestX - 2 >= 0 && latestY + 2 < rowCount && coordinates[latestX-2][latestY+2] == checkSymbol) {
                //3 aligned with the previous 2 coordinates
                return true;

            } else { //possible that the following coordinate to the latest move matches symbol
                //3 aligned with the previous and following coordinates
                return latestX + 1 < colCount && latestY - 1 >= 0 && coordinates[latestX+1][latestY-1] == checkSymbol;
            }
        } else { //following coordinates to the latest move coordinate
            //3 aligned with the following 2 coordinates
            return latestX + 1 < colCount && latestY - 1 >= 0 && coordinates[latestX+1][latestY-1] == checkSymbol &&
                    latestX + 2 < colCount && latestY - 2 >= 0 && coordinates[latestX+2][latestY-2] == checkSymbol;
        }
    }

    /**
     * Checks if there are three matching symbols in the board diagonally descending (top left to bottom right)
     * according to the neighbouring coordinates of the latest move that was played.
     * @param checkSymbol the symbol to look for in the last move's neighbouring coordinates
     *
     * @return Returns true if there are three matching symbols in the neighbouring coordinates of the latest
     * move. Returns false otherwise.
     */
    private boolean checkThreeInARowDiagonallyDescending(char checkSymbol){
        //At most, we only need to check between two positions before and after the latest move coordinates
        //since we check for 3 of the same symbols aligned in a particular direction

        //previous coordinates to the latest move coordinate
        if (latestX - 1 >= 0 && latestY - 1 >= 0 && coordinates[latestX-1][latestY-1] == checkSymbol) {
            if (latestX - 2 >= 0 && latestY - 2 >= 0 && coordinates[latestX-2][latestY-2] == checkSymbol) {
                //3 aligned with the previous 2 coordinates
                return true;

            } else { //possible that the following coordinate to the latest move matches symbol
                //3 aligned with the previous and following coordinates
                return latestX + 1 < colCount && latestY + 1 < rowCount && coordinates[latestX+1][latestY+1] == checkSymbol;
            }
        } else { //following coordinates to the latest move coordinate
            //3 aligned with the following 2 coordinates
            return latestX + 1 < colCount && latestY + 1 < rowCount && coordinates[latestX+1][latestY+1] == checkSymbol &&
                    latestX + 2 < colCount && latestY + 2 < rowCount && coordinates[latestX+2][latestY+2] == checkSymbol;
        }
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
