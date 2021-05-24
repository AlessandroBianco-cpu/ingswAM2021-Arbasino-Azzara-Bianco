package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

public class ResourcePlayerWantsToSpendMessage extends Client2Server{

    private static final long serialVersionUID = 5976649949967856397L;

    private ResourceType resourceType;
    private int fromWarehouse;
    private int fromStrongbox;
    private int fromExtraDepot;

    public ResourcePlayerWantsToSpendMessage(ResourceType resourceType, int fromWarehouse, int fromStrongbox, int fromExtraDepot) {
        this.resourceType = resourceType;
        this.fromWarehouse = fromWarehouse;
        this.fromStrongbox = fromStrongbox;
        this.fromExtraDepot = fromExtraDepot;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getFromWarehouse() {
        return fromWarehouse;
    }

    public int getFromStrongbox() {
        return fromStrongbox;
    }

    public int getFromExtraDepot() {
        return fromExtraDepot;
    }
}
