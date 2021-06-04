package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

import java.util.List;

/**
 * Packet used to send to the server the will of the player to activate the production.
 * It contains all the indexes (0 for base Power / 1-3 for developmentCards / 4-5 for leaders of extra development type).
 * It also contains all the resources player has to choose (i.e. base production input/output, extra development output).
 */
public class ActivateProductionMessage extends Client2Server{
    private static final long serialVersionUID = -1270775943321773786L;

    private final List<Integer> indexes;
    private final ResourceType firstBaseInput;
    private final ResourceType secondBaseInput;
    private final ResourceType baseOutput;
    private final List<ResourceType> leaderOutput;

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
