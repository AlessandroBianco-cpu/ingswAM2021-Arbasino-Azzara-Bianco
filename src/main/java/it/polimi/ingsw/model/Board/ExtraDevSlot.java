package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Cards.CardRequirement;
import it.polimi.ingsw.model.Cards.ExtraDevCard;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to represent a production slot created and used by an extra production leader card
 */
public class ExtraDevSlot extends ProductionSlot{

    private final List<QuantityResource> productionPowerInput;
    private List<QuantityResource> productionPowerOutput;
    private final ExtraDevCard card;

    public ExtraDevSlot(ExtraDevCard card){
        this.card = card;

        this.productionPowerInput = new ArrayList<>();
        productionPowerInput.add(new QuantityResource(card.getAbilityResource(), 1));

        this.productionPowerOutput = new ArrayList<>();
    }

    public ExtraDevCard getCard() {
        return card;
    }

    /**
     * Sets the output of the production power of the extra production leader card
     * @param output output player wants to set
     */
    public void setProductionPowerOutput(ResourceType output) {
        productionPowerOutput = new ArrayList<>();
        productionPowerOutput.add(new QuantityResource(ResourceType.FAITH,1));
        productionPowerOutput.add(new QuantityResource(output,1));
    }

    @Override
    public List<QuantityResource> getProductionPowerInput() {
        return productionPowerInput;
    }

    @Override
    public List<QuantityResource> getProductionPowerOutput() {
        return productionPowerOutput;
    }

    @Override
    public int numberOfCardsSatisfyingCardRequirement(CardRequirement cardRequirement) {
        return 0;
    }

    @Override
    boolean hasCards() {
        return true;
    }
}
