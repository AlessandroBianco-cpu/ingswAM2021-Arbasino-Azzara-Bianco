package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.QuantityResource;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    @Test
    public void swapAllDepotsInWarehouse() {
        System.out.println("\nexecuting swapAllDepotsWarehouse\n");
        Warehouse wh = new Warehouse();
        wh.addResource(SHIELD,0);
        wh.addResource(COIN,1);
        wh.addResource(STONE,2);
        wh.swap(0,1);
        wh.swap(0,2);
        wh.swap(1,2);
        wh.swap(2,0);
        wh.swap(1,0);
        wh.swap(2,1);

        //checking correct final status of the warehouse after the swaps
        assertEquals((new QuantityResource(COIN, 1)), wh.getDepot(0), "Wrong first shelf status");
        assertEquals((new QuantityResource(STONE, 1)), wh.getDepot(1), "Wrong second shelf status");
        assertEquals((new QuantityResource(SHIELD, 1)), wh.getDepot(2), "Wrong third shelf status");
    }

    @Test
    public void tryToAddExtraTypeOfResource(){
        System.out.println("\nexecuting tryToAddExtraTypeOfResource\n");
        Warehouse wh = new Warehouse();
        wh.addResource(SHIELD,0);
        wh.addResource(COIN,1);
        wh.addResource(STONE,2);

        //all the three insertion aren't possible
        assertFalse(wh.canAddResourceInWarehouse(SERVANT,0), "Resource incorrectly added");
        assertFalse(wh.canAddResourceInWarehouse(SERVANT,1), "Resource incorrectly added");
        assertFalse(wh.canAddResourceInWarehouse(SERVANT,2), "Resource incorrectly added");

        //checking correct final status of the warehouse, that should be the same status of the beginning
        assertEquals((new QuantityResource(SHIELD, 1)), wh.getDepot(0), "Wrong first shelf status");
        assertEquals((new QuantityResource(COIN, 1)), wh.getDepot(1), "Wrong second shelf status");
        assertEquals((new QuantityResource(STONE, 1)), wh.getDepot(2), "Wrong third shelf status");

    }

    @Test
    public void addingSomeResources(){
        System.out.println("\nexecuting addingSomeResources\n");
        Warehouse wh = new Warehouse();
        wh.addResource(SHIELD,0);
        wh.addResource(COIN,1);
        wh.addResource(STONE,2);

        assertFalse(wh.canAddResourceInWarehouse(SHIELD,0), "Shield incorrectly added");
        assertTrue(wh.canAddResourceInWarehouse(STONE,2), "No space");
        wh.addResource(STONE,2);

        //checking correct final status of the warehouse
        assertEquals((new QuantityResource(SHIELD, 1)), wh.getDepot(0), "Wrong first shelf status");
        assertEquals((new QuantityResource(COIN, 1)), wh.getDepot(1), "Wrong second shelf status");
        assertEquals((new QuantityResource(STONE, 2)), wh.getDepot(2), "Wrong third shelf status");
    }

    @Test
    public void swapMoreResources(){
        System.out.println("\nexecuting swapMoreResources\n");
        Warehouse wh = new Warehouse();
        wh.addResource(SHIELD,0);
        wh.addResource(COIN,1);
        wh.addResource(STONE,2);
        wh.addResource(STONE,2);
        assertTrue(wh.swap(0,1));//swap coin and shield
        assertFalse(wh.swap(0,2));//should print an error,can't put two resources in the first shelf
        assertTrue(wh.swap(1,2));//swap a shield with two stones


        //checking correct final status of the warehouse
        assertEquals((new QuantityResource(COIN, 1)), wh.getDepot(0), "Wrong first shelf status");
        assertEquals((new QuantityResource(STONE, 2)), wh.getDepot(1), "Wrong second shelf status");
        assertEquals((new QuantityResource(SHIELD, 1)), wh.getDepot(2), "Wrong third shelf status");
    }

    @Test
    public void addInFullShelf() {
        System.out.println("\nexecuting addInFullShelf\n");
        Warehouse wh = new Warehouse();
        wh.addResource(SHIELD, 0);
        wh.addResource(COIN, 1);
        wh.addResource(STONE, 2);
        assertFalse(wh.canAddResourceInWarehouse(SHIELD, 0), "Resource Incorrectly added");//can't add the  resource, the shelf is full

        //checking correct final status of the warehouse, that should be the same status of the beginning
        assertEquals((new QuantityResource(SHIELD, 1)), wh.getDepot(0), "Wrong first shelf status");
        assertEquals((new QuantityResource(COIN, 1)), wh.getDepot(1), "Wrong second shelf status");
        assertEquals((new QuantityResource(STONE, 1)), wh.getDepot(2), "Wrong third shelf status");
    }

    @Test
    public void emptyAllShelves() {
        System.out.println("\nexecuting emptyAllShelves\n");
        Warehouse wh = new Warehouse();
        wh.addResource(SHIELD, 0);
        wh.addResource(COIN, 1);
        wh.addResource(STONE, 2);
        wh.addResource(STONE, 2);

        assertFalse(wh.hasEnoughResources(new QuantityResource(STONE, 3)), "The resource can be removed");
        assertTrue(wh.hasEnoughResources(new QuantityResource(SHIELD,1)), "Not enough shields");
        wh.removeResourcesFromWarehouse(new QuantityResource(SHIELD,1));
        assertTrue(wh.hasEnoughResources(new QuantityResource(COIN,1)), "Not enough coins");
        wh.removeResourcesFromWarehouse(new QuantityResource(COIN,1));
        assertTrue(wh.hasEnoughResources(new QuantityResource(STONE,2)), "Not enough stones");
        wh.removeResourcesFromWarehouse(new QuantityResource(STONE,2));

        //set of assertFalse to verify that the warehouse is actually empty
        assertFalse(wh.hasEnoughResources(new QuantityResource(COIN, 1)), "At least one non empty shelf");
        assertFalse(wh.hasEnoughResources(new QuantityResource(STONE, 1)), "At least one non empty shelf");
        assertFalse(wh.hasEnoughResources(new QuantityResource(SHIELD, 1)), "At least one non empty shelf");
        assertFalse(wh.hasEnoughResources(new QuantityResource(SERVANT, 1)), "At least one non empty shelf");
    }

    @Test
    public void extraDepotsActions(){
        System.out.println("\nexecuting extraDepotsActions\n");
        Warehouse wh = new Warehouse();
        wh.addResource(SERVANT, 0);
        wh.addResource(COIN, 1);
        wh.addResource(COIN, 1);
        wh.addResource(STONE, 2);
        wh.addResource(STONE, 2);
        wh.addResource(STONE, 2);

        assertFalse(wh.extraDepotHasEnoughResources(new QuantityResource(STONE,1)));
        assertFalse(wh.canAddInExtraDepot(SHIELD), "Shield incorrectly stored");
        wh.addExtraDepot(SHIELD);
        assertTrue(wh.canAddInExtraDepot(SHIELD), "Shield can't be stored");
        wh.addInExtraDepot(SHIELD);
        assertFalse(wh.canMoveFromExtraDepotToWarehouse(0,0,1));
        assertFalse(wh.canMoveFromWarehouseToExtraDepot(0,0,1));
        wh.addExtraDepot(STONE);
        assertFalse(wh.canMoveFromWarehouseToExtraDepot(2,1,3));
        assertTrue(wh.canMoveFromWarehouseToExtraDepot(2,1,2));
        wh.moveFromWarehouseToExtraDepot(2,1,2);
        wh.addResource(STONE, 2);
        assertFalse(wh.canMoveFromExtraDepotToWarehouse(1,2,2));
        assertTrue(wh.canMoveFromExtraDepotToWarehouse(1,2,1));
        wh.moveFromExtraDepotToWarehouse(1,2,1);

        wh.addExtraDepot(SERVANT);
        assertTrue(wh.canMoveFromWarehouseToExtraDepot(0,2,1));
        wh.moveFromWarehouseToExtraDepot(0,2,1);
        assertTrue(wh.extraDepotHasEnoughResources(new QuantityResource(SERVANT,1)));
        wh.removeResourceFromExtraDepot(new QuantityResource(SERVANT,1));
        //checking correct final status of the warehouse and the extra Depots
        assertEquals(new QuantityResource(NOTHING,0), wh.getDepot(0));
        assertEquals(new QuantityResource(COIN,2), wh.getDepot(1));
        assertEquals(new QuantityResource(STONE,3), wh.getDepot(2));
        assertEquals(new QuantityResource(SHIELD,1), wh.getExtraDepot(0));
        assertEquals(new QuantityResource(STONE,1), wh.getExtraDepot(1));
        assertEquals(new QuantityResource(SERVANT,0), wh.getExtraDepot(2));

    }
}