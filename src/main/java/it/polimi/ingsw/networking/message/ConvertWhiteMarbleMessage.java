package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

/**
 * Packet used to send to the server the will of the player to convert a white marble into an other type of marble.
 * It contains the index of the marble he wants to convert (human-readable) and the type of resource he wants to convert it into
 */
public class ConvertWhiteMarbleMessage extends Client2Server{
    private static final long serialVersionUID = -1240123964326341430L;

    private final int marbleIndex;
    private final ResourceType resourceToConvert;

    public ConvertWhiteMarbleMessage(int marbleIndex, ResourceType resourceToConvert) {
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
