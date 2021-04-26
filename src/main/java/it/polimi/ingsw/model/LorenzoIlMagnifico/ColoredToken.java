package it.polimi.ingsw.model.LorenzoIlMagnifico;

import it.polimi.ingsw.model.Cards.DevCardColor;
import it.polimi.ingsw.model.LorenzoGameMethods;

public abstract class ColoredToken extends ActionToken{
    private LorenzoGameMethods lorenzoGameMethods;
    protected DevCardColor color;

    public ColoredToken(LorenzoGameMethods lorenzoGameMethods) {
        this.lorenzoGameMethods = lorenzoGameMethods;
    }

    @Override
    void doAction() {
        lorenzoGameMethods.throwCardByColor(color);
    }

}
