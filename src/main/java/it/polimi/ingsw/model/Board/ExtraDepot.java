package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceSpot;
import it.polimi.ingsw.model.ResourceType;

import static it.polimi.ingsw.utils.StaticUtils.EXTRA_DEPOT_MAX_SIZE;

/**
 * The class represents the Extra Slot provided by the special ability
 * of the ExtraDepot Leader Cards
 */
public class ExtraDepot implements ResourceSpot {

    private final QuantityResource slot;

    public ExtraDepot(ResourceType resourceType) {
        this.slot = new QuantityResource(resourceType, 0);
    }

    /**
     * Adds a resource in the ExtraDepot
     * @param quantityResource resource to add
     */
    public void addResource(QuantityResource quantityResource){
        slot.increase(quantityResource.getQuantity());
    }

    /**
     * Removes a certain quantity from the extraDepot
     * @param amount quantity to remove
     */
    public void removeQuantityResource(int amount){
            slot.decrease(amount);
    }

    public ResourceType getExtraDepotResourceType(){
        return slot.getResourceType();
    }

    public int getCurrentNumberOfResources(){
        return slot.getQuantity();
    }

    public int getMAX_SIZE() {
        return EXTRA_DEPOT_MAX_SIZE;
    }

    @Override
    public boolean hasEnoughResources(QuantityResource quantityResource) {
        return (quantityResource.getResourceType()==slot.getResourceType() &&
                slot.getQuantity()>= quantityResource.getQuantity());
    }
}
