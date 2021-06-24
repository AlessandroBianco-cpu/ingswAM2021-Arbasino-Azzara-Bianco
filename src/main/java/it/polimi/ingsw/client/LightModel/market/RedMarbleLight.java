package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class RedMarbleLight extends MarbleLight {

    public RedMarbleLight() {
        this.resource = ResourceType.FAITH;
    }

    @Override
    public String toString() {
        return (ConsoleColors.RED + "⬤" + ConsoleColors.RESET);
    }

    @Override
    public String toImage() { return "/graphics/marbles/red.png"; }
}
