package it.polimi.ingsw.model.LorenzoIlMagnifico;

import it.polimi.ingsw.model.Cards.DevCardColor;
import it.polimi.ingsw.model.LorenzoGameMethods;

public class GreenToken extends ColoredToken{

    public GreenToken(LorenzoGameMethods lorenzoGameMethods) {
        super(lorenzoGameMethods);
        this.color = DevCardColor.GREEN;
    }

}
