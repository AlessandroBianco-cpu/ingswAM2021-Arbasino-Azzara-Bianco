package it.polimi.ingsw.model.GameMode;

import it.polimi.ingsw.model.LorenzoIlMagnifico.LorenzoIlMagnifico;
import it.polimi.ingsw.model.MarbleMarket.*;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SinglePlayerGameTest {

    Game singlePlayerGame;
    LorenzoIlMagnifico lorenzoIlMagnifico;
    @BeforeEach
    void setup(){
        singlePlayerGame = new SinglePlayerGame();
        lorenzoIlMagnifico = ((SinglePlayerGame)singlePlayerGame).getLorenzoIlMagnifico();
    }

    @Test
    void lorenzoCorrectlyAdvance(){
        Player player = new Player(singlePlayerGame, "player", singlePlayerGame.getDevCardMarket());
        List<Player> listOfPlayers = singlePlayerGame.getPlayers();
        listOfPlayers.add(player);
        List<Marble> marbles = new ArrayList<>();
        marbles.add(new WhiteMarble());
        marbles.add(new RedMarble());
        marbles.add(new GreyMarble());
        marbles.add(new BlueMarble());

        //Player's marbles will be filtered: Red ones will update his position, white ones will be discarded if
        //player does not own a ConvertWhiteLeaderCard
        player.marbleFilter(marbles);

        player.discardResourceFromBuffer(1);
        player.discardResourceFromBuffer(0);

        assertEquals(2, lorenzoIlMagnifico.getPosition());
    }

}