package it.polimi.ingsw.observer;

import it.polimi.ingsw.networking.message.Message;

/**
 * Observer interface used by UserInputManager
 */
public interface ViewObserver {

    void updateNickname(String nickname);


    void updatePlayersNumber(int number);


    void updateMessage(Message m);

}
