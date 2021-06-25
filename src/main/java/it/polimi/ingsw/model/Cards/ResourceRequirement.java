package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.QuantityResource;

/**
 * This class represents a requirement of resource type (i.e 2 SHIELDS)
 */
public class ResourceRequirement extends  Requirement{

    private final QuantityResource quantityResource;

    public ResourceRequirement(QuantityResource quantityResource) {
        this.quantityResource = quantityResource;
    }

    @Override
    public QuantityResource getResource() {
        return quantityResource;
    }

    @Override
    public boolean isResourceRequirement() {
        return true;
    }
}
