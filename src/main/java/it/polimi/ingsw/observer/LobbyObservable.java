package it.polimi.ingsw.observer;

import it.polimi.ingsw.networking.Lobby;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class used by Lobby
 */
public class LobbyObservable {
    private final List<LobbyObserver> observers = new ArrayList<>();

    public void addObserver(LobbyObserver observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void notifyPlayersNumber(Lobby lobby){
        synchronized (observers) {
            for(LobbyObserver observer : observers){
                observer.updatePlayersNumber(lobby);
            }
        }
    }

    public void notifyLobbyIsReady(){
        synchronized (observers) {
            for(LobbyObserver observer : observers){
                observer.createNewLobby();
            }
        }
    }
}
