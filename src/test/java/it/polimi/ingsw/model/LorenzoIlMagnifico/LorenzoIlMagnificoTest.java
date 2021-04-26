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

        blackToken.doAction();
        assertEquals(2, lory.getPosition(), "Incorrect position after BlackToken");
        shuffleToken.doAction();
        assertEquals(3, lory.getPosition(), "Incorrect position after ShuffleToken");
        blackToken.doAction();
        assertEquals(5, lory.getPosition(), "Incorrect position after BlackToken");

    }

    @Test
    public void destroyCardsWithSameColor(){
        SinglePlayerGame game = new SinglePlayerGame();
        LorenzoIlMagnifico lory = new LorenzoIlMagnifico(game);

        BlueToken token = new BlueToken(game);

        for(int i=0; i<6; i++){
            token.doAction();
        }

        assertTrue(game.getLorenzoWin());

    }


}