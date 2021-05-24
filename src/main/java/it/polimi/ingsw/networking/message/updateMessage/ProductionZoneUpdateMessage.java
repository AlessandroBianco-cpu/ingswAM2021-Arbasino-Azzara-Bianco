package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.Cards.ExtraDevCard;
import it.polimi.ingsw.networking.message.Broadcast;

import java.util.List;

public class ProductionZoneUpdateMessage extends Broadcast {
    private final static long serialVersionUID = -6173989691231375175L;
    private String nickname;
    private DevCard[] cardsInProductionSlot;
    private List<ExtraDevCard> activatedExtraCards;


    public ProductionZoneUpdateMessage(String nickname, DevCard[] cardsInProductionSlot, List<ExtraDevCard> activatedExtraCards) {
        this.nickname = nickname;
        this.cardsInProductionSlot = cardsInProductionSlot;
        this.activatedExtraCards = activatedExtraCards;
    }

    public String getNickname() {
        return nickname;
    }

    public DevCard[] getCardsInProductionSlot() {
        return cardsInProductionSlot;
    }

    public List<ExtraDevCard> getActivatedExtraCards() {
        return activatedExtraCards;
    }
}
