package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.ActivateProductionMessage;
import it.polimi.ingsw.networking.message.BuyCardMessage;
import it.polimi.ingsw.networking.message.InsertMarbleMessage;
import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.networking.message.updateMessage.CardPaymentResourceBufferUpdateMessage;
import it.polimi.ingsw.networking.message.updateMessage.ProductionResourceBufferUpdateMessage;

import java.util.List;

/**
 * State before the player has chosen the Main Action (takeResourcesFromMarket || buyDevCard || activateProduction)
 */
public class BeforeMainActionState extends PlayerState{

    public BeforeMainActionState(Controller controller) {
        super(controller);
    }

    @Override
    public void performAction(Message message) {
        if (message instanceof InsertMarbleMessage){
            handleInsertMarbleMessage((InsertMarbleMessage) message);
        } else if (message instanceof BuyCardMessage){
            handleBuyCardMessage((BuyCardMessage) message);
        } else if (message instanceof ActivateProductionMessage){
            handleActivateProductionMessage((ActivateProductionMessage) message);
        } else baseActionsOutsideMainAction(message);
    }

    /**
     * Handles a message of marble insertion type
     * @param message message containing the insertMarble action player wants to perform
     */
    private void handleInsertMarbleMessage (InsertMarbleMessage message){
        int index = message.getPosition() - 1; //human readable --> programmer readable
        if (message.isRowInsertion()){
            if (index < 0 || index > 2){
                controller.sendErrorToCurrentPlayer("Wrong Row Index!");
            } else{
                List<Marble> marbleBuffer = controller.insertMarbleInRow(index);
                if (marbleBuffer.size() == 0)
                    controller.setCurrentPlayerState(new AfterMainActionState(controller));
                else
                    controller.setCurrentPlayerState(new MarketState(controller, marbleBuffer));
            }
        } else { //it is a ColInsertion
            if (index < 0 || index > 3){
                controller.sendErrorToCurrentPlayer("Wrong Column Index!");
            }else{
                List<Marble> marbleBuffer = controller.insertMarbleInCol(index);
                if(marbleBuffer.size() == 0)
                    controller.setCurrentPlayerState(new AfterMainActionState(controller));
                else
                    controller.setCurrentPlayerState(new MarketState(controller, marbleBuffer));
            }
        }
    }


    /**
     * Handles a message of buyCard type
     * @param message message containing the buyCard action player wants to perform
     */
    private void handleBuyCardMessage(BuyCardMessage message){
        int index = message.getIndexCard() - 1; //human readable --> programmer readable
        if (controller.canBuyDevCardFromMarket(index)){
            controller.sendMessageToCurrentPlayer(new CardPaymentResourceBufferUpdateMessage(controller.getDevCardCostFromMarketIndex(index)));
            controller.setCurrentPlayerState(new BuyDevCardState(controller, index));
        }else{
            controller.sendErrorToCurrentPlayer("You cannot buy this devCard!");
        }
    }

    /**
     * Handles a message of activateProduction type
     * @param message message containing the activateProduction action player wants to perform
     */
    private void handleActivateProductionMessage(ActivateProductionMessage message){
        List<Integer> prodSlotIndexes = message.getIndexes();
        if(prodSlotIndexes.contains(0)){
            controller.setBasePowerInput(message.getFirstBaseInput(), message.getSecondBaseInput());
            controller.setBasePowerOutput(message.getBaseOutput());
        }if(prodSlotIndexes.contains(4) && prodSlotIndexes.contains(5)) {
            List<ResourceType> leaderOutput = message.getLeaderOutput();
            for (int i = 0; i<leaderOutput.size(); i++){
                controller.setLeaderPowerOutput(i+4, leaderOutput.get(i));
            }
        }else if( prodSlotIndexes.contains(4) && !prodSlotIndexes.contains(5)){
            List<ResourceType> leaderOutput = message.getLeaderOutput();
            controller.setLeaderPowerOutput(4, leaderOutput.get(0));
        }else if( !prodSlotIndexes.contains(4) && prodSlotIndexes.contains(5)){
            List<ResourceType> leaderOutput = message.getLeaderOutput();
            controller.setLeaderPowerOutput(5, leaderOutput.get(0));
        }
        if (controller.canUseDevCards(prodSlotIndexes)) {
            controller.sendMessageToCurrentPlayer(new ProductionResourceBufferUpdateMessage(controller.getResourceToPayForProduction(prodSlotIndexes)));
            controller.setCurrentPlayerState(new ProductionState(controller, prodSlotIndexes));
        } else {
            controller.sendErrorToCurrentPlayer("You cannot activate this slots combo!");
        }
    }

}
