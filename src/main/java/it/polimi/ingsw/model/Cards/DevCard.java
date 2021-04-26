package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.QuantityResource;

import java.util.List;

public class DevCard extends Card {

    private int level;
    private List<QuantityResource> cost;
    private DevCardColor color;
    private List<QuantityResource> productionPowerInput;
    private List<QuantityResource> productionPowerOutput;

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

    //TODO Vedere se fare override
    public List<QuantityResource> getProductionPowerInput() {
        return productionPowerInput;
    }

    //TODO Vedere se fare override
    public List<QuantityResource> getProductionPowerOutput() {
        return productionPowerOutput;
    }
}
