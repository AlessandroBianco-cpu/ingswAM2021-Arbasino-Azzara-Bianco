package it.polimi.ingsw.model.LorenzoIlMagnifico;

import it.polimi.ingsw.model.Cards.DevCardColor;
import it.polimi.ingsw.model.LorenzoGameMethods;

public class PurpleToken extends ColoredToken{

    public PurpleToken(LorenzoGameMethods lorenzoGameMethods) {
        super(lorenzoGameMethods);
        this.color = DevCardColor.PURPLE;
    }

    @Override
    public boolean isPurple() {
        return true;
    }
}
