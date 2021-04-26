package it.polimi.ingsw.model.MarbleMarket;

import it.polimi.ingsw.model.ResourceType;

public class PurpleMarble extends Marble {

    public PurpleMarble() {
        resourceType = ResourceType.SERVANT;
    }

    /**
     * Converts the PurpleMarble into a servant
     * @return a Servant QuantityResource
     */
    @Override
    public ResourceType convertResource() {
        return resourceType;
    }

}
