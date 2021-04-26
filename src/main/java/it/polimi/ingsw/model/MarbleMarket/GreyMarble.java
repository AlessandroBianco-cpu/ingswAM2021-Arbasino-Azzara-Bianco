package it.polimi.ingsw.model.MarbleMarket;

import it.polimi.ingsw.model.ResourceType;

public class GreyMarble extends Marble {

    public GreyMarble() {
        resourceType = ResourceType.STONE;
    }

    @Override
    public ResourceType convertResource() {
        return resourceType;
    }

}
