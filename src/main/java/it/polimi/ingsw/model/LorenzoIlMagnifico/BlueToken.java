package it.polimi.ingsw.model.LorenzoIlMagnifico;

import it.polimi.ingsw.model.Cards.DevCardColor;
import it.polimi.ingsw.model.LorenzoGameMethods;

public class BlueToken extends ColoredToken{

    public BlueToken(LorenzoGameMethods lorenzoGameMethods) {
        super(lorenzoGameMethods);
        this.color = DevCardColor.BLUE;
    }

}
