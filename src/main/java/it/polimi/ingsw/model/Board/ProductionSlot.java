package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Cards.CardRequirement;
import it.polimi.ingsw.model.QuantityResource;

import java.util.List;

/**
 * The class represents a generic Production Slot of the PersonalBoard
 */
public abstract class ProductionSlot {

    abstract List<QuantityResource> getProductionPowerInput();
    abstract List<QuantityResource> getProductionPowerOutput();
    abstract boolean hasCards();
    /**
     * This method is used to count the number of Cards in the slot that satisfy the given requirement
     * @param cardRequirement is the Requirement player wants to see if and how many times is respected in the slot
     * @return the number of Cards that satisfy the given CardRequirement
     */
    public int numberOfCardsSatisfyingCardRequirement(CardRequirement cardRequirement){
        return 0;
    };

}
