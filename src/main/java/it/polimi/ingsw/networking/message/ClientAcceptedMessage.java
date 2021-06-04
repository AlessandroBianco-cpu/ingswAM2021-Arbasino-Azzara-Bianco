package it.polimi.ingsw.networking.message;

/**
 * Packet used to send the player that the chosen nickname is valid
 */
public class ClientAcceptedMessage extends Server2Client {
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
