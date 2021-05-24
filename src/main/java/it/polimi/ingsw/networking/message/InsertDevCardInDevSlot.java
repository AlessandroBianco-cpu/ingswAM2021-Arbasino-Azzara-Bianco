package it.polimi.ingsw.networking.message;

public class InsertDevCardInDevSlot extends Client2Server{

    private static final long serialVersionUID = -3475924272173066351L;
    private int indexToAdd;

    public InsertDevCardInDevSlot(int indexToAdd) {
        this.indexToAdd = indexToAdd;
    }

    public int getIndexToAdd() {
        return indexToAdd;
    }



}
