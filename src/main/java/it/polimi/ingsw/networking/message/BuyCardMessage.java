package it.polimi.ingsw.networking.message;


public class BuyCardMessage extends Client2Server {
    private static final long serialVersionUID = -8720652913346904350L;

    private int indexCard;

    public BuyCardMessage(int indexCard) {
        this.indexCard = indexCard;
    }

    public int getIndexCard() {
        return indexCard;
    }
}
