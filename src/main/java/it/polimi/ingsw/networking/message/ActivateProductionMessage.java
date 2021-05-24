package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

import java.util.List;

public class ActivateProductionMessage extends Client2Server{
    private static final long serialVersionUID = -1270775943321773786L;

    private List<Integer> indexes;
    private ResourceType firstBaseInput;
    private ResourceType secondBaseInput;
    private ResourceType baseOutput;
    private List<ResourceType> leaderOutput;

    public ActivateProductionMessage(List<Integer> indexes, ResourceType firstBaseInput, ResourceType secondBaseInput, ResourceType baseOutput, List<ResourceType> leaderOutput) {
        this.indexes = indexes;
        this.firstBaseInput = firstBaseInput;
        this.secondBaseInput = secondBaseInput;
        this.baseOutput = baseOutput;
        this.leaderOutput = leaderOutput;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public ResourceType getFirstBaseInput() {
        return firstBaseInput;
    }

    public ResourceType getSecondBaseInput() {
        return secondBaseInput;
    }

    public ResourceType getBaseOutput() {
        return baseOutput;
    }

    public List<ResourceType> getLeaderOutput() {
        return leaderOutput;
    }
}
