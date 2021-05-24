package it.polimi.ingsw.observer;


import it.polimi.ingsw.networking.ClientHandler;

/**
 * Observer interface used by Lobby
 */
public interface ConnectionObserver {

    void updateDisconnection(ClientHandler clientHandler);

}
