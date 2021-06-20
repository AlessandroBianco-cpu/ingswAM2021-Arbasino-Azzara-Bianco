package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.LeaderCardPowerAdder;
import it.polimi.ingsw.model.ResourceType;

import java.util.List;

public class DiscountCard extends LeaderCard {

    private final int discountAmount;

    public DiscountCard(List<Requirement> requirements, ResourceType resourceType, int id, int victoryPoints, int discountAmount) {
        super(requirements, resourceType, id, victoryPoints);
        this.discountAmount = discountAmount;
    }

    @Override
    public void activateCard(LeaderCardPowerAdder leaderCardPowerAdder) {
        leaderCardPowerAdder.addDiscountCardPower(this);
        this.active = true;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public boolean isDiscountCard() {
        return true;
    }
}
