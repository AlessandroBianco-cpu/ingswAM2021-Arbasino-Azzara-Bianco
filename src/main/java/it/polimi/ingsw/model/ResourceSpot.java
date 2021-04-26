package it.polimi.ingsw.model;

public interface ResourceSpot {

    /**
     * @param quantityResource Resource to look for
     * @return true if there are enough Resources of the given type inside the ResourceSpot
     */
    boolean hasEnoughResources(QuantityResource quantityResource);
}
