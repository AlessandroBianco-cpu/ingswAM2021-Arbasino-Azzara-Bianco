package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Board.Warehouse;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by Warehouse server-side
 */
public class WarehouseObservable {

    private final List<WarehouseObserver> observers = new ArrayList<>();

    public void addObserver(WarehouseObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(WarehouseObserver observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void notifyWarehouseState(Warehouse warehouse){
        synchronized (observers) {
            for(WarehouseObserver observer : observers){
                observer.updateWarehouseState(warehouse);
            }
        }
    }

}
