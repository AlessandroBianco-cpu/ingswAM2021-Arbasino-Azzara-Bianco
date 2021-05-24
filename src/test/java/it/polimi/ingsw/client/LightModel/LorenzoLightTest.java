package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.GameMode.SinglePlayerGame;
import it.polimi.ingsw.model.LorenzoIlMagnifico.*;
import it.polimi.ingsw.networking.message.updateMessage.LorenzoUpdateMessage;
import org.junit.jupiter.api.Test;

class LorenzoLightTest {

    @Test
    void graphicLorenzoTest() {
        LorenzoLight lory = new LorenzoLight();
        SinglePlayerGame s = new SinglePlayerGame();
        LorenzoIlMagnifico lollo = new LorenzoIlMagnifico(s);
        lory.print();
        System.out.println("-----------------");
        int pos = 15;
        ActionToken token = new BlueToken(s);
        LorenzoUpdateMessage message = new LorenzoUpdateMessage(pos, token);
        lory.updateLorenzo(message);
        lory.print();
        System.out.println("-----------------");
        pos = 7;
        token = new BlackToken(lollo);
        message = new LorenzoUpdateMessage(pos, token);
        lory.updateLorenzo(message);
        lory.print();
        System.out.println("-----------------");
        pos = 22;
        token = new YellowToken(s);
        message = new LorenzoUpdateMessage(pos, token);
        lory.updateLorenzo(message);
        lory.print();
        System.out.println("-----------------");
        message = new LorenzoUpdateMessage(pos-8, null);
        lory.updateLorenzo(message);
        lory.print();
        System.out.println("-----------------");
    }

}