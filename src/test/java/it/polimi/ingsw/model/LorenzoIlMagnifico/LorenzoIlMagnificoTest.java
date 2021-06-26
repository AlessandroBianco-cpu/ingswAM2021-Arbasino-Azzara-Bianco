package it.polimi.ingsw.model.LorenzoIlMagnifico;

import it.polimi.ingsw.model.GameMode.SinglePlayerGame;
import it.polimi.ingsw.model.LorenzoGameMethods;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LorenzoIlMagnificoTest {
    private LorenzoGameMethods lorenzoGameMethods;

    @Test
    public void blackTokenCorrectAdvance(){
        LorenzoIlMagnifico lory = new LorenzoIlMagnifico(lorenzoGameMethods);

        BlackToken blackToken = new BlackToken(lory);
        blackToken.doAction();

        assertEquals(2, lory.getPosition(), "Incorrect position after BlackToken");
    }

    @Test
    public void shuffleTokenCorrectAdvance(){
        LorenzoIlMagnifico lory = new LorenzoIlMagnifico(lorenzoGameMethods);
        TokenStack tokenStack = lory.getTokenStack();

        ShuffleToken shuffleToken = new ShuffleToken(lory, tokenStack);
        shuffleToken.doAction();

        assertEquals(1, lory.getPosition(), "Incorrect advance after Shuffle Token");
    }

    @Test
    public void mixedAdvanceTokens(){
        LorenzoIlMagnifico lory = new LorenzoIlMagnifico(lorenzoGameMethods);
        TokenStack tokenStack = lory.getTokenStack();

        BlackToken blackToken = new BlackToken(lory);
        ShuffleToken shuffleToken = new ShuffleToken(lory, tokenStack);

        assertTrue(blackToken.isBlack());
        blackToken.doAction();
        assertEquals(2, lory.getPosition(), "Incorrect position after BlackToken");
        shuffleToken.doAction();
        assertEquals(3, lory.getPosition(), "Incorrect position after ShuffleToken");
        blackToken.doAction();
        assertEquals(5, lory.getPosition(), "Incorrect position after BlackToken");
        assertEquals("blackToken.png",blackToken.toImage());
    }

    @Test
    public void destroyCardsWithBlueColor(){
        SinglePlayerGame game = new SinglePlayerGame();
        game.vaticanReport();
        BlueToken token = new BlueToken(game);
        assertTrue(token.isBlue());
        assertEquals("blueToken.png",token.toImage());

        for(int i=0; i<6; i++){
            token.doAction();
        }
        game.vaticanReport();
        assertTrue(game.isLorenzoWinner());
    }

    @Test
    public void destroyCardsWithYellowPurpleColor(){
        SinglePlayerGame game = new SinglePlayerGame();

        YellowToken yellowToken = new YellowToken(game);
        assertTrue(yellowToken.isYellow());
        assertEquals("yellowToken.png",yellowToken.toImage());
        PurpleToken purpleToken = new PurpleToken(game);
        assertTrue(purpleToken.isPurple());
        assertEquals("purpleToken.png",purpleToken.toImage());

        for(int i=0; i<3; i++){
            purpleToken.doAction();
            yellowToken.doAction();
        }

    }
}