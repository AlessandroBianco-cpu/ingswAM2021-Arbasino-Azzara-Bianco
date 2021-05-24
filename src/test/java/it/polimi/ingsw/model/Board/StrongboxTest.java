package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.QuantityResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

class StrongboxTest {

    Strongbox strongbox;

    @BeforeEach
    public void setup(){
        strongbox = new Strongbox();
    }

    @Test
    void testIncreaseStrongbox(){
        strongbox.increaseStrongbox(new QuantityResource(STONE,2));
        assertEquals(0, strongbox.getNumResource(COIN));
        assertEquals(0, strongbox.getNumResource(SERVANT));
        assertEquals(0, strongbox.getNumResource(SHIELD));
        assertEquals(2, strongbox.getNumResource(STONE));
        strongbox.increaseStrongbox(new QuantityResource(COIN, 2));
        assertEquals(2, strongbox.getNumResource(COIN));
        assertEquals(0, strongbox.getNumResource(SERVANT));
        assertEquals(0, strongbox.getNumResource(SHIELD));
        assertEquals(2, strongbox.getNumResource(STONE));
        strongbox.increaseStrongbox(new QuantityResource(SERVANT,1));
        assertEquals(2, strongbox.getNumResource(COIN));
        assertEquals(1, strongbox.getNumResource(SERVANT));
        assertEquals(0, strongbox.getNumResource(SHIELD));
        assertEquals(2, strongbox.getNumResource(STONE));
        strongbox.increaseStrongbox(new QuantityResource(STONE, 1));
        assertEquals(2, strongbox.getNumResource(COIN));
        assertEquals(1, strongbox.getNumResource(SERVANT));
        assertEquals(0, strongbox.getNumResource(SHIELD));
        assertEquals(3, strongbox.getNumResource(STONE));
    }

    @Test
    void decreaseStrongbox() {

        strongbox.increaseStrongbox(new QuantityResource(STONE,2));
        strongbox.increaseStrongbox(new QuantityResource(COIN, 2));
        strongbox.increaseStrongbox(new QuantityResource(SERVANT,1));

        strongbox.decreaseStrongbox(new QuantityResource(SERVANT, 1));
        strongbox.decreaseStrongbox(new QuantityResource(COIN,2));
        assertEquals(0, strongbox.getNumResource(COIN));
        assertEquals(0, strongbox.getNumResource(SERVANT));
        assertEquals(0, strongbox.getNumResource(SHIELD));
        assertEquals(2, strongbox.getNumResource(STONE));

    }

    @Test
    void hasEnoughResources() {
        strongbox.increaseStrongbox(new QuantityResource(STONE,2));
        strongbox.increaseStrongbox(new QuantityResource(COIN, 2));
        strongbox.increaseStrongbox(new QuantityResource(SERVANT,1));

        assertTrue(strongbox.hasEnoughResources(new QuantityResource(STONE, 1)));
        assertTrue(strongbox.hasEnoughResources(new QuantityResource(STONE, 2)));
        assertTrue(strongbox.hasEnoughResources(new QuantityResource(COIN, 2)));
        assertFalse(strongbox.hasEnoughResources(new QuantityResource(COIN, 4)));
        assertFalse(strongbox.hasEnoughResources(new QuantityResource(SERVANT, 5)));
    }

    @Test
    void getNumResource() {
        strongbox.increaseStrongbox(new QuantityResource(COIN, 2));
        strongbox.increaseStrongbox(new QuantityResource(SERVANT,1));

        assertEquals(2, strongbox.getNumResource(COIN));
        assertEquals(1, strongbox.getNumResource(SERVANT));
    }
}