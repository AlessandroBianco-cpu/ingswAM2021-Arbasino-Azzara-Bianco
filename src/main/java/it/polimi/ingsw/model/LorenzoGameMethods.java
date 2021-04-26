package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.DevCardColor;

public interface LorenzoGameMethods extends VaticanReporter {
    @Override
    void vaticanReport();

    void setLorenzoWin();

    void throwCardByColor(DevCardColor color);
}
