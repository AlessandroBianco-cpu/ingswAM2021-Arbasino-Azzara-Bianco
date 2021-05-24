package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.client.LightModel.market.MarbleLight;
import it.polimi.ingsw.networking.message.Server2Client;

import java.util.List;

public class MarbleBufferUpdateMessage extends Server2Client {
    private static final long serialVersionUID = -4730332732914752476L;

    private List<MarbleLight> buffer;

    public MarbleBufferUpdateMessage(List<MarbleLight> buffer) {
        this.buffer = buffer;
    }

    public List<MarbleLight> getBuffer() {
        return buffer;
    }
}
