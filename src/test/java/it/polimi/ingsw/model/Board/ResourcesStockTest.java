package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.QuantityResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

class ResourcesStockTest {

    ResourcesStock resourcesStock;

    @BeforeEach
    public void setup(){
        resourcesStock = new ResourcesStock();
    }

    @Test
    void increaseStock() {
        resourcesStock.increaseStock(new QuantityResource(SHIELD, 3));
        resourcesStock.increaseStock(new QuantityResource(COIN, 2));
        assertEquals(2, resourcesStock.getNumberOfResource(COIN));
        assertEquals(0, resourcesStock.getNumberOfResource(SERVANT));
        assertEquals(3, resourcesStock.getNumberOfResource(SHIELD));
        assertEquals(0, resourcesStock.getNumberOfResource(STONE));
        resourcesStock.increaseStock(new QuantityResource(SHIELD, 2));
        assertEquals(2, resourcesStock.getNumberOfResource(COIN));
        assertEquals(0, resourcesStock.getNumberOfResource(SERVANT));
        assertEquals(5, resourcesStock.getNumberOfResource(SHIELD));
        assertEquals(0, resourcesStock.getNumberOfResource(STONE));
    }

    @Test
    void getTotalNumberOfResources() {
        resourcesStock.increaseStock(new QuantityResource(SHIELD, 3));
        resourcesStock.increaseStock(new QuantityResource(COIN, 2));
        resourcesStock.increaseStock(new QuantityResource(SHIELD, 2));
        assertEquals(7, resourcesStock.getTotalNumberOfResources());
        resourcesStock.increaseStock(new QuantityResource(SERVANT, 1));
        assertEquals(8, resourcesStock.getTotalNumberOfResources());
    }

    @Test
    void decreaseStock(){
        resourcesStock.increaseStock(new QuantityResource(SHIELD, 3));
        resourcesStock.increaseStock(new QuantityResource(COIN, 2));
        resourcesStock.increaseStock(new QuantityResource(SHIELD, 2));
        resourcesStock.decreaseStock(new QuantityResource(SHIELD, 2));
        assertEquals(2, resourcesStock.getNumberOfResource(COIN));
        assertEquals(0, resourcesStock.getNumberOfResource(SERVANT));
        assertEquals(3, resourcesStock.getNumberOfResource(SHIELD));
        assertEquals(0, resourcesStock.getNumberOfResource(STONE));
        resourcesStock.decreaseStock(new QuantityResource(COIN, 2));
        resourcesStock.decreaseStock(new QuantityResource(SHIELD, 3));
        assertEquals(0, resourcesStock.getNumberOfResource(COIN));
        assertEquals(0, resourcesStock.getNumberOfResource(SERVANT));
        assertEquals(0, resourcesStock.getNumberOfResource(SHIELD));
        assertEquals(0, resourcesStock.getNumberOfResource(STONE));
    }

    @Test
    void hasEnoughResources() {
        //initial check without manipulating the stock
        assertFalse(resourcesStock.hasEnoughResources(new QuantityResource(COIN, 1)));
        assertFalse(resourcesStock.hasEnoughResources(new QuantityResource(SERVANT, 1)));
        assertFalse(resourcesStock.hasEnoughResources(new QuantityResource(SHIELD, 1)));
        assertFalse(resourcesStock.hasEnoughResources(new QuantityResource(STONE, 1)));


        resourcesStock.increaseStock(new QuantityResource(SHIELD, 3));
        resourcesStock.increaseStock(new QuantityResource(COIN, 2));
        resourcesStock.increaseStock(new QuantityResource(SHIELD, 2));
        resourcesStock.decreaseStock(new QuantityResource(SHIELD, 2));


        assertTrue(resourcesStock.hasEnoughResources(new QuantityResource(COIN, 1)));
        assertTrue(resourcesStock.hasEnoughResources(new QuantityResource(COIN, 2)));
        assertFalse(resourcesStock.hasEnoughResources(new QuantityResource(COIN, 3)));
        assertTrue(resourcesStock.hasEnoughResources(new QuantityResource(COIN, 0)));
        
        assertFalse(resourcesStock.hasEnoughResources(new QuantityResource(SERVANT, 4)));

        assertTrue(resourcesStock.hasEnoughResources(new QuantityResource(SHIELD, 3)));
        assertFalse(resourcesStock.hasEnoughResources(new QuantityResource(SHIELD, 4)));

        assertFalse(resourcesStock.hasEnoughResources(new QuantityResource(STONE, 5)));
    }

}