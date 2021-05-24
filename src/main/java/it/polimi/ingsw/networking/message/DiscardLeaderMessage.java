package it.polimi.ingsw.networking.message;

public class DiscardLeaderMessage extends Client2Server {

    private static final long serialVersionUID = 2247524166072279266L;

    private int index;

    public DiscardLeaderMessage(int index) {
        this.index = index;
    }

    public int getLeaderIndex() {
        return index;
    }
}

