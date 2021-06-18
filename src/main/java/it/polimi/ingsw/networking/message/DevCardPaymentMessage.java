package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

/**
 * Packet used to send to the server the will of the pay a certain type of resource he has to pay in order to buy a DevCard.
 * It contains:
 *      - The type of resource he wants to pay
 *      - How many resources he wants to pay from the Warehouse
 *      - How many resources he wants to pay from the ExtraDepot
 *      - How many resources he wants to pay from the Strongbox
 *      - His will to use the discount of a leaderCard he could possess.
 */
public class DevCardPaymentMessage extends Client2Server{
    private static final long serialVersionUID = 7456174716735493810L;

    private final int fromWarehouse;
    private final int fromExtraDepot;
    private final int fromStrongbox;

    private final ResourceType resourceType;
    private final boolean applyDiscount;

    public DevCardPaymentMessage(int fromWarehouse, int fromStrongbox, int fromExtraDepot, ResourceType resourceType, boolean applyDiscount) {
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
