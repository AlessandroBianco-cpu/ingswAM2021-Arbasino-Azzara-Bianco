package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.model.Cards.LeaderDeck;
import it.polimi.ingsw.networking.message.updateMessage.LeaderInHandUpdateMessage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class LeaderCardsInHandLightTest {

    @Test
    void graphicLeaderCardsTest() {
        LeaderDeck deck = new LeaderDeck();
        List<LeaderCard> prova = new ArrayList<>();
        for(int i=0; i<8; i++){
            prova.add(deck.popFirstCard());
        }
        LeaderInHandUpdateMessage message = new LeaderInHandUpdateMessage(prova, "me");
        LeaderCardsInHandLight lcl = new LeaderCardsInHandLight();
        lcl.updateLeaderInHand(message);
        lcl.print();
        prova.remove(6);
        lcl.print();
        prova.get(3).attivazioneCartaPerTestareCarteLight();
        lcl.print();
    }
}