package it.polimi.ingsw.model.MarbleMarket;

import it.polimi.ingsw.model.ResourceType;

public class WhiteMarble extends Marble {

    public WhiteMarble() {
        resourceType = ResourceType.NOTHING;
    }

    @Override
    public boolean isWhiteMarble() {
        return true;
    }

    @Override
    public ResourceType convertResource() {
        ResourceType resourceTypeTemp = resourceType;
        resourceType = ResourceType.NOTHING;
        return resourceTypeTemp;
    }

    public void convertWhiteToResource(ResourceType  resourceType){
        this.resourceType = resourceType;
    }

}
