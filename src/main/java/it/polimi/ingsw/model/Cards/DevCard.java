package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.QuantityResource;

import java.util.List;

public class DevCard extends Card {

    private final int level;
    private final List<QuantityResource> cost;
    private final DevCardColor color;
    private final List<QuantityResource> productionPowerInput;
    private final List<QuantityResource> productionPowerOutput;

    public DevCard(int victoryPoints, int level, int id, List<QuantityResource> cost, DevCardColor color,
        List<QuantityResource> productionPowerInput, List<QuantityResource> productionPowerOutput) {
        this.victoryPoints = victoryPoints;
        this.level = level;
        this.id = id;
        this.cost = cost;
        this.color = color;
        this.productionPowerInput = productionPowerInput;
        this.productionPowerOutput = productionPowerOutput;
    }

    public int getLevel() {
        return level;
    }

    public List<QuantityResource> getCost() {
        return cost;
    }

    public DevCardColor getColor() {
        return color;
    }

    public List<QuantityResource> getProductionPowerInput() {
        return productionPowerInput;
    }

    public List<QuantityResource> getProductionPowerOutput() {
        return productionPowerOutput;
    }
}
