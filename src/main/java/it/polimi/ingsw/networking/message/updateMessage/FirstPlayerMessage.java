package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.networking.message.Server2Client;

public class FirstPlayerMessage extends Server2Client {
    private final static long serialVersionUID = -7737365793479572413L;

    private final String nickname;

    public FirstPlayerMessage(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() { return nickname; }
}
