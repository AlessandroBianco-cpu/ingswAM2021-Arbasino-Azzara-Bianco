package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to store a marble in an extra depot
 * It contains the index of the marble from which he wants to take the marble (human readable)
 */
public class StoreResourceInExtraDepot extends Client2Server{
    private static final long serialVersionUID = 1149093058863972562L;

    private final int marbleBufferIndex;

    public StoreResourceInExtraDepot(int marbleBufferIndex) {
        this.marbleBufferIndex = marbleBufferIndex;
    }

    public int getMarbleBufferIndex() {
        return marbleBufferIndex;
    }
}
