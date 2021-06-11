package it.polimi.ingsw.networking;

public interface EndGameObserver {

    void manageEndGame(int lobbyID);

    void managePreGameDisconnection(ClientHandler ch);
}
