package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class ChooseResourcesMessage extends Server2Client {
    private static final long serialVersionUID = 5887304475099119773L;

    private int numOfRes;
    private List<ResourceType> resources;
    private List<Integer> indexes;

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
