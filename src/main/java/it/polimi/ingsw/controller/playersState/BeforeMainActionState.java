package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.ActivateProductionMessage;
import it.polimi.ingsw.networking.message.BuyCardMessage;
import it.polimi.ingsw.networking.message.InsertMarbleMessage;
import it.polimi.ingsw.networking.message.Message;

import java.util.List;

public class BeforeMainActionState extends PlayerState{

    public BeforeMainActionState(Controller controller) {
        super(controller);
    }

    @Override
    public void performAction(Message message) {
        if (message instanceof InsertMarbleMessage){
            int index = ((InsertMarbleMessage) message).getPosition() - 1; //human readable --> programmer readable
            if (((InsertMarbleMessage) message).isRowInsertion()){
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
        } else if (message instanceof BuyCardMessage){
            int index = ((BuyCardMessage) message).getIndexCard() - 1; //human readable --> programmer readable
            if (controller.canBuyDevCardFromMarket(index)){
                controller.setCurrentPlayerState(new BuyDevCardState(controller, index));
            }else{
                controller.sendErrorToCurrentPlayer("You cannot buy this devCard!");
            }
        } else if (message instanceof ActivateProductionMessage){
            List<Integer> prodSlotIndexes = ((ActivateProductionMessage) message).getIndexes();
            if(prodSlotIndexes.contains(0)){
                controller.setBasePowerInput(((ActivateProductionMessage) message).getFirstBaseInput(), ((ActivateProductionMessage) message).getSecondBaseInput());
                controller.setBasePowerOutput(((ActivateProductionMessage) message).getBaseOutput());
            }if(prodSlotIndexes.contains(4) && prodSlotIndexes.contains(5)) {
                List<ResourceType> leaderOutput = ((ActivateProductionMessage) message).getLeaderOutput();
                for (int i = 0; i<leaderOutput.size(); i++){
                    controller.setLeaderPowerOutput(i+4, leaderOutput.get(i));
                }
            }else if( prodSlotIndexes.contains(4) && !prodSlotIndexes.contains(5)){
                List<ResourceType> leaderOutput = ((ActivateProductionMessage) message).getLeaderOutput();
                    controller.setLeaderPowerOutput(4, leaderOutput.get(0));
            }else if( !prodSlotIndexes.contains(4) && prodSlotIndexes.contains(5)){
                List<ResourceType> leaderOutput = ((ActivateProductionMessage) message).getLeaderOutput();
                controller.setLeaderPowerOutput(5, leaderOutput.get(0));
            }
            if (controller.canUseDevCards(prodSlotIndexes)) {
                controller.setCurrentPlayerState(new ProductionState(controller, prodSlotIndexes));

            } else {
                controller.sendErrorToCurrentPlayer("You cannot activate this slots combo!");
            }
        } else baseActionsOutsideMainAction(message);
    }
}
