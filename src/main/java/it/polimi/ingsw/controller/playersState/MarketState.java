package it.polimi.ingsw.controller.playersState;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.networking.message.*;

import java.util.List;

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
        if (message instanceof DiscardMarble){
            int marbleIndex = ((DiscardMarble) message).getBufferIndex() - 1; //human readable --> programmer readable
            if(controller.canDiscardResourceFromBuffer(marbleIndex)){
                controller.discardResourceFromBuffer(marbleIndex);
                if (marbleBuffer.size() == 0)
                    controller.setCurrentPlayerState(new AfterMainActionState(controller));
            } else{
                controller.sendErrorToCurrentPlayer("You can't discard this marble!");
            }
        } else if (message instanceof StoreResourceInWarehouse){
            int marbleIndex = ((StoreResourceInWarehouse) message).getMarbleBufferIndex() -1; //human readable --> programmer readable
            int shelfIndex = ((StoreResourceInWarehouse) message).getShelfIndex() -1; //human readable --> programmer readable
            if(controller.canAddResourceInWarehouseFromBuffer(marbleIndex, shelfIndex)){
                controller.addResourceInWarehouseFromBuffer(marbleIndex, shelfIndex);
                if (marbleBuffer.size() == 0)
                    controller.setCurrentPlayerState(new AfterMainActionState(controller));
            } else {
                controller.sendErrorToCurrentPlayer("You can't store this marble in the given spot!");
            }
        } else if (message instanceof StoreResourceInExtraDepot){
            int marbleBufferIndex = ((StoreResourceInExtraDepot) message).getMarbleBufferIndex() -1; //human readable --> programmer readable
            if (controller.canAddResourceInExtraDepotFromBuffer(marbleBufferIndex)){
                controller.addResourceInExtraDepotFromBuffer(marbleBufferIndex);
                if (marbleBuffer.size() == 0)
                    controller.setCurrentPlayerState(new AfterMainActionState(controller));
            } else {
                controller.sendErrorToCurrentPlayer("You can't store this marble in the given spot!");
            }
        } else if (message instanceof ConvertWhiteMarble){
            int marbleBufferIndex = ((ConvertWhiteMarble) message).getMarbleIndex() -1; //human readable --> programmer readable
            if (controller.canConvertWhiteMarble(marbleBufferIndex)){
                controller.convertWhiteMarble(((ConvertWhiteMarble) message).getResourceToConvert(), marbleBufferIndex);
            } else {
                controller.sendErrorToCurrentPlayer("You can't convert this marble!");
            }
        } else {
            baseActionsDuringMainAction(message);
        }
    }
}
