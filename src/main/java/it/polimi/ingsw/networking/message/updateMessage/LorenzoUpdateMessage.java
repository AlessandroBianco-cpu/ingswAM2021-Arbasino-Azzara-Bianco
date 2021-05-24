package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.model.LorenzoIlMagnifico.ActionToken;
import it.polimi.ingsw.networking.message.Server2Client;

public class LorenzoUpdateMessage extends Server2Client {
    private final static long serialVersionUID = 6234456658744356988L;
    private int position;
    private ActionToken token;

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
