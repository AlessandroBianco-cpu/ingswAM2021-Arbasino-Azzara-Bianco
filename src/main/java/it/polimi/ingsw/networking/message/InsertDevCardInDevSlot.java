package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to insert a Development Card
 * he just bought in a certain slot in the production-zone of his personal board.
 * It contains the index of the development slot in which he wants to add the card just bought ( 1-3)
 */
public class InsertDevCardInDevSlot extends Client2Server{
    private static final long serialVersionUID = -3475924272173066351L;

    private final int indexToAdd;

    public InsertDevCardInDevSlot(int indexToAdd) {
        this.indexToAdd = indexToAdd;
    }

    public int getIndexToAdd() {
        return indexToAdd;
    }



}
