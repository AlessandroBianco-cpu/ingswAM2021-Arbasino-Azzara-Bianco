package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class BlueMarbleLight extends MarbleLight{

    public BlueMarbleLight() {
        this.resource = ResourceType.SHIELD;
    }

    @Override
    public String toString() {
        return (ConsoleColors.BLUE + "⬤" + ConsoleColors.RESET);
    }

    @Override
    public String toImage() { return "/marbles/blue.png"; }
}
