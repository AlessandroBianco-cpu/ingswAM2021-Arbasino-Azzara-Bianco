package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.QuantityResource;

public abstract class Requirement {

    public boolean isCardRequirement(){
        return false;
    }

    public boolean isResourceRequirement(){
        return false;
    }

    public QuantityResource getResource() { return null; }

}



