package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to insert a marble in a certain row or column of the market.
 * It contains:
 *      - the position of the row or column in which he wants to insert the marble
 *      - a boolean that indicates his will to insert it in a row (true) or column (false)
 */
public class InsertMarbleMessage extends Client2Server {
    private static final long serialVersionUID = -224505203998305577L;

    private final boolean rowInsertion; //if true is row insertion, if false col insertion
    private final int position;

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
