package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceSpot;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.ResourceType.NOTHING;

public class Warehouse implements ResourceSpot {

    private final int ERROR = -1;
    private final int NOT_EXISTS = -1;
    private final int NUMBER_OF_DEPOTS = 3;
    private QuantityResource[] depots;
    private List<ExtraDepot> extraDepots;

    public Warehouse(){
        depots=new QuantityResource[NUMBER_OF_DEPOTS];
        for(int i = 0; i < NUMBER_OF_DEPOTS; i++){
           depots[i] = new QuantityResource(NOTHING,0);
        }
        this.extraDepots = new ArrayList<>();
    }

    /**
     * The method swaps, if possible, the resources of the given shelves
     * @param depotFrom number of depot player wants to swap resources from
     * @param depotTo number of depot player wants to swap resources to
     */
    public boolean swap(int depotFrom, int depotTo) {
        if (!(depots[depotFrom].getQuantity() <= depotTo + 1 && depots[depotTo].getQuantity() <= depotFrom + 1))
            return false;

        QuantityResource tmp = depots[depotFrom];
        depots[depotFrom] = depots[depotTo];
        depots[depotTo] = tmp;

        return true;
    }

    public boolean canMoveFromWarehouseToExtraDepot(int depotFrom, int toExtraDepot, int quantity){
        return  depots[depotFrom].getQuantity() >= quantity
                && ((extraDepots.get(toExtraDepot).getCurrentNumberOfResources()+quantity) <= extraDepots.get(toExtraDepot).getMAX_SIZE()
                && depots[depotFrom].getResourceType() == extraDepots.get(toExtraDepot).getExtraDepotResourceType());
    }

    public void moveFromWarehouseToExtraDepot(int depotFrom, int toExtraDepot, int quantity){
        depots[depotFrom].decrease(quantity);
        if (depots[depotFrom].getQuantity() == 0)
            depots[depotFrom].setResourceType(NOTHING);
        extraDepots.get(toExtraDepot).addResource(new QuantityResource(extraDepots.get(toExtraDepot).getExtraDepotResourceType(), quantity));
    }

    public boolean canMoveFromExtraDepotToWarehouse(int extraDepotFrom, int depotTo, int quantity){
        return  extraDepots.get(extraDepotFrom).hasEnoughResources(new QuantityResource(extraDepots.get(extraDepotFrom).getExtraDepotResourceType(),quantity))
                && (depots[depotTo].getQuantity()+quantity) <= (depotTo+1)
                && (depots[depotTo].getResourceType() == extraDepots.get(extraDepotFrom).getExtraDepotResourceType()
                            || depots[depotTo].getResourceType()==NOTHING);
    }

    public void moveFromExtraDepotToWarehouse(int extraDepotFrom, int depotTo, int quantity){
        extraDepots.get(extraDepotFrom).removeQuantityResource(quantity);
        for(int i=0; i < quantity; i++){
            addResource(extraDepots.get(extraDepotFrom).getExtraDepotResourceType(), depotTo);
        }
    }

    public boolean canAddResourceInWarehouse(ResourceType type, int shelfIndex){
        if(depots[shelfIndex].getQuantity() < shelfIndex + 1){
            int indexWithSameResource = indexWithSameResource(type);
            if (indexWithSameResource == NOT_EXISTS ){
                //true if there are no other depots of same resource type and there is space in the given depot
                //false if all depots are occupied
                return depots[shelfIndex].getResourceType() == NOTHING;
            }else if (indexWithSameResource == shelfIndex){
                //true if the given depot in input is of the same type and has enough space
                //false if the depot given has the same resource type but not any space available
                return depots[shelfIndex].getQuantity() < shelfIndex + 1;
            }
        } return false;
    }

    /**
     * This method adds the given resource in the given Depot
     * @param type type of resource to add
     * @param shelfIndex index of the depot in which caller wants to put the resource to add
     */
    public void addResource(ResourceType type, int shelfIndex) {
        //no other depot contains the same kind of resource and the chosen depot is free
        if (depots[shelfIndex].getResourceType() == NOTHING) {
            depots[shelfIndex].increase(1);
            depots[shelfIndex].setResourceType(type);
        } else if(depots[shelfIndex].getQuantity()<shelfIndex+1){
            //the shelf chosen is right and there's space to add the resource
            depots[shelfIndex].increase(1);
        }
    }

    /**
     * method used to retrieve the index of the depot containing resources of the same type given as parameter
     * @param type type of resource looking for
     * @return the index of the depot containing the resource type given as parameter
     */
    public int indexWithSameResource(ResourceType type){
        for(int i=0; i<NUMBER_OF_DEPOTS; i++){
            if(depots[i].getResourceType() == type)
                return i;
        }
        return NOT_EXISTS;
    }

    public QuantityResource getDepot(int shelfIndex){
        return depots[shelfIndex];
    }

    public QuantityResource getExtraDepot(int extraDepotIndex){
        return (new QuantityResource(extraDepots.get(extraDepotIndex).getExtraDepotResourceType(),extraDepots.get(extraDepotIndex).getCurrentNumberOfResources()));
    }

    /**
     * Method used to know if the Warehouse has enough resources to be withdrawn
     * @param requestedQuantityResource resource requested
     * @return true if the asked resource can actually be withdrawn
     */
    @Override
    public boolean hasEnoughResources(QuantityResource requestedQuantityResource) {
        int indexWithSameResource = indexWithSameResource(requestedQuantityResource.getResourceType());
        if (indexWithSameResource == NOT_EXISTS)
            return false;
        return depots[indexWithSameResource].getQuantity() >= requestedQuantityResource.getQuantity();
    }

    /**
     * Method used to remove resources from the Warehouse. This method should be used after that
     * hasEnoughResources returned true
     * @param quantityResource resource to remove
     */
    public void removeResourcesFromWarehouse(QuantityResource quantityResource){
        int indexWithSameResource = indexWithSameResource(quantityResource.getResourceType());
        depots[indexWithSameResource].decrease(quantityResource.getQuantity());
        if(depots[indexWithSameResource].getQuantity()==0)
            depots[indexWithSameResource].setResourceType(NOTHING);
    }

    /**
     * Method used to add an extraDepot after the activation of a ExtraDepot LeaderCard
     * @param resourceType type of the resource of the depot that will be created
     */
    public void addExtraDepot(ResourceType resourceType){
        extraDepots.add(new ExtraDepot(resourceType));
    }

    /**
     * This method is used to retrieve if the caller can add a given resource type in the extraDepotCard
     * @param resourceType type of resource player wants to add
     * @return true if can added, false otherwise
     */
    public boolean canAddInExtraDepot(ResourceType resourceType){
        int extraDepotIndex = getExtraDepotIndexByResourceType(resourceType);
        if (extraDepotIndex == NOT_EXISTS)
            return false;
        return extraDepots.get(extraDepotIndex).getCurrentNumberOfResources() < extraDepots.get(extraDepotIndex).getMAX_SIZE();
    }

    /**
     * This method adds the given resource into the corresponding extraDepot (it should be invoked after a true result
     * from the CanAddInExtraDepot method
     * @param resourceType resource to add
     */
    public void addInExtraDepot(ResourceType resourceType){
        extraDepots .get(getExtraDepotIndexByResourceType(resourceType))
                    .addResource(new QuantityResource(resourceType, 1));
    }


    /**
     * Method used to get a QuantityResource from the extraDepot of the same tipe of the quantity resource given in input
     * @param quantityResource Resources asked to withdraw
     */
    public void removeResourceFromExtraDepot(QuantityResource quantityResource){
        extraDepots .get(getExtraDepotIndexByResourceType(quantityResource.getResourceType()))
                    .removeQuantityResource(quantityResource.getQuantity());
    }

    public int getExtraDepotIndexByResourceType(ResourceType resourceType){
        for(int i = 0; i < extraDepots.size(); i++)
            if (extraDepots.get(i).getExtraDepotResourceType() == resourceType)
                return i;
        return NOT_EXISTS;
    }

    /**
     * Method used to know if there is an extraDepot of the given type and has enough resources to be withdrawn
     * @param quantityResource resource to withdraw
     * @return true if the resource can actually be withdrawn
     */
    public boolean extraDepotHasEnoughResources(QuantityResource quantityResource){
        int extraDepotIndex = getExtraDepotIndexByResourceType(quantityResource.getResourceType());
        if (extraDepotIndex == NOT_EXISTS)
            return false;
        return extraDepots.get(extraDepotIndex).hasEnoughResources(quantityResource);
    }

}
