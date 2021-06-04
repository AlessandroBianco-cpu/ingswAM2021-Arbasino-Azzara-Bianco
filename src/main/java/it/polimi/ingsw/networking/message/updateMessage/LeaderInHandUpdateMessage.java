package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.networking.message.Server2Client;

import java.util.List;

/**
 * Packet used to update the status of the Leader Cards possessed by the player that receives the packet
 */
public class LeaderInHandUpdateMessage extends Server2Client {
    private final static long serialVersionUID = 8181773999047700073L;

    private final List<LeaderCard> cards;
    private final String nickname;

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
