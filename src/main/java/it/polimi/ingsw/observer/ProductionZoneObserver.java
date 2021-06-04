package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Board.PersonalBoard;

/**
 * Observer interface used by VirtualView
 */
public interface ProductionZoneObserver {

    void updateProductionZoneState(PersonalBoard personalBoard);

}
