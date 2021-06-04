package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.networking.message.Message;

public interface SceneObserver {

    void updateNewMessageToSend(Message message);
}
