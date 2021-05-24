package it.polimi.ingsw.networking.message;

/**
 * This class is used to represent the action of adding a marble
 * into the warehouse or extra depot
 */
public class StoreResourceInWarehouse extends Client2Server{

    private final long serialVersionUID = -8843216297856549917L;

    private int marbleBufferIndex;
    private int shelfIndex;

    public StoreResourceInWarehouse(int marbleBufferIndex, int shelfIndex) {
        this.marbleBufferIndex = marbleBufferIndex;
        this.shelfIndex = shelfIndex;
    }

    public int getMarbleBufferIndex() {
        return marbleBufferIndex;
    }

    public int getShelfIndex() {
        return shelfIndex;
    }

}
