package it.polimi.ingsw.networking.message;

/**
 *  Packet used to send to the server the will of the player to activate a certain LeaderCard
 */
public class ActivateLeaderMessage extends Client2Server{
    private static final long serialVersionUID = -7631098886670901305L;

    private final int index;

    public ActivateLeaderMessage(int index) {
        this.index = index;
    }

    public int getLeaderIndex() {
        return index;
    }
}
