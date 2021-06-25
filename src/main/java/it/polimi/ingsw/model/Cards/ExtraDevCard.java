package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.LeaderCardPowerAdder;
import it.polimi.ingsw.model.ResourceType;

import java.util.List;

/**
 * Leader card of Extra Development type. It permits the player to have an additional
 * Production Slot with an exclusive production power
 */
public class ExtraDevCard extends LeaderCard{

    public ExtraDevCard(List<Requirement> requirements, ResourceType resourceType, int id, int victoryPoints) {
        super(requirements, resourceType, id, victoryPoints);
    }

    @Override
    public void activateCard(LeaderCardPowerAdder leaderCardPowerAdder) {
        leaderCardPowerAdder.addExtraDevCardPower(this);
        this.active = true;
    }

    @Override
    public boolean isExtraDevCard() {
        return true;
    }


}
