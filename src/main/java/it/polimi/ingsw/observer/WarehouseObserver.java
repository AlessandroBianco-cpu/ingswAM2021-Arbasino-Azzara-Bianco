package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Board.Warehouse;

/**
 * Observer interface used by VirtualView
 */
public interface WarehouseObserver {

    void updateWarehouseState(Warehouse warehouse);

}
