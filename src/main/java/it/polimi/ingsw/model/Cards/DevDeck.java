package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.QuantityResource;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static it.polimi.ingsw.model.Cards.DevCardColor.*;
import static it.polimi.ingsw.model.ResourceType.*;

public class DevDeck extends Deck{

    private List <DevCard> cards;
    private List<QuantityResource> costOne;
    private List<QuantityResource> costTwo;
    private List<QuantityResource> costThree;
    private List<QuantityResource> costFour;
    private List<QuantityResource> powerInputOne;
    private List<QuantityResource> powerInputTwo;
    private List<QuantityResource> powerInputThree;
    private List<QuantityResource> powerInputFour;
    private List<QuantityResource> powerOutputOne;
    private List<QuantityResource> powerOutputTwo;
    private List<QuantityResource> powerOutputThree;
    private List<QuantityResource> powerOutputFour;

    private final int ONE_LEVEL=1;
    private final int TWO_LEVEL=2;
    private final int THREE_LEVEL=3;

    public DevDeck() {
        cards = new LinkedList<>();
        costOne = new LinkedList<>();
        costTwo = new LinkedList<>();
        costThree = new LinkedList<>();
        costFour = new LinkedList<>();
        powerInputOne = new LinkedList<>();
        powerInputTwo = new LinkedList<>();
        powerInputThree = new LinkedList<>();
        powerInputFour = new LinkedList<>();
        powerOutputOne = new LinkedList<>();
        powerOutputTwo = new LinkedList<>();
        powerOutputThree = new LinkedList<>();
        powerOutputFour = new LinkedList<>();
    }

    @Override
    public DevCard popFirstCard() {
        return ((LinkedList<DevCard>) cards).pop();
    }

    public DevCard getFirstCard(){
        if(cards.size() > 0)
            return cards.get(0);
        return null;
    }

    public int getDeckSize(){
        return cards.size();
    }

    //functions that build the twelve development decks
    public void greenLevelOneDeck(){
        costOne.add(new QuantityResource(SHIELD,2));
        powerInputOne.add(new QuantityResource(COIN,1));
        powerOutputOne.add(new QuantityResource(FAITH,1));
        costTwo.add(new QuantityResource(SHIELD,1));
        costTwo.add(new QuantityResource(SERVANT,1));
        costTwo.add(new QuantityResource(STONE,1));
        powerInputTwo.add(new QuantityResource(STONE,1));
        powerOutputTwo.add(new QuantityResource(SERVANT,1));
        costThree.add(new QuantityResource(SHIELD,3));
        powerInputThree.add(new QuantityResource(SERVANT,2));
        powerOutputThree.add(new QuantityResource(COIN,1));
        powerOutputThree.add(new QuantityResource(SHIELD,1));
        powerOutputThree.add(new QuantityResource(STONE,1));
        costFour.add(new QuantityResource(SHIELD,2));
        costFour.add(new QuantityResource(COIN,2));
        powerInputFour.add(new QuantityResource(STONE,1));
        powerInputFour.add(new QuantityResource(SERVANT,1));
        powerOutputFour.add(new QuantityResource(COIN,2));
        powerOutputFour.add(new QuantityResource(FAITH,1));

        DevCard cardOne = new DevCard(1,ONE_LEVEL,17,costOne,GREEN,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(2,ONE_LEVEL,18,costTwo,GREEN,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(3,ONE_LEVEL,19,costThree,GREEN,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(4,ONE_LEVEL,20,costFour,GREEN,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);
    }
    public void greenLevelTwoDeck(){
        costOne.add(new QuantityResource(SHIELD,4));
        powerInputOne.add(new QuantityResource(STONE,1));
        powerOutputOne.add(new QuantityResource(FAITH,2));
        costTwo.add(new QuantityResource(SHIELD,3));
        costTwo.add(new QuantityResource(SERVANT,2));
        powerInputTwo.add(new QuantityResource(SHIELD,1));
        powerInputTwo.add(new QuantityResource(SERVANT,1));
        powerOutputTwo.add(new QuantityResource(STONE,3));
        costThree.add(new QuantityResource(SHIELD,5));
        powerInputThree.add(new QuantityResource(COIN,2));
        powerOutputThree.add(new QuantityResource(STONE,2));
        powerOutputThree.add(new QuantityResource(FAITH,2));
        costFour.add(new QuantityResource(SHIELD,3));
        costFour.add(new QuantityResource(COIN,3));
        powerInputFour.add(new QuantityResource(COIN,1));
        powerOutputFour.add(new QuantityResource(SHIELD,2));
        powerOutputFour.add(new QuantityResource(FAITH,1));

        DevCard cardOne = new DevCard(5,TWO_LEVEL,21,costOne,GREEN,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(6,TWO_LEVEL,22,costTwo,GREEN,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(7,TWO_LEVEL,23,costThree,GREEN,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(8,TWO_LEVEL,24,costFour,GREEN,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);
    }
    public void greenLevelThreeDeck(){
        costOne.add(new QuantityResource(SHIELD,6));
        powerInputOne.add(new QuantityResource(COIN,2));
        powerOutputOne.add(new QuantityResource(STONE,3));
        powerOutputOne.add(new QuantityResource(FAITH,2));
        costTwo.add(new QuantityResource(SHIELD,5));
        costTwo.add(new QuantityResource(SERVANT,2));
        powerInputTwo.add(new QuantityResource(COIN,1));
        powerInputTwo.add(new QuantityResource(SERVANT,1));
        powerOutputTwo.add(new QuantityResource(SHIELD,2));
        powerOutputTwo.add(new QuantityResource(STONE,2));
        powerOutputTwo.add(new QuantityResource(FAITH,1));
        costThree.add(new QuantityResource(SHIELD,7));
        powerInputThree.add(new QuantityResource(SERVANT,1));
        powerOutputThree.add(new QuantityResource(COIN,1));
        powerOutputThree.add(new QuantityResource(FAITH,3));
        costFour.add(new QuantityResource(SHIELD,4));
        costFour.add(new QuantityResource(COIN,4));
        powerInputFour.add(new QuantityResource(STONE,1));
        powerOutputFour.add(new QuantityResource(COIN,3));
        powerOutputFour.add(new QuantityResource(SHIELD,1));

        DevCard cardOne =new DevCard(9,THREE_LEVEL,25,costOne,GREEN,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(10,THREE_LEVEL,26,costTwo,GREEN,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(11,THREE_LEVEL,27,costThree,GREEN,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(12,THREE_LEVEL,28,costFour,GREEN,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);
    }
    public void blueLevelOneDeck(){
        costOne.add(new QuantityResource(COIN,2));
        powerInputOne.add(new QuantityResource(SHIELD,1));
        powerOutputOne.add(new QuantityResource(FAITH,1));
        costTwo.add(new QuantityResource(COIN,1));
        costTwo.add(new QuantityResource(SERVANT,1));
        costTwo.add(new QuantityResource(STONE,1));
        powerInputTwo.add(new QuantityResource(SERVANT,1));
        powerOutputTwo.add(new QuantityResource(STONE,1));
        costThree.add(new QuantityResource(COIN,3));
        powerInputThree.add(new QuantityResource(STONE,2));
        powerOutputThree.add(new QuantityResource(COIN,1));
        powerOutputThree.add(new QuantityResource(SERVANT,1));
        powerOutputThree.add(new QuantityResource(SHIELD,1));
        costFour.add(new QuantityResource(COIN,2));
        costFour.add(new QuantityResource(SERVANT,2));
        powerInputFour.add(new QuantityResource(SHIELD,1));
        powerInputFour.add(new QuantityResource(STONE,1));
        powerOutputFour.add(new QuantityResource(SERVANT,2));
        powerOutputFour.add(new QuantityResource(FAITH,1));

        DevCard cardOne = new DevCard(1,ONE_LEVEL,29,costOne,BLUE,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(2,ONE_LEVEL,30,costTwo, BLUE,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(3,ONE_LEVEL,31,costThree, BLUE,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(4,ONE_LEVEL,32,costFour, BLUE,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);
    }
    public void blueLevelTwoDeck(){
        costOne.add(new QuantityResource(COIN,4));
        powerInputOne.add(new QuantityResource(SERVANT,1));
        powerOutputOne.add(new QuantityResource(FAITH,2));
        costTwo.add(new QuantityResource(COIN,3));
        costTwo.add(new QuantityResource(STONE,2));
        powerInputTwo.add(new QuantityResource(COIN,1));
        powerInputTwo.add(new QuantityResource(STONE,1));
        powerOutputTwo.add(new QuantityResource(SERVANT,3));
        costThree.add(new QuantityResource(COIN,5));
        powerInputThree.add(new QuantityResource(SERVANT,2));
        powerOutputThree.add(new QuantityResource(SHIELD,2));
        powerOutputThree.add(new QuantityResource(FAITH,2));
        costFour.add(new QuantityResource(COIN,3));
        costFour.add(new QuantityResource(STONE,3));
        powerInputFour.add(new QuantityResource(SERVANT,1));
        powerOutputFour.add(new QuantityResource(STONE,2));
        powerOutputFour.add(new QuantityResource(FAITH,1));

        DevCard cardOne = new DevCard(5,TWO_LEVEL,33,costOne, BLUE,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(6,TWO_LEVEL,34,costTwo, BLUE,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(7,TWO_LEVEL,35,costThree, BLUE,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(8,TWO_LEVEL,36,costFour, BLUE,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);

    }
    public void blueLevelThreeDeck(){
        costOne.add(new QuantityResource(COIN,6));
        powerInputOne.add(new QuantityResource(SERVANT,2));
        powerOutputOne.add(new QuantityResource(SHIELD,3));
        powerOutputOne.add(new QuantityResource(FAITH,2));
        costTwo.add(new QuantityResource(COIN,5));
        costTwo.add(new QuantityResource(STONE,2));
        powerInputTwo.add(new QuantityResource(COIN,1));
        powerInputTwo.add(new QuantityResource(SHIELD,1));
        powerOutputTwo.add(new QuantityResource(SERVANT,2));
        powerOutputTwo.add(new QuantityResource(STONE,2));
        powerOutputTwo.add(new QuantityResource(FAITH,1));
        costThree.add(new QuantityResource(COIN,7));
        powerInputThree.add(new QuantityResource(STONE,1));
        powerOutputThree.add(new QuantityResource(SHIELD,1));
        powerOutputThree.add(new QuantityResource(FAITH,3));
        costFour.add(new QuantityResource(COIN,4));
        costFour.add(new QuantityResource(STONE,4));
        powerInputFour.add(new QuantityResource(SERVANT,1));
        powerOutputFour.add(new QuantityResource(COIN,1));
        powerOutputFour.add(new QuantityResource(SHIELD,3));

        DevCard cardOne = new DevCard(9,THREE_LEVEL,37,costOne, BLUE,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(10,THREE_LEVEL,38,costTwo, BLUE,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(11,THREE_LEVEL,39,costThree, BLUE,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(12,THREE_LEVEL,40,costFour, BLUE,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);
    }
    public void yellowLevelOneDeck(){
        costOne.add(new QuantityResource(STONE,2));
        powerInputOne.add(new QuantityResource(SERVANT,1));
        powerOutputOne.add(new QuantityResource(FAITH,1));
        costTwo.add(new QuantityResource(SHIELD,1));
        costTwo.add(new QuantityResource(STONE,1));
        costTwo.add(new QuantityResource(COIN,1));
        powerInputTwo.add(new QuantityResource(SHIELD,1));
        powerOutputTwo.add(new QuantityResource(COIN,1));
        costThree.add(new QuantityResource(STONE,3));
        powerInputThree.add(new QuantityResource(SHIELD,2));
        powerOutputThree.add(new QuantityResource(COIN,1));
        powerOutputThree.add(new QuantityResource(SERVANT,1));
        powerOutputThree.add(new QuantityResource(STONE,1));
        costFour.add(new QuantityResource(STONE,2));
        costFour.add(new QuantityResource(SHIELD,2));
        powerInputFour.add(new QuantityResource(COIN,1));
        powerInputFour.add(new QuantityResource(SERVANT,1));
        powerOutputFour.add(new QuantityResource(SHIELD,2));
        powerOutputFour.add(new QuantityResource(FAITH,1));

        DevCard cardOne = new DevCard(1,ONE_LEVEL,41,costOne,YELLOW,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(2,ONE_LEVEL,42,costTwo,YELLOW,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(3,ONE_LEVEL,43,costThree,YELLOW,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(4,ONE_LEVEL,44,costFour,YELLOW,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);
    }
    public void yellowLevelTwoDeck(){
        costOne.add(new QuantityResource(STONE,4));
        powerInputOne.add(new QuantityResource(SHIELD,1));
        powerOutputOne.add(new QuantityResource(FAITH,2));
        costTwo.add(new QuantityResource(STONE,3));
        costTwo.add(new QuantityResource(SHIELD,2));
        powerInputTwo.add(new QuantityResource(STONE,1));
        powerInputTwo.add(new QuantityResource(SHIELD,1));
        powerOutputTwo.add(new QuantityResource(COIN,3));
        costThree.add(new QuantityResource(STONE,5));
        powerInputThree.add(new QuantityResource(SHIELD,2));
        powerOutputThree.add(new QuantityResource(SERVANT,2));
        powerOutputThree.add(new QuantityResource(FAITH,2));
        costFour.add(new QuantityResource(STONE,3));
        costFour.add(new QuantityResource(SERVANT,3));
        powerInputFour.add(new QuantityResource(SHIELD,1));
        powerOutputFour.add(new QuantityResource(COIN,2));
        powerOutputFour.add(new QuantityResource(FAITH,1));

        DevCard cardOne = new DevCard(5,TWO_LEVEL,45,costOne,YELLOW,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(6,TWO_LEVEL,46,costTwo,YELLOW,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(7,TWO_LEVEL,47,costThree,YELLOW,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(8,TWO_LEVEL,48,costFour,YELLOW,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);
    }
    public void yellowLevelThreeDeck(){
        costOne.add(new QuantityResource(STONE,6));
        powerInputOne.add(new QuantityResource(SHIELD,2));
        powerOutputOne.add(new QuantityResource(SERVANT,3));
        powerOutputOne.add(new QuantityResource(FAITH,2));
        costTwo.add(new QuantityResource(STONE,5));
        costTwo.add(new QuantityResource(SERVANT,2));
        powerInputTwo.add(new QuantityResource(STONE,1));
        powerInputTwo.add(new QuantityResource(SERVANT,1));
        powerOutputTwo.add(new QuantityResource(COIN,2));
        powerOutputTwo.add(new QuantityResource(SHIELD,2));
        powerOutputTwo.add(new QuantityResource(FAITH,1));
        costThree.add(new QuantityResource(STONE,7));
        powerInputThree.add(new QuantityResource(SHIELD,1));
        powerOutputThree.add(new QuantityResource(SERVANT,1));
        powerOutputThree.add(new QuantityResource(FAITH,3));
        costFour.add(new QuantityResource(STONE,4));
        costFour.add(new QuantityResource(SERVANT,4));
        powerInputFour.add(new QuantityResource(SHIELD,1));
        powerOutputFour.add(new QuantityResource(STONE,1));
        powerOutputFour.add(new QuantityResource(SERVANT,3));

        DevCard cardOne = new DevCard(9,THREE_LEVEL,49,costOne,YELLOW,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(10,THREE_LEVEL,50,costTwo,YELLOW,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(11,THREE_LEVEL,51,costThree,YELLOW,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(12,THREE_LEVEL,52,costFour,YELLOW,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);
    }
    public void purpleLevelOneDeck(){
        costOne.add(new QuantityResource(SERVANT,2));
        powerInputOne.add(new QuantityResource(STONE,1));
        powerOutputOne.add(new QuantityResource(FAITH,1));
        costTwo.add(new QuantityResource(SHIELD,1));
        costTwo.add(new QuantityResource(COIN,1));
        costTwo.add(new QuantityResource(SERVANT,1));
        powerInputTwo.add(new QuantityResource(COIN,1));
        powerOutputTwo.add(new QuantityResource(SHIELD,1));
        costThree.add(new QuantityResource(SERVANT,3));
        powerInputThree.add(new QuantityResource(COIN,2));
        powerOutputThree.add(new QuantityResource(SERVANT,1));
        powerOutputThree.add(new QuantityResource(SHIELD,1));
        powerOutputThree.add(new QuantityResource(STONE,1));
        costFour.add(new QuantityResource(SERVANT,2));
        costFour.add(new QuantityResource(STONE,2));
        powerInputFour.add(new QuantityResource(COIN,1));
        powerInputFour.add(new QuantityResource(SHIELD,1));
        powerOutputFour.add(new QuantityResource(STONE,2));
        powerOutputFour.add(new QuantityResource(FAITH, 1));

        DevCard cardOne = new DevCard(1,ONE_LEVEL,53,costOne,PURPLE,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(2,ONE_LEVEL,54,costTwo,PURPLE,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(3,ONE_LEVEL,55,costThree,PURPLE,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(4,ONE_LEVEL,56,costFour,PURPLE,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);
    }
    public void purpleLevelTwoDeck(){
        costOne.add(new QuantityResource(SERVANT,4));
        powerInputOne.add(new QuantityResource(COIN, 1));
        powerOutputOne.add(new QuantityResource(FAITH,2));
        costTwo.add(new QuantityResource(SERVANT,3));
        costTwo.add(new QuantityResource(COIN,2));
        powerInputTwo.add(new QuantityResource(COIN,1));
        powerInputTwo.add(new QuantityResource(SERVANT,1));
        powerOutputTwo.add(new QuantityResource(SHIELD,3));
        costThree.add(new QuantityResource(SERVANT,5));
        powerInputThree.add(new QuantityResource(STONE,2));
        powerOutputThree.add(new QuantityResource(COIN,2));
        powerOutputThree.add(new QuantityResource(FAITH,2));
        costFour.add(new QuantityResource(SERVANT,3));
        costFour.add(new QuantityResource(SHIELD,3));
        powerInputFour.add(new QuantityResource(STONE,1));
        powerOutputFour.add(new QuantityResource(SERVANT,2));
        powerOutputFour.add(new QuantityResource(FAITH,1));

        DevCard cardOne = new DevCard(5,TWO_LEVEL,57,costOne,PURPLE,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(6,TWO_LEVEL,58,costTwo,PURPLE,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(7,TWO_LEVEL,59,costThree,PURPLE,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(8,TWO_LEVEL,60,costFour,PURPLE,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);
    }
    public void purpleLevelThreeDeck(){
        costOne.add(new QuantityResource(SERVANT,6));
        powerInputOne.add(new QuantityResource(STONE,2));
        powerOutputOne.add(new QuantityResource(COIN,3));
        powerOutputOne.add(new QuantityResource(FAITH,2));
        costTwo.add(new QuantityResource(SERVANT,5));
        costTwo.add(new QuantityResource(COIN,2));
        powerInputTwo.add(new QuantityResource(STONE,1));
        powerInputTwo.add(new QuantityResource(SHIELD,1));
        powerOutputTwo.add(new QuantityResource(COIN,2));
        powerOutputTwo.add(new QuantityResource(SERVANT,2));
        powerOutputTwo.add(new QuantityResource(FAITH,1));
        costThree.add(new QuantityResource(SERVANT,7));
        powerInputThree.add(new QuantityResource(COIN,1));
        powerOutputThree.add(new QuantityResource(STONE,1));
        powerInputThree.add(new QuantityResource(FAITH,3));
        costFour.add(new QuantityResource(SERVANT,4));
        costFour.add(new QuantityResource(SHIELD,4));
        powerInputFour.add(new QuantityResource(COIN,1));
        powerOutputFour.add(new QuantityResource(STONE,3));
        powerOutputFour.add(new QuantityResource(SERVANT,1));

        DevCard cardOne = new DevCard(9,THREE_LEVEL,61,costOne,PURPLE,powerInputOne,powerOutputOne);
        DevCard cardTwo = new DevCard(10, THREE_LEVEL,62,costTwo,PURPLE,powerInputTwo,powerOutputTwo);
        DevCard cardThree = new DevCard(11, THREE_LEVEL,63,costThree,PURPLE,powerInputThree,powerOutputThree);
        DevCard cardFour = new DevCard(12, THREE_LEVEL,64,costFour,PURPLE,powerInputFour,powerOutputFour);
        cards.add(cardOne);
        cards.add(cardTwo);
        cards.add(cardThree);
        cards.add(cardFour);
        Collections.shuffle(cards);

    }

    public List<DevCard> getCards() {
        return cards;
    }
}
