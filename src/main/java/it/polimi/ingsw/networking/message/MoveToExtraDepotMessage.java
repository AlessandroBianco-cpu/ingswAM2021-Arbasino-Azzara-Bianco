package it.polimi.ingsw.networking.message;


public class MoveToExtraDepotMessage extends Client2Server{
    private static final long serialVersionUID = -8745744815809918777L;

    private int depotFrom;
    private int extraDepotTo;
    private int quantity;

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
