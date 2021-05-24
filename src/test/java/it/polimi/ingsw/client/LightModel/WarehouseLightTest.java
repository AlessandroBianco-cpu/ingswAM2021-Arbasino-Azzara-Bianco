package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.networking.message.updateMessage.WarehouseUpdateMessage;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.ResourceType.*;

class WarehouseLightTest {

    @Test
    void graphicWarehouseTest(){
        WarehouseLight wl = new WarehouseLight();
        wl.print();
        System.out.println("---------");
        QuantityResource[] qArray = new QuantityResource[5];
        qArray[0] = new QuantityResource(NOTHING, 0);
        qArray[1] = new QuantityResource(SHIELD, 2);
        qArray[2] = new QuantityResource(SERVANT, 1);
        qArray[3] = new QuantityResource(NOTHING, 0);
        qArray[4] = new QuantityResource(NOTHING, 0);

        WarehouseUpdateMessage message = new WarehouseUpdateMessage("me", qArray);

        wl.updateWarehouseLight(message);
        wl.print();
        System.out.println("---------");

        qArray[0] = new QuantityResource(COIN, 1);
        qArray[1] = new QuantityResource(SHIELD, 2);
        qArray[2] = new QuantityResource(SERVANT, 3);
        qArray[3] = new QuantityResource(STONE, 1);

        message = new WarehouseUpdateMessage("me", qArray);

        wl.updateWarehouseLight(message);
        wl.print();
        System.out.println("---------");

        qArray[0] = new QuantityResource(NOTHING,0);
        qArray[1] = new QuantityResource(NOTHING,0);
        qArray[2] = new QuantityResource(NOTHING,0);
        qArray[3] = new QuantityResource(STONE, 2);
        qArray[4] = new QuantityResource(SERVANT, 0);

        message = new WarehouseUpdateMessage("me", qArray);

        wl.updateWarehouseLight(message);
        wl.print();
        System.out.println("---------");

    }

}