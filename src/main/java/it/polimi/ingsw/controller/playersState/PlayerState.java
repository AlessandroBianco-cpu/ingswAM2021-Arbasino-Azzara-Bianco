package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.networking.message.*;

public abstract class PlayerState {

    protected Controller controller;

    public PlayerState(Controller controller) {
        this.controller = controller;
    }

    public void performAction(Message message) {

    }

    public void baseActionsOutsideMainAction(Message message){
        if (message instanceof ActivateLeaderMessage){
            if (controller.canActivateLeaderCard(((ActivateLeaderMessage) message).getLeaderIndex()-1)) {   //human readable --> programmer readable
                controller.activateLeader(((ActivateLeaderMessage) message).getLeaderIndex()-1); //human readable --> programmer readable
            } else {
                controller.sendErrorToCurrentPlayer("You can't activate this leader card!");
            }
        } else if (message instanceof DiscardLeaderMessage){
            if (!(controller.discardLeaderCard(((DiscardLeaderMessage) message).getLeaderIndex()-1))){  //human readable --> programmer readable
                controller.sendErrorToCurrentPlayer("You can't discard this leader card!");
            }
        } else {
            swapAndErrorActions(message);
        }
    }

    private void swapAndErrorActions(Message message) {
        if (message instanceof WarehouseSwapMessage){
            if (!(controller.swap(((WarehouseSwapMessage) message).getFrom()-1, ((WarehouseSwapMessage) message).getTo()-1))){    //human readable --> programmer readable
                controller.sendErrorToCurrentPlayer("Invalid swap move!");
            }
        } else if (message instanceof MoveFromExtraDepotMessage){
            int extraDepotFrom = ((MoveFromExtraDepotMessage) message).getExtraDepotFrom() -1; //human readable --> programmer readable
            int depotTo = ((MoveFromExtraDepotMessage) message).getWarehouseDepotTo() -1;
            int quantity = ((MoveFromExtraDepotMessage) message).getQuantity() -1;

            if (controller.canMoveFromExtraDepotToWarehouse(extraDepotFrom, depotTo, quantity)){
                controller.moveFromExtraDepotToWarehouse(extraDepotFrom, depotTo, quantity);
            } else {
                controller.sendErrorToCurrentPlayer("Invalid swap move!");
            }
        } else if (message instanceof MoveToExtraDepotMessage){
            int depotFrom = ((MoveToExtraDepotMessage) message).getDepotFrom() -1;  //human readable --> programmer readable
            int extraDepotTo = ((MoveToExtraDepotMessage) message).getExtraDepotTo() -1;
            int quantity = ((MoveToExtraDepotMessage) message).getQuantity() -1;

            if (controller.canMoveFromWarehouseToExtraDepot(depotFrom, extraDepotTo, quantity)){
                controller.moveFromWarehouseToExtraDepot(depotFrom, extraDepotTo, quantity);
            } else {
                controller.sendErrorToCurrentPlayer("Invalid swap move!");
            }

        }else {
            controller.sendErrorToCurrentPlayer("Invalid command in current game state");
        }
    }

    public void baseActionsDuringMainAction(Message message){
        swapAndErrorActions(message);
    }


}
