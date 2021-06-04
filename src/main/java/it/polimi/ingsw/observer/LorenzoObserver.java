package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.LorenzoIlMagnifico.LorenzoIlMagnifico;

/**
 * Observer interface used by VirtualView
 */
public interface LorenzoObserver {

    void updateLorenzoState(LorenzoIlMagnifico lorenzoIlMagnifico);

    void updateLorenzoPosition(LorenzoIlMagnifico lorenzoIlMagnifico);

}
