package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.networking.message.*;

import java.util.List;

/**
 * State used to manage the storage of resources taken from the MarbleMarket
 */
public class MarketState extends PlayerState{

    List <Marble> marbleBuffer;

    public MarketState(Controller controller, List<Marble> marbleBuffer) {
        super(controller);
        this.marbleBuffer = marbleBuffer;
    }

    @Override
    public void performAction(Message message) {
        /* if (marbleBuffer.size() == 0){
            controller.setCurrentPlayerState(new AfterMainActionState(controller));
            return;
        } */
        if (message instanceof DiscardMarbleMessage){
            handleDiscardMarbleMessage((DiscardMarbleMessage) message);
        } else if (message instanceof StoreResourceInWarehouseMessage){
            handleStoreResourceInWarehouseMessage((StoreResourceInWarehouseMessage) message);
        } else if (message instanceof StoreResourceInExtraDepotMessage){
            handleStoreResourceInExtraDepotMessage((StoreResourceInExtraDepotMessage) message);
        } else if (message instanceof ConvertWhiteMarbleMessage){
            handleConvertWhiteMarbleMessage((ConvertWhiteMarbleMessage) message);
        } else {
            baseActionsDuringMainAction(message);
        }
    }

    /**
     * This method manages the will of the player of a discarding a marble taken after a market action.
     * @param message discard marble request from the player.
     */
    private void handleDiscardMarbleMessage(DiscardMarbleMessage message){
        int marbleIndex = message.getBufferIndex() - 1; //human readable --> programmer readable
        if(controller.canDiscardResourceFromBuffer(marbleIndex)){
            controller.discardResourceFromBuffer(marbleIndex);
            if (marbleBuffer.size() == 0)
                controller.setCurrentPlayerState(new AfterMainActionState(controller));
        } else {
            controller.sendErrorToCurrentPlayer("You can't discard this marble!");
        }
    }

    /**
     * This method manages the will of the player of storing a marble in the warehouse.
     * @param message insert resource in warehouse request from the player.
     */
    private void handleStoreResourceInWarehouseMessage(StoreResourceInWarehouseMessage message){
        int marbleIndex = message.getMarbleBufferIndex() -1; //human readable --> programmer readable
        int shelfIndex = message.getShelfIndex() -1; //human readable --> programmer readable
        if(controller.canAddResourceInWarehouseFromBuffer(marbleIndex, shelfIndex)){
            controller.addResourceInWarehouseFromBuffer(marbleIndex, shelfIndex);
            if (marbleBuffer.size() == 0)
                controller.setCurrentPlayerState(new AfterMainActionState(controller));
        } else {
            controller.sendErrorToCurrentPlayer("You can't store this marble in the given spot!");
        }
    }

    /**
     * This method manages the will of the player of storing a resource in an extra Depot.
     * @param message insert a resource in extra depot request from the player.
     */
    private void handleStoreResourceInExtraDepotMessage(StoreResourceInExtraDepotMessage message){
        int marbleBufferIndex = message.getMarbleBufferIndex() - 1; //human readable --> programmer readable
        if (controller.canAddResourceInExtraDepotFromBuffer(marbleBufferIndex)){
            controller.addResourceInExtraDepotFromBuffer(marbleBufferIndex);
            if (marbleBuffer.size() == 0)
                controller.setCurrentPlayerState(new AfterMainActionState(controller));
        } else {
            controller.sendErrorToCurrentPlayer("You can't store this marble in the given spot!");
        }
    }

    /**
     * This method manages the will of the player of converting a white marble in the buffer of resources he has to store/discard after a market action.
     * @param message convert white marble request from the player.
     */
    private void handleConvertWhiteMarbleMessage(ConvertWhiteMarbleMessage message){
        int marbleBufferIndex = message.getMarbleIndex() -1; //human readable --> programmer readable
        if (controller.canConvertWhiteMarble(marbleBufferIndex)){
            controller.convertWhiteMarble(((ConvertWhiteMarbleMessage) message).getResourceToConvert(), marbleBufferIndex);
        } else {
            controller.sendErrorToCurrentPlayer("You can't convert this marble!");
        }
    }

}
