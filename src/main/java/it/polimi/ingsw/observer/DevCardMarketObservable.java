package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.DevCardMarket;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by DevCardMarket server-side
 */
public class DevCardMarketObservable {

    private final List<DevCardMarketObserver> observers = new ArrayList<>();

    public void addObserver(DevCardMarketObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void notifyDevCardMarketState(DevCardMarket devCardMarket) {
        synchronized (observers) {
            for(DevCardMarketObserver observer : observers){
                observer.updateDevCardMarketState(devCardMarket);
            }
        }
    }

}
