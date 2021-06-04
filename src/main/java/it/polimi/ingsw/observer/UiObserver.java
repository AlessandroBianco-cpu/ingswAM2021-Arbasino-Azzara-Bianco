package it.polimi.ingsw.observer;


import it.polimi.ingsw.networking.message.Message;

import java.io.IOException;

/**
 * Observer interface used by NetworkHandler
 */
public interface UiObserver {

    void updateMessage(Message message) throws IOException;

    void updateConnection(String ip, String port);

}
