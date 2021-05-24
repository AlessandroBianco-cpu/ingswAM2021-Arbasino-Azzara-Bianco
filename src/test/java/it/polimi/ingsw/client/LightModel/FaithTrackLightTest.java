package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.networking.message.updateMessage.FaithTrackUpdateMessage;
import org.junit.jupiter.api.Test;

class FaithTrackLightTest {

    @Test
    void graphicFaithTrackTest(){
        FaithTrackLight faithTrackLight = new FaithTrackLight();
        FaithTrackUpdateMessage message = new FaithTrackUpdateMessage("test", 8, true,false,false);
        faithTrackLight.update(message);
        faithTrackLight.print();
    }

}