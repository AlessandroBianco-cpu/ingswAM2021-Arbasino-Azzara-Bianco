package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class WhiteMarbleLight extends MarbleLight{

    public WhiteMarbleLight() {
        this.resource = ResourceType.NOTHING;
    }

    @Override
    public String toString() {
        return (ConsoleColors.WHITE_BRIGHT + "⬤" + ConsoleColors.RESET);
    }

    @Override
    public String toImage() {
        return "/graphics/marbles/white.png";
    }
}
