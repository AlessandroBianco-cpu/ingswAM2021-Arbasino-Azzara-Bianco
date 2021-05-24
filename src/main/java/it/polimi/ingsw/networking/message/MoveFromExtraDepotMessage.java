package it.polimi.ingsw.networking.message;

public class MoveFromExtraDepotMessage extends Client2Server{

    private static final long serialVersionUID = -3556374456478380527L;

    private int extraDepotFrom;
    private int warehouseDepotTo;
    private int quantity;

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
