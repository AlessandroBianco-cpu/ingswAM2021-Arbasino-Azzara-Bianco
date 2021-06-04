package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to move resources from an extraDepot to a depot of the warehouse.
 * It contains:
 *      - @depotFrom: the index of the depot of the warehouse from which he wants to move the resources (human readable)
 *      - @extraDepotTo: the index of the extraDepot of the leader card in which he wants to put the resources (human readable)
 *      - @quantity: how many resources he wants to move
 */
public class MoveToExtraDepotMessage extends Client2Server{
    private static final long serialVersionUID = -8745744815809918777L;

    private final int depotFrom;
    private final int extraDepotTo;
    private final int quantity;

    public MoveToExtraDepotMessage(int depotFrom, int extraDepotTo,int quantity) {
        this.depotFrom = depotFrom;
        this.extraDepotTo = extraDepotTo;
        this.quantity = quantity;
    }

    public int getDepotFrom() {
        return depotFrom;
    }

    public int getExtraDepotTo(){return extraDepotTo;}

    public int getQuantity() {
        return quantity;
    }
}
