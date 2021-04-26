package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Cards.CardRequirement;
import it.polimi.ingsw.model.Cards.ExtraDevCard;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class ExtraDevSlot extends ProductionSlot{

    private List<QuantityResource> productionPowerInput;
    private List<QuantityResource> productionPowerOutput;

    public ExtraDevSlot(ExtraDevCard card){
        this.productionPowerInput = new ArrayList<>();
        productionPowerInput.add(new QuantityResource(card.getAbilityResource(), 1));

        this.productionPowerOutput = new ArrayList<>();
    }

    public void setProductionPowerOutput(ResourceType output) {
        productionPowerInput.clear();
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
