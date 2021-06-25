package it.polimi.ingsw.observer;


import it.polimi.ingsw.networking.message.Message;

import java.io.IOException;

/**
 * It is observer of NetworkHandlerObservable (ex UI observable)
 */
public interface NetworkHandler extends Runnable {

    void updateMessage(Message message) throws IOException;

    void updateConnection(String ip, String port);

    void closeConnection();

}
