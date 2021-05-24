package it.polimi.ingsw.networking.message;

public class ActivateLeaderMessage extends Client2Server{
    private static final long serialVersionUID = -7631098886670901305L;

    private int index;

    public ActivateLeaderMessage(int index) {
        this.index = index;
    }

    public int getLeaderIndex() {
        return index;
    }
}
