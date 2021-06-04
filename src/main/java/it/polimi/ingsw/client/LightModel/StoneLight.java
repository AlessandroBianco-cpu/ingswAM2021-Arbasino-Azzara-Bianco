package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class StoneLight extends ResourceLight{

    public StoneLight() {
        this.resource = ResourceType.STONE;
    }

    @Override
    public String toString() {
        return (ConsoleColors.BLACK_BRIGHT + "⬤" + ConsoleColors.RESET);
    }

    @Override
    public String toImage() {
        return "/punchBoard/stone.png";
    }
}
