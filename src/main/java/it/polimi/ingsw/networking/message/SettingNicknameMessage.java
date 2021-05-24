package it.polimi.ingsw.networking.message;

public class SettingNicknameMessage extends Client2Server{

    private static final long serialVersionUID = 110627911100523547L;
    private String nickname;


    public SettingNicknameMessage(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
