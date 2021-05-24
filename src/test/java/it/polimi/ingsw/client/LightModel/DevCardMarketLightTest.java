package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.DevCardMarket;
import it.polimi.ingsw.networking.message.updateMessage.DevCardMarketUpdateMessage;
import org.junit.jupiter.api.Test;

class DevCardMarketLightTest {

    @Test
    void graphicWarehouseLightTest() {
        DevCardMarketLight dcml =  new DevCardMarketLight();
        DevCardMarket dcm = new DevCardMarket();
        DevCard[] cards = dcm.getTopCards();
        DevCardMarketUpdateMessage message = new DevCardMarketUpdateMessage(cards);
        dcml.updateDevCardMarketLight(message);
        dcml.print();
        cards[0] = null;
        message = new DevCardMarketUpdateMessage(cards);
        dcml.updateDevCardMarketLight(message);
        dcml.print();
    }
}