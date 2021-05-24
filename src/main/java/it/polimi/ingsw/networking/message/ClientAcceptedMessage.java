package it.polimi.ingsw.networking.message;

import java.io.Serializable;

/**
 * Message class used to send a nickname
 */
public class ClientAcceptedMessage implements Serializable {

    //set-up done
    private static final long serialVersionUID = 1720561504621287405L;

    private final String nickname;

    public ClientAcceptedMessage(String name) {
        this.nickname = name;
    }

    public String getNickname() {
        return nickname;
    }
}
