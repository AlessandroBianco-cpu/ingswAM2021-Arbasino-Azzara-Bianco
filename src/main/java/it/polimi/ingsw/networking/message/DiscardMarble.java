package it.polimi.ingsw.networking.message;

public class DiscardMarble extends Client2Server{

    private static final long serialVersionUID = -1890903805148175960L;
    private int bufferIndex;

    public DiscardMarble(int bufferIndex) {
        this.bufferIndex = bufferIndex;
    }

    public int getBufferIndex() {
        return bufferIndex;
    }
}
