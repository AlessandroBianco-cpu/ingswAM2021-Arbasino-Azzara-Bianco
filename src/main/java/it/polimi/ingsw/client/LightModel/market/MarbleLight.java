package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.model.ResourceType;

import java.io.Serializable;

public abstract class MarbleLight implements Serializable {

    protected ResourceType resource;

    public ResourceType getResource() {
        return resource;
    }

    @Override
    public abstract String toString();
}
