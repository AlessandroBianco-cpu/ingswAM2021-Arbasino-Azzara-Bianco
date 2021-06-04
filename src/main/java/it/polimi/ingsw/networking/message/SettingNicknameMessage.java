package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to use a certain nickname
 */
public class SettingNicknameMessage extends Client2Server{
    private static final long serialVersionUID = 110627911100523547L;

    private final String nickname;

    public SettingNicknameMessage(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
