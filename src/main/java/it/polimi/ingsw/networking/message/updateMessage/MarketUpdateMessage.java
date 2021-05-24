package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.Broadcast;

import java.util.List;

public class MarketUpdateMessage extends Broadcast {

    private static final long serialVersionUID = -7840429262400112486L;
    private List<ResourceType> marketStatus;
    private ResourceType marbleLeft;

    public MarketUpdateMessage(List<ResourceType> marketStatus, ResourceType marbleLeft) {
        this.marketStatus = marketStatus;
        this.marbleLeft = marbleLeft;
    }

    public List<ResourceType> getMarketStatus() {
        return marketStatus;
    }

    public ResourceType getMarbleLeft() {
        return marbleLeft;
    }
}
