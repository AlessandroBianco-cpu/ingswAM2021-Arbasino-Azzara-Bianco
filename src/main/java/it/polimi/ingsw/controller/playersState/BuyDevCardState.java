package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.DevCardPaymentMessage;
import it.polimi.ingsw.networking.message.InsertDevCardInDevSlotMessage;
import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.networking.message.PlacementDevCardMessage;
import it.polimi.ingsw.networking.message.updateMessage.CardPaymentResourceBufferUpdateMessage;

import java.util.ArrayList;
import java.util.List;


/**
 * State used to manage the purchase of a developmentCard
 */
public class BuyDevCardState extends PlayerState{

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
            if (message instanceof InsertDevCardInDevSlotMessage){
                handleInsertDevCardInDevSlotMessage((InsertDevCardInDevSlotMessage) message);
            }
        } else if (message instanceof DevCardPaymentMessage){
            handleDevCardPaymentMessage((DevCardPaymentMessage) message);
        } else {
            baseActionsDuringMainAction(message);
        }
    }


    /**
     * This method manages the will of the player of inserting a DevCard just bought in a certain development slot of his personal board.
     * @param message insert devCard request from the player.
     */
    private void handleInsertDevCardInDevSlotMessage(InsertDevCardInDevSlotMessage message){
        DevCard devCard = controller.getDevCardFromDevCardMarketIndex(devCardMarketIndex);
        if (controller.satisfiesDevCardInsertionRule(devCard,message.getIndexToAdd())){
            devCard = controller.popDevCardFromDevCardMarketIndex(devCardMarketIndex);
            controller.addBoughtDevCard(devCard, message.getIndexToAdd());
            controller.setCurrentPlayerState(new AfterMainActionState(controller));
        } else {
            controller.sendErrorToCurrentPlayer("You can't place this card in the given slot!");
        }
    }

    private void handleDevCardPaymentMessage(DevCardPaymentMessage message){
        applyPayment(message);
        if (listResourcesToPay.size() == 0)
            controller.sendMessageToCurrentPlayer(new PlacementDevCardMessage(controller.getDevCardFromDevCardMarketIndex(devCardMarketIndex)));
    }

    /**
     * Remove the resource paid by the player from the list of resources he has to pay in order to place the card
     * @param message message containing the resources used to pay
     */
    private void applyPayment(DevCardPaymentMessage message){
        int totalResourceGiven = 0;
        ResourceType givenResourceType = message.getResourceType();
        if (message.wantsToApplyDiscount()){
            if(controller.canApplyDiscount(givenResourceType)){
                totalResourceGiven -= controller.getDiscountAmount(givenResourceType); //because the discount is a negative number
            } else {
                controller.sendErrorToCurrentPlayer("You can't apply this discount!");
                return;
            }
        }

        int fromStrongbox = message.getFromStrongbox();
        int fromWarehouse = message.getFromWarehouse();
        int fromExtraDepot = message.getFromExtraDepot();

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
                controller.sendMessageToCurrentPlayer(new CardPaymentResourceBufferUpdateMessage(listResourcesToPay));
                return;
            }
        }
    }

}
