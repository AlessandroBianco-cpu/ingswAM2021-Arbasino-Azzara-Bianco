package it.polimi.ingsw.model.MarbleMarket;

import it.polimi.ingsw.model.ResourceType;

public abstract class Marble {
    protected ResourceType resourceType;
    public abstract ResourceType convertResource();
    public boolean isWhiteMarble(){
        return false;
    }
    public ResourceType getResourceType(){
        return resourceType;
    }
}
