package it.polimi.ingsw.model.MarbleMarket;

import it.polimi.ingsw.model.ResourceType;

public class BlueMarble extends Marble {

    public BlueMarble() {
        resourceType = ResourceType.SHIELD;
    }

    /**
     * Converts the BlueMarble into a shield
     * @return a Shield QuantityResource
     */
    @Override
    public ResourceType convertResource() {
        return resourceType;
    }

}
