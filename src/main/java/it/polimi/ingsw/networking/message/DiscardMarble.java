package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to discard a marble of the ones he received after a market action
 * It contains the index of the marble he wants to discard (human readable).
 */
public class DiscardMarble extends Client2Server{
    private static final long serialVersionUID = -1890903805148175960L;

    private final int bufferIndex;

    public DiscardMarble(int bufferIndex) {
        this.bufferIndex = bufferIndex;
    }

    public int getBufferIndex() {
        return bufferIndex;
    }
}
