package it.polimi.ingsw.model.LorenzoIlMagnifico;

import java.io.Serializable;

public abstract class ActionToken implements Serializable {

    abstract void doAction();

    public abstract String toImage();

    public boolean isBlack(){ return false; }

    public boolean isBlue(){
        return false;
    }

    public boolean isGreen(){
        return false;
    }

    public boolean isPurple(){
        return false;
    }

    public boolean isYellow() { return false;
    }

    public boolean isShuffle(){
        return false;
    }


}
