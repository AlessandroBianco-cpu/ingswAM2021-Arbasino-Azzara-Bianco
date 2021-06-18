package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.updateMessage.MarketUpdateMessage;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketLightTest {

    @Test
    public void printMarket() {
        MarketLight market = new MarketLight();

        List<ResourceType> resourcesInMarket = new LinkedList<>();

        resourcesInMarket.add(ResourceType.NOTHING);
        resourcesInMarket.add(ResourceType.COIN);
        resourcesInMarket.add(ResourceType.STONE);
        resourcesInMarket.add(ResourceType.SHIELD);
        resourcesInMarket.add(ResourceType.SHIELD);
        resourcesInMarket.add(ResourceType.SERVANT);
        resourcesInMarket.add(ResourceType.SERVANT);
        resourcesInMarket.add(ResourceType.NOTHING);
        resourcesInMarket.add(ResourceType.STONE);
        resourcesInMarket.add(ResourceType.COIN);
        resourcesInMarket.add(ResourceType.NOTHING);
        resourcesInMarket.add(ResourceType.FAITH);

        ResourceType resourceLeft = ResourceType.NOTHING;

        MarketUpdateMessage message = new MarketUpdateMessage(resourcesInMarket, resourceLeft);

        market.updateMarketLight(message);
        market.print();
    }

}