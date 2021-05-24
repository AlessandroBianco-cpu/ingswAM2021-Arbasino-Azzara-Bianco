package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Board.PersonalBoard;

import java.util.ArrayList;
import java.util.List;

public class ProductionZoneObservable {

    private final List<ProductionZoneObserver> observers = new ArrayList<>();

    public void addObserver(ProductionZoneObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(ProductionZoneObserver observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void notifyPersonalBoardState(PersonalBoard personalBoard){
        synchronized (observers) {
            for(ProductionZoneObserver observer : observers){
                observer.updateProductionZoneState(personalBoard);
            }
        }
    }

}
