package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

public class DevCardPayment extends Client2Server{

    private static final long serialVersionUID = 7456174716735493810L;

    private int fromWarehouse;
    private int fromExtraDepot;
    private int fromStrongbox;

    private ResourceType resourceType;
    private boolean applyDiscount;

    public DevCardPayment(int fromWarehouse, int fromExtraDepot, int fromStrongbox, ResourceType resourceType, boolean applyDiscount) {
        this.fromWarehouse = fromWarehouse;
        this.fromExtraDepot = fromExtraDepot;
        this.fromStrongbox = fromStrongbox;
        this.resourceType = resourceType;
        this.applyDiscount = applyDiscount;
    }

    public int getFromWarehouse() {
        return fromWarehouse;
    }

    public int getFromExtraDepot() {
        return fromExtraDepot;
    }

    public int getFromStrongbox() {
        return fromStrongbox;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public boolean wantsToApplyDiscount() {
        return applyDiscount;
    }
}
