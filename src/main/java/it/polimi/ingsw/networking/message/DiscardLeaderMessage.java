package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to discard a certain LeaderCard
 * It contains the index of the Leader Card he wants to discard (human readable)
 */
public class DiscardLeaderMessage extends Client2Server {
    private static final long serialVersionUID = 2247524166072279266L;

    private final int index;

    public DiscardLeaderMessage(int index) {
        this.index = index;
    }

    public int getLeaderIndex() {
        return index;
    }
}

