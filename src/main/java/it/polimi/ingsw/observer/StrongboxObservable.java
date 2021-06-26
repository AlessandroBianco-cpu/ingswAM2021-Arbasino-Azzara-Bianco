package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Board.Strongbox;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by Strongbox server-side
 */
public class StrongboxObservable {

    private final List<StrongboxObserver> observers = new ArrayList<>();

    public void addObserver(StrongboxObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void notifyStrongboxState(Strongbox strongbox) {
        synchronized (observers) {
            for(StrongboxObserver observer : observers){
                observer.updateStrongboxState(strongbox);
            }
        }
    }

}
