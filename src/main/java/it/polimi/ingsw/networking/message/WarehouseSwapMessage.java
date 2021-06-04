package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to swap resources between the depots of the warehouse
 * It contains:
 *      -@from : index of the depot from which player wants to move the resources (human readable)
 *      -@to : index of the depot in which player want to move the resources (human readable)
 */
public class WarehouseSwapMessage extends Client2Server{
    private static final long serialVersionUID = 4206888102204439281L;

    private final int from;
    private final int to;

    public WarehouseSwapMessage(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
