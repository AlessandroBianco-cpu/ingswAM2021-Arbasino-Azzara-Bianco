package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Board.Strongbox;

/**
 * Observer interface used by VirtualView
 */
public interface StrongboxObserver {

    void updateStrongboxState(Strongbox strongbox);

}
