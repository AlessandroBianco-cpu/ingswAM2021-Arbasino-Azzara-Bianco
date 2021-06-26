package it.polimi.ingsw.networking.message;

import it.polimi.ingsw.model.ResourceType;

/**
 * Packet used to send to the server the will of the player to pay resources necessary in order to complete the production.
 * It contains:
 *      - @resourceType: the type of resource player wants to pay
 *      - @fromWarehouse: how many resources he wants to pay from the warehouse
 *      - @fromStrongbox: how many resources he wants to pay from the strongbox
 *      - @fromExtraDepot: how many resources he wants to pay from the extra depot
 */
public class ProductionPaymentMessage extends Client2Server{
    private static final long serialVersionUID = 5976649949967856397L;

    private final ResourceType resourceType;
    private final int fromWarehouse;
    private final int fromStrongbox;
    private final int fromExtraDepot;

    public ProductionPaymentMessage(int fromWarehouse, int fromStrongbox, int fromExtraDepot, ResourceType resourceType) {
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
