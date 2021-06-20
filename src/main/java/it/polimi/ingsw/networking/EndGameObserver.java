package it.polimi.ingsw.networking;

/**
 * This interface is implemented by the Waiting room to handle the removing of nicknames from DataBase
 */
public interface EndGameObserver {

    void manageEndGame(int lobbyID);

    void managePreGameDisconnection(ClientHandler ch);
}
