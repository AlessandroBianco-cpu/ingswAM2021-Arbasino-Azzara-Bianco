package it.polimi.ingsw.networking.message;

public class NumOfPlayerMessage extends Client2Server {
    private static final long serialVersionUID = -5640190812041668353L;

    private int numPlayers;

    public NumOfPlayerMessage(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
