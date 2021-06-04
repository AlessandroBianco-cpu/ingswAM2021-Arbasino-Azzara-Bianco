package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.MarbleMarket.Market;

/**
 * Observer interface used by VirtualView
 */
public interface MarketObserver {

    void updateMarketState(Market market);

}
