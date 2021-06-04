package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.networking.message.Broadcast;

/**
 * Packet used to update the status of the DevCardMarket
 */
public class DevCardMarketUpdateMessage extends Broadcast {
    private static final long serialVersionUID = -1179250416056169491L;

    private final DevCard[] DevCardMarketStatus;

    public DevCardMarketUpdateMessage(DevCard[] devCardMarketStatus) {
        DevCardMarketStatus = devCardMarketStatus;
    }

    public DevCard[] getDevCardMarketStatus() {
        return DevCardMarketStatus;
    }
}
