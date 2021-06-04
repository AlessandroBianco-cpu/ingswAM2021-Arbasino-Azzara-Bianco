package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Packet used to send to the server the will of the player to pick resource(s) and
 * the index of the warehouse where he wants to add the chosen resources(s) (initial stage of the game).
 */
public class ChooseResourcesMessage extends Server2Client {
    private static final long serialVersionUID = 5887304475099119773L;

    private final int numOfRes;
    private final List<ResourceType> resources;
    private final List<Integer> indexes;

    public ChooseResourcesMessage(int numOfRes) {
        this.numOfRes = numOfRes;
        this.resources = new ArrayList<>();
        this.indexes = new ArrayList<>();
    }

    public int getNumOfRes() {
        return numOfRes;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public List<ResourceType> getResources() {
        return resources;
    }

    public void addResource(ResourceType res, int index) {
        resources.add(res);
        indexes.add(index);
    }

}
