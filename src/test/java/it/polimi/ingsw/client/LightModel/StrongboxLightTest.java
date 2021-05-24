package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.networking.message.updateMessage.StrongboxUpdateMessage;
import org.junit.jupiter.api.Test;

class StrongboxLightTest {

    @Test
    void graphicStrongboxUpdate() {
        StrongboxLight strongboxLight = new StrongboxLight();
        strongboxLight.print();
        StrongboxUpdateMessage message = new StrongboxUpdateMessage("test",1, 2, 3,4);
        strongboxLight.update(message);
        strongboxLight.print();
        message = new StrongboxUpdateMessage("test",0, 12, 5,7);
        strongboxLight.update(message);
        strongboxLight.print();
    }

}