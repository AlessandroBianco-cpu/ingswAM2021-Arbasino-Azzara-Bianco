package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.DevCardMarket;

/**
 * Observer interface used by VirtualView
 */
public interface DevCardMarketObserver {

    void updateDevCardMarketState(DevCardMarket devCardMarket);

}
