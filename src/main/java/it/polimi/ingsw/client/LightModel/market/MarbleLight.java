package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.model.ResourceType;

import java.io.Serializable;

/**
 * Lightweight representation of the Marble stored client-side
 */
public abstract class MarbleLight implements Serializable {

    protected ResourceType resource;

    public ResourceType getResource() {
        return resource;
    }

    /**
     * @return the CLI representation of the Marble
     */
    @Override
    public abstract String toString();

    /**
     * @return the path of the image corresponding to the Marble
     */
    public abstract String toImage();
}
