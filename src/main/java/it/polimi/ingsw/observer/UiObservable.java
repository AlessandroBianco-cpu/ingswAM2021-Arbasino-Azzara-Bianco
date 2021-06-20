package it.polimi.ingsw.observer;

import it.polimi.ingsw.networking.message.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by CLI and GUI
 */
public class UiObservable {
    private final List<UiObserver> observers = new ArrayList<>();

    public void addObserver(UiObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void notifyMessage(Message m){
        synchronized (observers) {
            for(UiObserver observer : observers){
                try {
                    observer.updateMessage(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void notifyConnection(String ip, String port){
        synchronized (observers) {
            for(UiObserver observer : observers){
                observer.updateConnection(ip, port);
            }
        }
    }
}
