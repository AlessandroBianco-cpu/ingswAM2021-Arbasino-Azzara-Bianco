package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class ShieldLight extends ResourceLight{

    public ShieldLight() {
        this.resource = ResourceType.SHIELD;
    }

    @Override
    public String toString() {
        return (ConsoleColors.BLUE + "⬤" + ConsoleColors.RESET);
    }
}
