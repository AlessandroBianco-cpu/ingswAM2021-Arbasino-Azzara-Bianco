package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.networking.message.EndTurnMessage;
import it.polimi.ingsw.networking.message.Message;

/**
 * State after the player has chosen the Main Action (takeResourcesFromMarket || buyDevCard || activateProduction)
 */
public class AfterMainActionState extends PlayerState{

    public AfterMainActionState(Controller controller) {
        super(controller);
    }

    @Override
    public void performAction(Message message) {
        if (message instanceof EndTurnMessage){
            handleEndTurnMessage();
        } else {
            baseActionsOutsideMainAction(message);
        }
    }

    /**
     * Handles a message of endTurn type
     */
    private void handleEndTurnMessage(){
        controller.setCurrentPlayerWantToEndTurn(true);
    }

}
