package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.LorenzoIlMagnifico.LorenzoIlMagnifico;

public interface LorenzoObserver {

    void updateLorenzoState(LorenzoIlMagnifico lorenzoIlMagnifico);

    void updateLorenzoPosition(LorenzoIlMagnifico lorenzoIlMagnifico);

}
