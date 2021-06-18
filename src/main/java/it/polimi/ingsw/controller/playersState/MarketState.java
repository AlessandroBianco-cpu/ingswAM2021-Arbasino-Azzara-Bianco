package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.model.ResourceType;
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
     * Handles a message of discardMarble type
     * @param message message containing the discardMarble action player wants to perform
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
     * Handles a message of storeResourceInWarehouse type
     * @param message message containing the storeResourceInWarehouse action player wants to perform
     */
    private void handleStoreResourceInWarehouseMessage(StoreResourceInWarehouseMessage message){
        int marbleIndex = message.getMarbleBufferIndex() - 1; //human readable --> programmer readable
        int shelfIndex = message.getShelfIndex() - 1; //human readable --> programmer readable
        if(controller.canAddResourceInWarehouseFromBuffer(marbleIndex, shelfIndex)){
            controller.addResourceInWarehouseFromBuffer(marbleIndex, shelfIndex);
            if (marbleBuffer.size() == 0)
                controller.setCurrentPlayerState(new AfterMainActionState(controller));
        } else {
            controller.sendErrorToCurrentPlayer("You can't store this marble in the given spot!");
        }
    }

    /**
     * Handles a message of storeResourceInExtraDepot type
     * @param message message containing the storeResourceInExtraDepot action player wants to perform
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
     * Handles a message of convertWhiteMarble type
     * @param message message containing the convertWhiteMarble action player wants to perform
     */
    private void handleConvertWhiteMarbleMessage(ConvertWhiteMarbleMessage message){
        int marbleBufferIndex = message.getMarbleIndex() - 1; //human readable --> programmer readable
        ResourceType resourceToConvert = message.getResourceToConvert();
        if (controller.canConvertWhiteMarble(resourceToConvert, marbleBufferIndex)){
            controller.convertWhiteMarble(resourceToConvert, marbleBufferIndex);
        } else {
            controller.sendErrorToCurrentPlayer("You can't convert this marble!");
        }
    }

}
