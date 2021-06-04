package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.Cards.DevCard;

/**
 * This message is sent from the server to the client to send him the card he just bought
 */
public class PlacementDevCardMessage extends Server2Client{
    private static final long serialVersionUID = 4578683310486716480L;

    private final DevCard boughtCard;

    public PlacementDevCardMessage(DevCard boughtCard) {
        this.boughtCard = boughtCard;
    }

    public DevCard getBoughtCard() {
        return boughtCard;
    }
}
