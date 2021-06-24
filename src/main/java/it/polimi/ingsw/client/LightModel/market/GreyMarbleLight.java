package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class GreyMarbleLight extends MarbleLight{

    public GreyMarbleLight() {
        this.resource = ResourceType.STONE;
    }

    @Override
    public String toString() {
        return (ConsoleColors.BLACK_BRIGHT + "⬤" + ConsoleColors.RESET);
    }

    @Override
    public String toImage() {
        return "/graphics/marbles/grey.png";
    }
}
