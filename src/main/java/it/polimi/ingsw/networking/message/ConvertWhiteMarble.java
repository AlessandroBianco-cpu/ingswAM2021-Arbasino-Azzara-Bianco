package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

public class ConvertWhiteMarble extends Client2Server{

    private static final long serialVersionUID = -1240123964326341430L;

    private int marbleIndex;
    private ResourceType resourceToConvert;

    public ConvertWhiteMarble(int marbleIndex, ResourceType resourceToConvert) {
        this.marbleIndex = marbleIndex;
        this.resourceToConvert = resourceToConvert;
    }

    public int getMarbleIndex() {
        return marbleIndex;
    }

    public ResourceType getResourceToConvert() {
        return resourceToConvert;
    }
}
