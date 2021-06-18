package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.networking.message.*;

/**
 * Genetic state of the player
 */
public abstract class PlayerState {

    protected Controller controller;

    public PlayerState(Controller controller) {
        this.controller = controller;
    }

    public void performAction(Message message) {

    }

    /**
     * Actions player can do before or after the execution of a mainAction (i.e. activate or discard a leader card)
     * @param message action sent by the player
     */
    public void baseActionsOutsideMainAction(Message message){
        if (message instanceof ActivateLeaderMessage){
            handleActivateLeaderMessage((ActivateLeaderMessage) message);
        } else if (message instanceof DiscardLeaderMessage){
            handleDiscardLeaderMessage((DiscardLeaderMessage) message);
        } else {
            swapAndErrorActions(message);
        }
    }

    /**
     * Handles a message of activateLeader type
     * @param message message containing the activateLeader action player wants to perform
     */
    private void handleActivateLeaderMessage(ActivateLeaderMessage message){
        if (controller.canActivateLeaderCard( message.getLeaderIndex() - 1))  //human readable --> programmer readable
            controller.activateLeader(message.getLeaderIndex() - 1); //human readable --> programmer readable
        else
            controller.sendErrorToCurrentPlayer("You can't activate this leader card!");
    }

    /**
     * Handles a message of discardLeader type
     * @param message message containing the discardLeader action player wants to perform
     */
    private void handleDiscardLeaderMessage(DiscardLeaderMessage message){
        if (!(controller.discardLeaderCard(message.getLeaderIndex() - 1)))  //human readable --> programmer readable
            controller.sendErrorToCurrentPlayer("You can't discard this leader card!");
    }

    /**
     * Actions player can do during the execution of a mainAction (i.e. activate or discard a leader card)
     * @param message action sent by the player
     */
    public void baseActionsDuringMainAction(Message message){
        swapAndErrorActions(message);
    }

    /**
     * Actions player can do in every moment of its turn (i.e. swapping resources between his resource spots)
     * @param message actions sent by the player
     */
    private void swapAndErrorActions(Message message) {
        if (message instanceof WarehouseSwapMessage){
            handleWarehouseSwapMessage((WarehouseSwapMessage) message);
        } else if (message instanceof MoveFromExtraDepotMessage){
            handleMoveFromExtraDepotMessage((MoveFromExtraDepotMessage) message);
        } else if (message instanceof MoveToExtraDepotMessage){
            handleMoveToExtraDepotMessage((MoveToExtraDepotMessage) message);
        }else {
            controller.sendErrorToCurrentPlayer("Invalid command in current game state");
        }
    }

    /**
     * Handles a message of warehouseSwap type
     * @param message message containing the warehouseSwap action player wants to perform
     */
    private void handleWarehouseSwapMessage(WarehouseSwapMessage message){
        if (!(controller.swap(message.getFrom() - 1, message.getTo() - 1)))   //human readable --> programmer readable
            controller.sendErrorToCurrentPlayer("Invalid swap move!");
        else
            controller.sendResponseToCurrentPlayer("Correct swap");
    }

    /**
     * Handles a message of moveFromExtraDepot type
     * @param message message containing the moveFromExtraDepot action player wants to perform
     */
    private void handleMoveFromExtraDepotMessage(MoveFromExtraDepotMessage message){
        int extraDepotFrom = message.getExtraDepotFrom() - 1; //human readable --> programmer readable
        int depotTo = message.getWarehouseDepotTo() - 1;
        int quantity = message.getQuantity();

        if (controller.canMoveFromExtraDepotToWarehouse(extraDepotFrom, depotTo, quantity)){
            controller.moveFromExtraDepotToWarehouse(extraDepotFrom, depotTo, quantity);
            controller.sendResponseToCurrentPlayer("Correct swap");
        } else {
            controller.sendErrorToCurrentPlayer("Invalid swap move!");
        }
    }

    /**
     * Handles a message of moveToExtraDepot type
     * @param message message containing the moveToExtraDepot action player wants to perform
     */
    private void handleMoveToExtraDepotMessage(MoveToExtraDepotMessage message){
        int depotFrom = message.getDepotFrom() - 1;  //human readable --> programmer readable
        int extraDepotTo = message.getExtraDepotTo() - 1;
        int quantity = message.getQuantity();

        if (controller.canMoveFromWarehouseToExtraDepot(depotFrom, extraDepotTo, quantity)){
            controller.moveFromWarehouseToExtraDepot(depotFrom, extraDepotTo, quantity);
            controller.sendResponseToCurrentPlayer("Correct swap");
        } else {
            controller.sendErrorToCurrentPlayer("Invalid swap move!");
        }
    }

}
