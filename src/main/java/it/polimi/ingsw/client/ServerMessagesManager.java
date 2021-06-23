package it.polimi.ingsw.client;

import it.polimi.ingsw.networking.message.*;
import it.polimi.ingsw.networking.message.updateMessage.*;
import it.polimi.ingsw.view.View;

public class ServerMessagesManager {

    private final View view;

    public ServerMessagesManager(View view) {
        this.view = view;
    }

    /**
     * Manages all types of messages sent from Server
     * @param inputObject message from server
     */
    public void manageInputFromServer(Object inputObject) {

        if(inputObject instanceof PingMessage){
            return;
        }

        if (inputObject instanceof ClientAcceptedMessage)
            view.registerClient((ClientAcceptedMessage) inputObject);

        else if(inputObject instanceof StartTurnMessage)
            view.displayStartTurn((StartTurnMessage) inputObject);

        else if(inputObject instanceof PlayerIsQuittingMessage)
            view.displayPlayersNumChange(((PlayerIsQuittingMessage) inputObject).getMessage(),false);

        else if(inputObject instanceof PlayerIsRejoiningMessage)
            view.displayPlayersNumChange(((PlayerIsRejoiningMessage) inputObject).getMessage(),true);

        else if(inputObject instanceof WaitingMessage)
            view.waitingOtherPlayers(((WaitingMessage) inputObject).getMessage());

        else if(inputObject instanceof MarbleBufferUpdateMessage)
            view.updateBuffer((MarbleBufferUpdateMessage) inputObject);

        else if (inputObject instanceof CardPaymentResourceBufferUpdateMessage)
            view.updateDevCardResourcesToPay((CardPaymentResourceBufferUpdateMessage) inputObject);

        else if (inputObject instanceof PlacementDevCardMessage)
            view.updatePlaceNewCard((PlacementDevCardMessage) inputObject);

        else if (inputObject instanceof ProductionResourceBufferUpdateMessage)
            view.updateProductionResourcesToPay((ProductionResourceBufferUpdateMessage) inputObject);

        else if(inputObject instanceof MarketUpdateMessage)
            view.updateMarketLight((MarketUpdateMessage) inputObject);

        else if (inputObject instanceof StrongboxUpdateMessage)
            view.updateStrongboxLight((StrongboxUpdateMessage) inputObject);

        else if (inputObject instanceof FaithTrackUpdateMessage)
            view.updateFaithTrack((FaithTrackUpdateMessage) inputObject);

        else if (inputObject instanceof WarehouseUpdateMessage)
            view.updateWarehouseLight((WarehouseUpdateMessage) inputObject);

        else if (inputObject instanceof LorenzoUpdateMessage)
            view.updateLorenzoLight((LorenzoUpdateMessage) inputObject);

        else if (inputObject instanceof ProductionZoneUpdateMessage)
            view.updateProductionZone((ProductionZoneUpdateMessage) inputObject);

        else if (inputObject instanceof DevCardMarketUpdateMessage)
            view.updateDevCardMarket((DevCardMarketUpdateMessage) inputObject);

        else if (inputObject instanceof NicknamesUpdateMessage)
            view.updateNicknames((NicknamesUpdateMessage) inputObject);

        else if(inputObject instanceof ChooseResourcesMessage) {
            ChooseResourcesMessage m = (ChooseResourcesMessage) inputObject;
            view.updateNumOfResourcesToAdd(m.getNumOfRes());
        }
        else if (inputObject instanceof LeaderInHandUpdateMessage)
            view.updateLeaderCardsInHand((LeaderInHandUpdateMessage) inputObject);

        else if (inputObject instanceof OpponentsLeaderCardsInHandUpdateMessage)
            view.updateOpponentsLeaderCardsInHand ((OpponentsLeaderCardsInHandUpdateMessage) inputObject);

        else if(inputObject instanceof ClientInputResponse)
            view.displayStringMessages( ((ClientInputResponse) inputObject).getErrorMessage());

        else if (inputObject instanceof WinnerMessage)
            view.displayWinner(((WinnerMessage) inputObject).getMessage());

        else if (inputObject instanceof WrongTurnMessage)
            view.displayWrongTurn();

        else if (inputObject instanceof GameStartedMessage)
            view.gameStarted();

        else if (inputObject instanceof SetPlayersNumMessage)
            view.askPlayersNumber();

        else if (inputObject instanceof SetNicknameMessage)
            view.askLogin();

        else if (inputObject instanceof SetLeadersMessage)
            view.askInitialDiscard();

        else if (inputObject instanceof FirstPlayerMessage)
            view.updateFirstPlayer((FirstPlayerMessage) inputObject);

        else if (inputObject instanceof TakenNameMessage)
            view.displayTakenNickname();

        else if (inputObject instanceof RemoveClientForErrors)
            view.quittingForProblem(((RemoveClientForErrors)inputObject).getMessage());

        else if (inputObject instanceof S2CPlayersNumberMessage){
            view.updatePlayersNumber(((S2CPlayersNumberMessage) inputObject).getNum());

        } else {
            throw new IllegalArgumentException();
        }
    }

}
