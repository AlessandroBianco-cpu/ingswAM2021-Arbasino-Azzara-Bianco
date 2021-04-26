package it.polimi.ingsw.messages;

import java.io.Serializable;

public class SpotsMessage implements Serializable {

    //set-up done
    private static final long serialVersionUID = -4988456853603982030L;

    private final String inStrongbox;
    private final String inDepot;
    private final String inExtraDepot;

    public SpotsMessage(String inStrongbox, String inDepot, String inExtraDepot) {
        this.inStrongbox = inStrongbox;
        this.inDepot = inDepot;
        this.inExtraDepot = inExtraDepot;
    }

    public String getInStrongbox() {
        return inStrongbox;
    }

    public String getInDepot() {
        return inDepot;
    }

    public String getInExtraDepot() {
        return inExtraDepot;
    }
}

