package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class ServantLight extends ResourceLight {

    public ServantLight() {
        this.resource = ResourceType.SERVANT;
    }

    @Override
    public String toString() {
        return (ConsoleColors.PURPLE + "⬤" + ConsoleColors.RESET);
    }
}
