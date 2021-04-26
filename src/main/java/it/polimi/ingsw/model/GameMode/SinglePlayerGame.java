package it.polimi.ingsw.model.GameMode;

import it.polimi.ingsw.model.Cards.DevCardColor;
import it.polimi.ingsw.model.LorenzoGameMethods;
import it.polimi.ingsw.model.LorenzoIlMagnifico.LorenzoIlMagnifico;
import it.polimi.ingsw.model.Player;

public class SinglePlayerGame extends Game implements LorenzoGameMethods {

    private LorenzoIlMagnifico lorenzoIlMagnifico;
    private final int NOT_AVAILABLE_DECK = -1;
    private boolean lorenzoIsWinner;

    public SinglePlayerGame() {
        lorenzoIlMagnifico = new LorenzoIlMagnifico(this);
        lorenzoIsWinner = false;
    }

    @Override
    public void setLorenzoWin(){
        lorenzoIsWinner = true;
    }

    public boolean getLorenzoWin(){return lorenzoIsWinner;}

    @Override
    public void throwCardByColor(DevCardColor color){
        final int cardsToDestroy = 2;
        int destroyedCards = 0;
        int colorCode = devCardMarket.colorParser(color);
        int indexAvailable = devCardMarket.firstAvailableDeckByColor(colorCode);
        while(destroyedCards < cardsToDestroy && indexAvailable != NOT_AVAILABLE_DECK){
            devCardMarket.getDeckByIndex(indexAvailable).popFirstCard();
            destroyedCards++;
            indexAvailable = devCardMarket.firstAvailableDeckByColor(colorCode);
        }
        if(indexAvailable == NOT_AVAILABLE_DECK){
            setLorenzoWin();
        }
    }

    @Override
    public void advanceAfterDiscard(Player discarder) {
        lorenzoIlMagnifico.advance();
    }

    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }
}
