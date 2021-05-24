package it.polimi.ingsw.observer;

import it.polimi.ingsw.networking.Lobby;

/**
 * Observer interface used by Server
 */
public interface LobbyObserver {

    void updatePlayersNumber(Lobby lobby);

    void createNewLobby();

}
