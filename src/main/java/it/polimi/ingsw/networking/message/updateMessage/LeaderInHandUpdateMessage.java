package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.networking.message.Broadcast;

import java.util.List;

public class LeaderInHandUpdateMessage extends Broadcast {
    private final static long serialVersionUID = 8181773999047700073L;
    private List<LeaderCard> cards;
    private String nickname;

    public LeaderInHandUpdateMessage(List<LeaderCard> cards, String nickname) {
        this.cards = cards;
        this.nickname = nickname;
    }

    public List<LeaderCard> getCards() {
        return cards;
    }

    public String getNickname() {
        return nickname;
    }
}
