package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.networking.message.Server2Client;

import java.util.List;

/**
 * Packet used to update the status of the Leader Cards possessed by the opponent of the Client that receives the message.
 * It contains the list of the active leader Cards of the opponent and the number of LeaderCards he still has in hand in order to visualize them correctly.
 */
public class OpponentsLeaderCardsInHandUpdateMessage extends Server2Client {
    private final static long serialVersionUID = 4184120133986925633L;

    private final List<LeaderCard> activeCards;
    private final int numberOfCards;
    private final String nickname;

    public OpponentsLeaderCardsInHandUpdateMessage(List<LeaderCard> activeCards, int numberOfCards, String nickname) {
        this.activeCards = activeCards;
        this.numberOfCards = numberOfCards;
        this.nickname = nickname;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public List<LeaderCard> getActiveCards() {
        return activeCards;
    }

    public String getNickname() {
        return nickname;
    }

}
