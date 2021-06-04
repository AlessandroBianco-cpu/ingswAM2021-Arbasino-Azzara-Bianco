package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.ResourceType;

/**
 * Lightweight representation of the resources stored client-side
 */
public abstract class ResourceLight {
    protected ResourceType resource;

    public ResourceType getResource() {
        return resource;
    }

    /**
     * @return the CLI representation of the Resource
     */
    @Override
    public abstract String toString();

    /**
     * @return the path of the image corresponding to the Resource
     */
    public abstract String toImage();
}
