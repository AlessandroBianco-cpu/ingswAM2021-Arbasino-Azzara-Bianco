package it.polimi.ingsw.model.LorenzoIlMagnifico;

import it.polimi.ingsw.model.Cards.DevCardColor;
import it.polimi.ingsw.model.LorenzoGameMethods;

public class YellowToken extends ColoredToken {

    public YellowToken(LorenzoGameMethods lorenzoGameMethods) {
        super(lorenzoGameMethods);
        this.color = DevCardColor.YELLOW;
    }
}
