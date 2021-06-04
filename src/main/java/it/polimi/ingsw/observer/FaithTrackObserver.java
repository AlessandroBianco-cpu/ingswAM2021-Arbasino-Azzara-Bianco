package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Board.FaithTrack;

/**
 * Observer interface used by VirtualView
 */
public interface FaithTrackObserver {

    void updateFaithTrack(FaithTrack faithTrack);

}
