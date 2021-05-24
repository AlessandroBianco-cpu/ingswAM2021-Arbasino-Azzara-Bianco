package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.networking.message.Broadcast;

public class DevCardMarketUpdateMessage extends Broadcast {

    private static final long serialVersionUID = -1179250416056169491L;
    private DevCard[] DevCardMarketStatus;

    public DevCardMarketUpdateMessage(DevCard[] devCardMarketStatus) {
        DevCardMarketStatus = devCardMarketStatus;
    }

    public DevCard[] getDevCardMarketStatus() {
        return DevCardMarketStatus;
    }
}
