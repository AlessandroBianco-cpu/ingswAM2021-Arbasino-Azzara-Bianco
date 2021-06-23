package it.polimi.ingsw.observer;

import it.polimi.ingsw.networking.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by VirtualView
 */
public class ViewObservable {
    private final List<ViewObserver> observers = new ArrayList<>();

    public void addObserver(ViewObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(ViewObserver observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void notifyNickname(String nickname){
        synchronized (observers) {
            for(ViewObserver observer : observers){
                observer.updateNickname(nickname);
            }
        }
    }


    public void notifyPlayersNumber(int numbers){
        synchronized (observers) {
            for(ViewObserver observer : observers){
                observer.updatePlayersNumber(numbers);
            }
        }
    }


    public void notifyNewMessage(Message m){
        synchronized (observers) {
            for(ViewObserver observer : observers){
                observer.updateMessage(m);
            }
        }
    }

}