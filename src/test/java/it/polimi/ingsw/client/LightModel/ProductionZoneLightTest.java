package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.Cards.*;
import it.polimi.ingsw.networking.message.updateMessage.ProductionZoneUpdateMessage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ProductionZoneLightTest {

    @Test
    void graphicProductionZoneTest() {
        LeaderDeck ld = new LeaderDeck();
        ExtraDevCard c1 = null;
        ExtraDevCard c2 = null;
        LeaderCard tmp;
        do{
            tmp = ld.popFirstCard();
            if(tmp.isExtraDevCard()){
                if(c1 == null)
                    c1 = (ExtraDevCard) tmp;
                else
                    c2 = (ExtraDevCard) tmp;
            }
        }while(c1 == null || c2 == null);
        DevDeck[] devDecks = new DevDeck[3];
        for(int i=0; i<3; i++){
            devDecks[i] = new DevDeck();
        }
        devDecks[0].greenLevelThreeDeck();
        devDecks[1].purpleLevelTwoDeck();
        devDecks[2].yellowLevelOneDeck();
        List<ExtraDevCard> leaders = new ArrayList<>();
        leaders.add(c1);
        leaders.add(c2);
        DevCard[] devs = new DevCard[3];
        devs[0] = devDecks[0].popFirstCard();
        devs[1] = devDecks[1].popFirstCard();
        devs[2] = devDecks[2].popFirstCard();
        ProductionZoneLight pz = new ProductionZoneLight();
        ProductionZoneUpdateMessage message = new ProductionZoneUpdateMessage("me", devs, leaders);
        pz.updateProductionZoneLight(message);
        pz.print();
        devs[1]=null;
        leaders.remove(c2);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        message = new ProductionZoneUpdateMessage("me", devs, leaders);
        pz.updateProductionZoneLight(message);
        pz.print();

    }


}