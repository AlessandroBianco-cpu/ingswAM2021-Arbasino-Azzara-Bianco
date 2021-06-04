package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class YellowMarbleLight extends MarbleLight {

    public YellowMarbleLight() {
        this.resource = ResourceType.COIN;
    }

    @Override
    public String toString() {
        return (ConsoleColors.YELLOW + "⬤" + ConsoleColors.RESET);
    }

    @Override
    public String toImage() { return "/marbles/yellow.png"; }
}
