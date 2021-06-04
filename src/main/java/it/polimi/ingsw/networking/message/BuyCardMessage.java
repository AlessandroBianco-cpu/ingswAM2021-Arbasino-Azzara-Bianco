package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to buy a certain DevCard.
 * It contains the index of the card he wants to buy (human readable)
 */
public class BuyCardMessage extends Client2Server {
    private static final long serialVersionUID = -8720652913346904350L;

    private final int indexCard;

    public BuyCardMessage(int indexCard) {
        this.indexCard = indexCard;
    }

    public int getIndexCard() {
        return indexCard;
    }
}
