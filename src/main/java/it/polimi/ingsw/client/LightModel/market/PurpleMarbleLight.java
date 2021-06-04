package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class PurpleMarbleLight extends MarbleLight{

    public PurpleMarbleLight() {
        this.resource = ResourceType.SERVANT;
    }

    @Override
    public String toString() { return (ConsoleColors.PURPLE + "⬤" + ConsoleColors.RESET); }

    @Override
    public String toImage() {
        return "/marbles/purple.png";
    }
}
