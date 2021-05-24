package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.networking.message.Broadcast;

import java.util.List;

public class NicknamesUpdateMessage extends Broadcast {
    private static final long serialVersionUID = 7625184296434553561L;

    private List<String> playersNickname;

    public NicknamesUpdateMessage(List<String> playersNickname) {
        this.playersNickname = playersNickname;
    }

    public List<String> getPlayersNickname() {
        return playersNickname;
    }
}
