package it.polimi.ingsw.networking;

import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.observer.ConnectionObserver;

public interface ClientHandler extends Runnable {

    void addObserver(ConnectionObserver observer);

    void send(Object object);

    void setMyTurn(boolean myTurn);

    void run();

    void setUserNickname(String userNickname);

    String getUserNickname();

    Message read();
}
