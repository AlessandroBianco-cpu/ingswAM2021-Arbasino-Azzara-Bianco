package it.polimi.ingsw.model.GameMode;


import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiPlayerGameTest {
    Game multiPlayerGame;
    Player alessandro;
    Player daniele;
    Player andrea;
    List<Player> list;


    @BeforeEach
    void setup(){
        multiPlayerGame = new MultiPlayerGame();
        alessandro = new Player("alessandro");
        daniele = new Player("daniele");
        andrea = new Player("andrea");
        list = new ArrayList<>();
        list.add(alessandro);
        list.add(daniele);
        list.add(andrea);

    }

    @Test
    void addPlayers() {
        multiPlayerGame.addPlayers(list);
        assertEquals(list,multiPlayerGame.getPlayers());
    }

    @Test
    void computeWinner() {
        multiPlayerGame.addPlayers(list);
        alessandro.setGame(multiPlayerGame);
        daniele.setGame(multiPlayerGame);
        andrea.setGame(multiPlayerGame);
        alessandro.increaseVictoryPoints(40);
        andrea.increaseVictoryPoints(20);
        assertEquals(alessandro, multiPlayerGame.computeWinnerPlayer());

        daniele.increaseVictoryPoints(10);
        andrea.increaseVictoryPoints(20); //alessandro and andrea have the same victory points
        alessandro.getPersonalBoard().getStrongbox().increaseStrongbox(new QuantityResource(ResourceType.SHIELD,50));
        assertEquals(alessandro, multiPlayerGame.computeWinnerPlayer());
    }


}