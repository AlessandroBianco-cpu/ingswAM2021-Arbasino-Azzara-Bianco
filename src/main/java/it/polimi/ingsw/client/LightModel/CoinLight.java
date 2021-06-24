package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.utils.ConsoleColors;

public class CoinLight extends ResourceLight{

    public CoinLight() {
        this.resource = ResourceType.COIN;
    }

    @Override
    public String toString() {
        return (ConsoleColors.YELLOW + "⬤" + ConsoleColors.RESET);
    }

    @Override
    public String toImage() {
        return "/graphics/punchBoard/coin.png";
    }
}
