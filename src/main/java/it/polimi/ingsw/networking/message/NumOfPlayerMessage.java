package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the first player of the game to set the number of players that the game will have
 */
public class NumOfPlayerMessage extends Client2Server {
    private static final long serialVersionUID = -5640190812041668353L;

    private final int numPlayers;

    public NumOfPlayerMessage(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
