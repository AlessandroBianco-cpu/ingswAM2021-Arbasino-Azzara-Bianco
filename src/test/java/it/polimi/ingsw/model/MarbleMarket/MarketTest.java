package it.polimi.ingsw.model.MarbleMarket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class MarketTest {

    Market market;
    Marble marble_0_0;
    Marble marble_0_1;
    Marble marble_0_2;
    Marble marble_0_3;
    Marble marble_1_0;
    Marble marble_1_1;
    Marble marble_1_2;
    Marble marble_1_3;
    Marble marble_2_0;
    Marble marble_2_1;
    Marble marble_2_2;
    Marble marble_2_3;
    Marble marbleLeft;

    @BeforeEach
    public void initAndSaveInitialState(){
        market = new Market();

        marble_0_0 = market.getMarbleByIndexes(0,0);
        marble_0_1 = market.getMarbleByIndexes(0,1);
        marble_0_2 = market.getMarbleByIndexes(0,2);
        marble_0_3 = market.getMarbleByIndexes(0,3);
        marble_1_0 = market.getMarbleByIndexes(1,0);
        marble_1_1 = market.getMarbleByIndexes(1,1);
        marble_1_2 = market.getMarbleByIndexes(1,2);
        marble_1_3 = market.getMarbleByIndexes(1,3);
        marble_2_0 = market.getMarbleByIndexes(2,0);
        marble_2_1 = market.getMarbleByIndexes(2,1);
        marble_2_2 = market.getMarbleByIndexes(2,2);
        marble_2_3 = market.getMarbleByIndexes(2,3);
        marbleLeft = market.getMarbleLeft();
    }

    @Test
    public void insertionInRow(){

        market.insertMarbleInRow(1);

        assertInstanceOf(market.getMarbleByIndexes(0,0).getClass(), marble_0_0);
        assertInstanceOf(market.getMarbleByIndexes(0,1).getClass(), marble_0_1);
        assertInstanceOf(market.getMarbleByIndexes(0,2).getClass(), marble_0_2);
        assertInstanceOf(market.getMarbleByIndexes(0,3).getClass(), marble_0_3);
        assertInstanceOf(market.getMarbleByIndexes(1,0).getClass(), marble_1_1);
        assertInstanceOf(market.getMarbleByIndexes(1,1).getClass(), marble_1_2);
        assertInstanceOf(market.getMarbleByIndexes(1,2).getClass(), marble_1_3);
        assertInstanceOf(market.getMarbleByIndexes(1,3).getClass(), marbleLeft);
        assertInstanceOf(market.getMarbleByIndexes(2,0).getClass(), marble_2_0);
        assertInstanceOf(market.getMarbleByIndexes(2,1).getClass(), marble_2_1);
        assertInstanceOf(market.getMarbleByIndexes(2,2).getClass(), marble_2_2);
        assertInstanceOf(market.getMarbleByIndexes(2,3).getClass(), marble_2_3);

        assertInstanceOf(market.getMarbleLeft().getClass(), marble_1_0);

    }

    @Test
    public void insertionInColumn(){

        market.insertMarbleInCol(1);

        assertInstanceOf(market.getMarbleByIndexes(0,0).getClass(), marble_0_0);
        assertInstanceOf(market.getMarbleByIndexes(0,1).getClass(), marble_1_1);
        assertInstanceOf(market.getMarbleByIndexes(0,2).getClass(), marble_0_2);
        assertInstanceOf(market.getMarbleByIndexes(0,3).getClass(), marble_0_3);
        assertInstanceOf(market.getMarbleByIndexes(1,0).getClass(), marble_1_0);
        assertInstanceOf(market.getMarbleByIndexes(1,1).getClass(), marble_2_1);
        assertInstanceOf(market.getMarbleByIndexes(1,2).getClass(), marble_1_2);
        assertInstanceOf(market.getMarbleByIndexes(1,3).getClass(), marble_1_3);
        assertInstanceOf(market.getMarbleByIndexes(2,0).getClass(), marble_2_0);
        assertInstanceOf(market.getMarbleByIndexes(2,1).getClass(), marbleLeft);
        assertInstanceOf(market.getMarbleByIndexes(2,2).getClass(), marble_2_2);
        assertInstanceOf(market.getMarbleByIndexes(2,3).getClass(), marble_2_3);

        assertInstanceOf(market.getMarbleLeft().getClass(), marble_0_1);
    }

    @Test
    public void insertionOnTheEdges(){ //insertion on outer column and row of the Market Matrix

        market.insertMarbleInCol(3);
        market.insertMarbleInRow(2);

        assertInstanceOf(market.getMarbleByIndexes(0,0).getClass(), marble_0_0);
        assertInstanceOf(market.getMarbleByIndexes(0,1).getClass(), marble_0_1);
        assertInstanceOf(market.getMarbleByIndexes(0,2).getClass(), marble_0_2);
        assertInstanceOf(market.getMarbleByIndexes(0,3).getClass(), marble_1_3);
        assertInstanceOf(market.getMarbleByIndexes(1,0).getClass(), marble_1_0);
        assertInstanceOf(market.getMarbleByIndexes(1,1).getClass(), marble_1_1);
        assertInstanceOf(market.getMarbleByIndexes(1,2).getClass(), marble_1_2);
        assertInstanceOf(market.getMarbleByIndexes(1,3).getClass(), marble_2_3);
        assertInstanceOf(market.getMarbleByIndexes(2,0).getClass(), marble_2_1);
        assertInstanceOf(market.getMarbleByIndexes(2,1).getClass(), marble_2_2);
        assertInstanceOf(market.getMarbleByIndexes(2,2).getClass(), marbleLeft);
        assertInstanceOf(market.getMarbleByIndexes(2,3).getClass(), marble_0_3);

        assertInstanceOf(market.getMarbleLeft().getClass(), marble_2_0);
    }

}