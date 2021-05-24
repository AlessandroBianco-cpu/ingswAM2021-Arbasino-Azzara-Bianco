package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.MarbleMarket.Market;

public interface MarketObserver {

    void updateMarketState(Market market);

}
