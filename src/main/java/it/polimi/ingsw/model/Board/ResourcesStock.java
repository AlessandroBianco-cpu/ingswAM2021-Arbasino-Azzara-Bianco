package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceSpot;
import it.polimi.ingsw.model.ResourceType;

import java.util.HashMap;
import java.util.Map;

/**
 * The class represents a summary of the total number of resources a player owns
 * in order to make faster the access to these quantity and improve the performances
 * of actions involving checks/counts about resources
 */
public class ResourcesStock implements ResourceSpot {

    private final Map<ResourceType, Integer> resources = new HashMap<>();

    public ResourcesStock(){
        resources.put(ResourceType.SERVANT, 50);
        resources.put(ResourceType.STONE, 50);
        resources.put(ResourceType.SHIELD, 50);
        resources.put(ResourceType.COIN, 50);
    }

    /**
     * This method adds the given QuantityResource to the appropriate location of the ResCounter
     * @param resource quantityResource to add given after production
     */
    public void increaseStock(QuantityResource resource){
        resources.put(resource.getResourceType(), resources.get(resource.getResourceType()) + resource.getQuantity());
    }

    /**
     * Thi method is use for count how many resource  you have got
     * @return the number of your total resources
     */
    public int getTotalNumberOfResources() { return resources.get(ResourceType.SERVANT)+resources.get(ResourceType.SHIELD)+resources.get(ResourceType.COIN)+resources.get(ResourceType.STONE); }

    /**
     * This method removes the given Resource to the appropriate location of the ResCounter
     * @param resource resource to remove
     */
    public void decreaseStock(QuantityResource resource) {
        resources.put(resource.getResourceType(), resources.get(resource.getResourceType()) - resource.getQuantity());
    }

    @Override
    public boolean hasEnoughResources(QuantityResource quantityResource) { return resources.get(quantityResource.getResourceType()) >= quantityResource.getQuantity(); }

    /**
     *
     * @param color The type of the resource looked for
     * @return the number of Resources of the given type inside the Resource Stock
     */
    public int getNumberOfResource(ResourceType color) {
        return resources.get(color);
    }

}
