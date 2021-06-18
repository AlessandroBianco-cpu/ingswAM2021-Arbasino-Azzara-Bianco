package it.polimi.ingsw.networking.message;

/**
 * This class is used to represent the action of adding a marble into a depot of the warehouse
 * It contains:
 *      - @marbleBufferIndex: index of the marble he wants to interact (human readable)
 *      - @shelfIndex: index of the depot of the warehouse in which he wants to put the resource
 */
public class StoreResourceInWarehouseMessage extends Client2Server{
    private static final long serialVersionUID = -8843216297856549917L;

    private final int marbleBufferIndex;
    private final int shelfIndex;

    public StoreResourceInWarehouseMessage(int marbleBufferIndex, int shelfIndex) {
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
