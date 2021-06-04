package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.LorenzoIlMagnifico.ActionToken;
import it.polimi.ingsw.networking.message.Server2Client;

/**
 * Packet used to update the status of Lorenzo Il Magnifico.
 * It contains his position and the last Token he used
 */
public class LorenzoUpdateMessage extends Server2Client {
    private final static long serialVersionUID = 6234456658744356988L;

    private final int position;
    private final ActionToken token;

    public LorenzoUpdateMessage(int position, ActionToken token) {
        this.position = position;
        this.token = token;
    }

    public int getPosition() {
        return position;
    }

    public ActionToken getToken() {
        return token;
    }
}
