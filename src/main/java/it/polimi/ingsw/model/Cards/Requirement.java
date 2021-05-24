package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.QuantityResource;

import java.io.Serializable;

public abstract class Requirement implements Serializable {

    public boolean isCardRequirement(){
        return false;
    }

    public boolean isResourceRequirement(){
        return false;
    }

    public QuantityResource getResource() { return null; }

    public int getLevel() {
        return 0;
    }

    public int getQuantity() {
        return 0;
    }

    public DevCardColor getColor() {
        return null;
    }

}



