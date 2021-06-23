package it.polimi.ingsw.model.MarbleMarket;

import it.polimi.ingsw.model.ResourceType;

public class YellowMarble extends Marble {

    public YellowMarble() {
        resourceType = ResourceType.COIN;
    }

    /**
     * Converts the YellowMarble into a coin
     * @return a Coin QuantityResource
     */
    @Override
    public ResourceType convertResource() {
        return resourceType;
    }

}
