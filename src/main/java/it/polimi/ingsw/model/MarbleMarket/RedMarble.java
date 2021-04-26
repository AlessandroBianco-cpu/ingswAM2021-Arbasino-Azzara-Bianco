package it.polimi.ingsw.model.MarbleMarket;

import it.polimi.ingsw.model.ResourceType;

public class RedMarble extends Marble {

    public RedMarble() {
        resourceType = ResourceType.FAITH;
    }

    /**
     * Converts the RedMarble into a faith point
     * @return a FaithPoint QuantityResource
     */
    @Override
    public ResourceType convertResource() {
        return resourceType;
    }

}
