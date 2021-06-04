package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.model.MarbleMarket.Marble;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by Player server-side
 */
public class PlayerItemsObservable {

    private final List<PlayerItemsObserver> observers = new ArrayList<>();

    public void addObserver(PlayerItemsObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(PlayerItemsObserver observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void notifyMarbleBuffer(List<Marble> marbleLinkedList) {
        synchronized (observers) {
            for(PlayerItemsObserver observer : observers){
                observer.updateMarbleBuffer(marbleLinkedList);
            }
        }
    }

    public void notifyLeaderCards(List<LeaderCard> leaderCards) {
        synchronized (observers) {
            for(PlayerItemsObserver observer : observers){
                observer.updateLeaderCards(leaderCards);
            }
        }
    }

}
