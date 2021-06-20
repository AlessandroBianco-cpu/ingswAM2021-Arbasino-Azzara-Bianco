package it.polimi.ingsw.controller;

import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.observer.ViewObserver;

/**
 * Class used to transfer the information of messages, nicknames and players number from virtualView/Lobby to controller
 */
public class UserInputManager implements ViewObserver {

    private Message actionMessage; //is the message sent by the client

    private String nickname; //set-up Lobby
    private int playersNumber; //set-up Lobby

    public UserInputManager() {
        nickname = null;
        playersNumber = 0;
    }

    public Message getActionMessage() {
        return actionMessage;
    }

    @Override
    public void updateMessage(Message m) {
        this.actionMessage = m;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    @Override
    public void updatePlayersNumber(int number) {
        this.playersNumber = number;
    }


}
