package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Cards.CardRequirement;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * The class represents the BaseProdPower
 */
public class BaseDevSlot extends ProductionSlot{

    private List<QuantityResource> productionPowerInput;
    private List<QuantityResource> productionPowerOutput;

    public BaseDevSlot() {
        productionPowerInput = new ArrayList<>();
        productionPowerOutput = new ArrayList<>();

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

    public void setProductionPowerInput(ResourceType firstInput, ResourceType secondInput) {
        productionPowerInput = new ArrayList<>();
        productionPowerInput.add( new QuantityResource(firstInput, 1));
        productionPowerInput.add( new QuantityResource(secondInput, 1));
    }

    public void setProductionPowerOutput(ResourceType output) {
        productionPowerOutput = new ArrayList<>();
        productionPowerOutput.add(new QuantityResource(output,1));
    }

    @Override
    boolean hasCards() {
        return false;
    }
}
