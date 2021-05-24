package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.DevCardPayment;
import it.polimi.ingsw.networking.message.InsertDevCardInDevSlot;
import it.polimi.ingsw.networking.message.Message;

import java.util.ArrayList;
import java.util.List;

public class BuyDevCardState  extends  PlayerState{

    int devCardMarketIndex;
    List<QuantityResource> totalResourcesToPay;
    List<QuantityResource> listResourcesToPay;

    public BuyDevCardState(Controller controller, int devCardMarketIndex) {
        super(controller);
        this.devCardMarketIndex = devCardMarketIndex;
        this.totalResourcesToPay = controller.getDevCardCostFromMarketIndex(devCardMarketIndex);
        this.listResourcesToPay = new ArrayList<>(totalResourcesToPay);
    }


    @Override
    public void performAction(Message message) {
        if (listResourcesToPay.size() == 0){
            if (message instanceof InsertDevCardInDevSlot){
                DevCard devCard = controller.getDevCardFromDevCardMarketIndex(devCardMarketIndex);
                if (controller.satisfiesDevCardInsertionRule(devCard,
                        ((InsertDevCardInDevSlot) message).getIndexToAdd() )){
                    devCard = controller.popDevCardFromDevCardMarketIndex(devCardMarketIndex);
                    controller.addBoughtDevCard(devCard, ((InsertDevCardInDevSlot) message).getIndexToAdd());
                    controller.setCurrentPlayerState(new AfterMainActionState(controller));
                } else {
                    controller.sendErrorToCurrentPlayer("You can't place this card in the given slot!");
                }
            }
        } else if (message instanceof DevCardPayment){
            applyPayment(message);
            if (listResourcesToPay.size() == 0)
                controller.sendErrorToCurrentPlayer("You can now place the Dev Card");
        } else {
            baseActionsDuringMainAction(message);
        }
    }

    private void applyPayment(Message message){
        int totalResourceGiven = 0;
        ResourceType givenResourceType = ((DevCardPayment) message).getResourceType();
        if (((DevCardPayment) message).wantsToApplyDiscount()){
            if(controller.canApplyDiscount(givenResourceType)){
                totalResourceGiven -= controller.getDiscountAmount(givenResourceType); //because the discount is a negative number
            } else {
                controller.sendErrorToCurrentPlayer("You can't apply this discount!");
                return;
            }
        }

        int fromStrongbox = ((DevCardPayment) message).getFromStrongbox();
        int fromWarehouse = ((DevCardPayment) message).getFromWarehouse();
        int fromExtraDepot = ((DevCardPayment) message).getFromExtraDepot();

        if (!(controller.playerHasEnoughResourcesInStrongbox(new QuantityResource(givenResourceType, fromStrongbox))
            && controller.playerHasEnoughResourcesInWarehouse(new QuantityResource(givenResourceType, fromWarehouse))
            && controller.playerHasEnoughResourcesInExtraDepot(new QuantityResource(givenResourceType, fromExtraDepot)))){
            controller.sendErrorToCurrentPlayer("You don't have the inserted resources in your resource spots!");
            return;
        }

        totalResourceGiven += (fromStrongbox + fromExtraDepot + fromWarehouse);

        for(QuantityResource qToPay : totalResourcesToPay){
            if (qToPay.getResourceType() == givenResourceType){
                if (qToPay.getQuantity() == totalResourceGiven){
                    controller.removeResourceFromPlayersResourceSpots(fromStrongbox, fromWarehouse, fromExtraDepot, givenResourceType);
                    removeResourcesFromListToPay(givenResourceType);

                } else{
                    controller.sendErrorToCurrentPlayer("Wrong quantity inserted!");
                }
                return;
            }
        }
    }


    /**
     * Method used to remove an element from a list. It has to be invoked only after having check
     * that the amount of resources given from the player is correct
     * @param resourceType type of resource to pay
     */
    private void removeResourcesFromListToPay(ResourceType resourceType){
        for(QuantityResource resourceToPay : listResourcesToPay){
            if (resourceToPay.getResourceType() == resourceType){
                listResourcesToPay.remove(resourceToPay);
                return;
            }
        }
    }

}
