package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.GameMode.Game;
import it.polimi.ingsw.model.GameMode.MultiPlayerGame;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.BeforeEach;

class PersonalBoardTest {
    Game game;
    Player player;
    PersonalBoard pb;


    @BeforeEach
    void setUp(){
        this.game = new MultiPlayerGame();
        this.player = new Player(game,"Alessandro",game.getDevCardMarket());
        this.pb = new PersonalBoard(player,game);
    }



}