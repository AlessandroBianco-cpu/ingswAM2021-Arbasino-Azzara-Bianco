package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.MarbleMarket.Market;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by FaithTrack server-side
 */
public class MarketObservable {

    private final List<MarketObserver> observers = new ArrayList<>();

    public void addObserver(MarketObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }


    public void notifyMarketState(Market market){
        synchronized (observers) {
            for(MarketObserver observer : observers){
                observer.updateMarketState(market);
            }
        }
    }

}
