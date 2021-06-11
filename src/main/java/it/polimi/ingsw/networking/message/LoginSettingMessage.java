package it.polimi.ingsw.networking.message;

/**
 * Packet used to send to the server the will of the player to use a certain nickname
 */
public class LoginSettingMessage extends Client2Server{
    private static final long serialVersionUID = 110627911100523547L;

    private final String nickname;
    private final boolean join;

    public LoginSettingMessage(String nickname, boolean join) {
        this.nickname = nickname;
        this.join = join;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean wantToJoin() {
        return join;
    }
}
