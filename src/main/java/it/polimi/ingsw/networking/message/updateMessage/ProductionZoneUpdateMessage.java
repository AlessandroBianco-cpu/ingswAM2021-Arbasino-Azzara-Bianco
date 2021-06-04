package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.Cards.ExtraDevCard;
import it.polimi.ingsw.networking.message.Broadcast;

import java.util.List;

/**
 * Packet used to update the status of the cards in the production zone of the player whose nickname corresponds to the one in the message.
 */
public class ProductionZoneUpdateMessage extends Broadcast {
    private final static long serialVersionUID = -6173989691231375175L;

    private final String nickname;
    private final List<DevCard> firstDevSlot;
    private final List<DevCard> secondDevSlot;
    private final List<DevCard> thirdDevSlot;
    private final List<ExtraDevCard> activatedExtraCards;


    public ProductionZoneUpdateMessage(String nickname, List<DevCard> firstDevSlot, List<DevCard> secondDevSlot, List<DevCard> thirdDevSlot, List<ExtraDevCard> activatedExtraCards) {
        this.nickname = nickname;
        this.firstDevSlot = firstDevSlot;
        this.secondDevSlot = secondDevSlot;
        this.thirdDevSlot = thirdDevSlot;
        this.activatedExtraCards = activatedExtraCards;
    }

    public String getNickname() {
        return nickname;
    }

    public List<DevCard> getFirstDevSlot() {
        return firstDevSlot;
    }

    public List<DevCard> getSecondDevSlot() {
        return secondDevSlot;
    }

    public List<DevCard> getThirdDevSlot() {
        return thirdDevSlot;
    }

    public List<ExtraDevCard> getActivatedExtraCards() {
        return activatedExtraCards;
    }
}
