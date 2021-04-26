package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.Cards.DevCardColor;
import it.polimi.ingsw.model.Cards.DevDeck;

import java.util.HashMap;
import java.util.Map;

public class DevCardMarket {
    private final int NUMBER_OF_DECKS = 12;
    private final int NOT_AVAILABLE = -1;
    private DevDeck[] devDecks;

    private Map<DevCardColor, Integer> colorMap;
    public DevCardMarket(){
        colorMap = new HashMap<>();
        devDecks = new DevDeck[NUMBER_OF_DECKS];
        colorMap.put(DevCardColor.GREEN,0);
        colorMap.put(DevCardColor.BLUE,1);
        colorMap.put(DevCardColor.YELLOW,2);
        colorMap.put(DevCardColor.PURPLE,3);

        for (int i = 0; i < NUMBER_OF_DECKS; i++)
            devDecks[i] = new DevDeck();

        devDecks[0].greenLevelOneDeck();
        devDecks[1].blueLevelOneDeck();
        devDecks[2].yellowLevelOneDeck();
        devDecks[3].purpleLevelOneDeck();
        devDecks[4].greenLevelTwoDeck();
        devDecks[5].blueLevelTwoDeck();
        devDecks[6].yellowLevelTwoDeck();
        devDecks[7].purpleLevelTwoDeck();
        devDecks[8].greenLevelThreeDeck();
        devDecks[9].blueLevelThreeDeck();
        devDecks[10].yellowLevelThreeDeck();
        devDecks[11].purpleLevelThreeDeck();
    }

    public DevCard popDevCardFromIndex(int index){
        return devDecks[index].popFirstCard();
    }

    public DevCard getDevCardFromIndex(int index){
        return devDecks[index].getFirstCard();
    }

    public int colorParser(DevCardColor color){
        return colorMap.get(color);
    }

    public int firstAvailableDeckByColor(int colorCode){
        for(int index = colorCode; index<NUMBER_OF_DECKS; index+=4){
            if(devDecks[index].getDeckSize()>0){
                return index;
            }
        }
        return NOT_AVAILABLE;
    }

    public int getNumberOfDecks() {
        return NUMBER_OF_DECKS;
    }

    public DevDeck getDeckByIndex(int index){
        return devDecks[index];
    }

}
