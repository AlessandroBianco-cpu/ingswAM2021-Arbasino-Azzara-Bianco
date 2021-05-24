package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.ResourceType;

public abstract class ResourceLight {
    protected ResourceType resource;

    public ResourceType getResource() {
        return resource;
    }

    @Override
    public abstract String toString();
}
