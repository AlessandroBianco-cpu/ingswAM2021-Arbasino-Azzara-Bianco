package it.polimi.ingsw.networking.message;

public class WarehouseSwapMessage extends Client2Server{
    private static final long serialVersionUID = 4206888102204439281L;

    private int from;
    private int to;

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
