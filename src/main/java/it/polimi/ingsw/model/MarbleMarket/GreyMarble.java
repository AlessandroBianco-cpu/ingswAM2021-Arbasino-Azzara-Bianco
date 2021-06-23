package it.polimi.ingsw.model.MarbleMarket;

import it.polimi.ingsw.model.ResourceType;

public class GreyMarble extends Marble {

    public GreyMarble() {
        resourceType = ResourceType.STONE;
    }

    /**
     * Converts the GreyMarble into a stone
     * @return a Stone QuantityResource
     */
    @Override
    public ResourceType convertResource() {
        return resourceType;
    }

}
