package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.networking.message.Server2Client;

import java.util.List;

/**
 * Packet used to update the status of the Resources player has to buy in order to complete the production action
 */
public class ProductionResourceBufferUpdateMessage extends Server2Client {
    private static final long serialVersionUID = 3272854361797734864L;

    private final List<QuantityResource> resourcesToPay;

    public ProductionResourceBufferUpdateMessage (List<QuantityResource> resourcesToPay) {
        this.resourcesToPay = resourcesToPay;
    }

    public List<QuantityResource> getResourcesToPay() {
        return resourcesToPay;
    }

}
