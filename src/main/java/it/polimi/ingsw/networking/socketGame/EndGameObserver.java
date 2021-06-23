package it.polimi.ingsw.networking.socketGame;

import it.polimi.ingsw.networking.ClientHandler;

public interface EndGameObserver {

    void manageEndGame(int lobbyID);

    void managePreGameDisconnection(ClientHandler ch);
}
