package it.polimi.ingsw.networking.message;

public class StoreResourceInExtraDepot extends Client2Server{

    private static final long serialVersionUID = 1149093058863972562L;
    private int marbleBufferIndex;

    public StoreResourceInExtraDepot(int marbleBufferIndex) {
        this.marbleBufferIndex = marbleBufferIndex;
    }

    public int getMarbleBufferIndex() {
        return marbleBufferIndex;
    }
}
