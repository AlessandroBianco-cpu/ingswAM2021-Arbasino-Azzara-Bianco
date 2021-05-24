package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.ResourceType;

public class NoResourceLight extends ResourceLight {

    public NoResourceLight() {
        this.resource = ResourceType.NOTHING;
    }

    @Override
    public String toString() {
        return (" ");
    }

}