package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to move resources from an extraDepot to a depot of the warehouse.
 * It contains:
 *      - @extraDepotFrom: the index of the extra depot from which he wants to move the resources (human readable)
 *      - @warehouseDepotTo: the index of the depot of the warehouse in which he wants to put the resources (human readable)
 *      - @quantity: how many resources he wants to move
 */
public class MoveFromExtraDepotMessage extends Client2Server{
    private static final long serialVersionUID = -3556374456478380527L;

    private final int extraDepotFrom;
    private final int warehouseDepotTo;
    private final int quantity;

    public MoveFromExtraDepotMessage(int extraDepotFrom, int warehouseDepotTo, int quantity) {
        this.extraDepotFrom = extraDepotFrom;
        this.warehouseDepotTo = warehouseDepotTo;
        this.quantity = quantity;
    }

    public int getExtraDepotFrom() {
        return extraDepotFrom;
    }

    public int getWarehouseDepotTo() {
        return warehouseDepotTo;
    }

    public int getQuantity() {
        return quantity;
    }
}
