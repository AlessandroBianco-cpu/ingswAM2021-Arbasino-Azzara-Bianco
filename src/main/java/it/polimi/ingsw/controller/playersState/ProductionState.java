package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.networking.message.ResourcePlayerWantsToSpendMessage;

import java.util.ArrayList;
import java.util.List;

public class ProductionState extends PlayerState{

    private List<Integer> productionSlotIndexes;
    private List<QuantityResource> totalResourcesToPay;
    private List<QuantityResource> resourceToPay;

    public ProductionState(Controller controller, List<Integer> productionSlotIndexes) {
        super(controller);
        this.productionSlotIndexes = productionSlotIndexes;
        this.resourceToPay = controller.getResourceToPayForProduction(productionSlotIndexes);
        this.totalResourcesToPay = new ArrayList<>(resourceToPay);
    }

    @Override
    public void performAction(Message message) {
        if (message instanceof ResourcePlayerWantsToSpendMessage){
            ResourceType resourceType = ((ResourcePlayerWantsToSpendMessage) message).getResourceType();

            if (!(resourceTypeIsInListToPay(resourceType))){ //The resource should not be paid
                controller.sendErrorToCurrentPlayer("The resource selected must not be paid");
                return;
            }
            int fromWarehouse = ((ResourcePlayerWantsToSpendMessage) message).getFromWarehouse();
            int fromStrongbox = ((ResourcePlayerWantsToSpendMessage) message).getFromStrongbox();
            int fromExtraDepot = ((ResourcePlayerWantsToSpendMessage) message).getFromExtraDepot();

            if (controller.playerHasEnoughResourcesInStrongbox(new QuantityResource(resourceType, fromStrongbox))
                && controller.playerHasEnoughResourcesInWarehouse(new QuantityResource(resourceType, fromWarehouse))
                && controller.playerHasEnoughResourcesInExtraDepot(new QuantityResource(resourceType, fromExtraDepot))
                && (fromStrongbox + fromWarehouse + fromExtraDepot) == getQuantityToPayFromList(resourceType)  ) {

                controller.removeResourceFromPlayersResourceSpots(fromStrongbox, fromWarehouse, fromExtraDepot, resourceType);

                removeResourcesFromListToPay(fromStrongbox, fromWarehouse, fromExtraDepot, resourceType);

                //There are no more resources to pay, purchase correctly completed
                if(resourceToPay.size() == 0){
                    controller.addProducedResources(productionSlotIndexes);
                    controller.setCurrentPlayerState(new AfterMainActionState(controller));
                }

            }else{
                controller.sendErrorToCurrentPlayer("The combination of resources selected is not valid ");
            }
        } else baseActionsDuringMainAction(message);
    }

    private void removeResourcesFromListToPay(int fromStrongbox, int fromWarehouse, int fromExtraDepot, ResourceType resourceType){
        int totalToRemove = fromStrongbox + fromWarehouse + fromExtraDepot;
        for(QuantityResource resourceInListToPay : totalResourcesToPay){
            if (resourceInListToPay.getResourceType() == resourceType){
                if (resourceInListToPay.getQuantity() < totalToRemove){
                    controller.sendErrorToCurrentPlayer("You inserted more resources than the ones you have to pay!");
                } else{
                    resourceInListToPay.decrease(totalToRemove);
                    if (resourceInListToPay.getQuantity() == 0)
                        resourceToPay.remove(resourceInListToPay);
                }
                return;
            }
        }
    }

    private boolean resourceTypeIsInListToPay(ResourceType resourceType){
        for (QuantityResource q : resourceToPay){
            if (q.getResourceType() == resourceType)
                return true;
        }
        return false;
    }

    private int getQuantityToPayFromList (ResourceType resourceType){
        for (QuantityResource q : resourceToPay){
            if (q.getResourceType() == resourceType)
                return q.getQuantity();
        }
        return 0;
    }


}
