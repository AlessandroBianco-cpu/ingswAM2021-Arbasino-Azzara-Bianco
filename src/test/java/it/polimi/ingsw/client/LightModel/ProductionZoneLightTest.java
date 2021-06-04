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
        List<DevCard> firstDepot = devDecks[0].getCards();
        firstDepot.remove(3);
        List<DevCard> secondDepot = devDecks[1].getCards();
        secondDepot.remove(3);
        List<DevCard> thirdDepot = devDecks[2].getCards();
        thirdDepot.remove(3);

        ProductionZoneLight pz = new ProductionZoneLight();
        ProductionZoneUpdateMessage message = new ProductionZoneUpdateMessage("me", firstDepot,secondDepot,thirdDepot, leaders);
        pz.updateProductionZoneLight(message);
        pz.print();
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        firstDepot.remove(2);
        firstDepot.remove(1);
        secondDepot.remove(2);
        message = new ProductionZoneUpdateMessage("me", firstDepot,secondDepot,thirdDepot, leaders);
        pz.updateProductionZoneLight(message);
        pz.print();
        secondDepot = new ArrayList<>();
        leaders.remove(c2);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        message = new ProductionZoneUpdateMessage("me", firstDepot,secondDepot,thirdDepot, leaders);
        pz.updateProductionZoneLight(message);
        pz.print();

    }


}