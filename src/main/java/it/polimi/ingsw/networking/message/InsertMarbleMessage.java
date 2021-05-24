package it.polimi.ingsw.networking.message;

public class InsertMarbleMessage extends Client2Server {

    private static final long serialVersionUID = -224505203998305577L;

    private boolean rowInsertion; //if true is row insertion, if false col insertion
    private int position;

    public InsertMarbleMessage(boolean rowInsertion, int position) {
        this.rowInsertion = rowInsertion;
        this.position = position;
    }

    public boolean isRowInsertion() {
        return rowInsertion;
    }

    public int getPosition() {
        return position;
    }
}
