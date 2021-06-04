package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.networking.message.Server2Client;

import java.util.List;

/**
 * Packet used to update the resources player has to pay in order to buy a DevCard
 */
public class CardPaymentResourceBufferUpdateMessage extends Server2Client {
    private static final long serialVersionUID = -9088507905995167123L;

    private final List<QuantityResource> resourcesToPay;

    public CardPaymentResourceBufferUpdateMessage(List<QuantityResource> resourcesToPay) {
        this.resourcesToPay = resourcesToPay;
    }

    public List<QuantityResource> getResourcesToPay() {
        return resourcesToPay;
    }
}
