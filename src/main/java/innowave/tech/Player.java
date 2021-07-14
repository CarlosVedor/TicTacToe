package innowave.tech;

public class Player {
    private String name;
    private char symbol;

    /**
     * Constructor for Player class
     * @param name the name of the player
     * @param symbol the symbol that represents the player's moves
     */
    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    //region Getters
    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
    //endregion
}
