package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.LeaderCardPowerAdder;
import it.polimi.ingsw.model.ResourceType;

import java.util.List;

public class ExtraDepotCard extends LeaderCard {

    public ExtraDepotCard(List<Requirement> requirements, ResourceType resourceType, int id, int victoryPoints) {
        super(requirements, resourceType, id, victoryPoints);
    }

    @Override
    public void activateCard(LeaderCardPowerAdder leaderCardPowerAdder) {
        leaderCardPowerAdder.addExtraDepotCardPower(this);
        this.active = true;
    }

    @Override
    public boolean isExtraDepotCard() {
        return true;
    }
}
