package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.Broadcast;

/**
 * Packet used to update the status of the Strongbox of the player whose nickname corresponds to the one in the message.
 */
public class WarehouseUpdateMessage extends Broadcast {
    private static final long serialVersionUID = -5698013560649295398L;

    private final String nickname;
    private final QuantityResource[] depots;

    public WarehouseUpdateMessage(String nickname, QuantityResource[] depots) {
        this.nickname = nickname;
        this.depots = depots;
    }

    public String getNickname() {
        return nickname;
    }

    public ResourceType getResourceTypeByShelf(int shelf){
        return depots[shelf].getResourceType();
    }

    public int getQuantityByShelf(int shelf){
        return depots[shelf].getQuantity();
    }

}
