package it.polimi.ingsw.view;

import it.polimi.ingsw.networking.message.ClientAcceptedMessage;
import it.polimi.ingsw.networking.message.PlacementDevCardMessage;
import it.polimi.ingsw.networking.message.StartTurnMessage;
import it.polimi.ingsw.networking.message.updateMessage.*;

/**
 * Is the interface for client-side: all these methods are called from the ClientNetworkHandler after
 * the dispatching of message received from server
 */
public interface View {


    void gameStarted();

    void registerClient(ClientAcceptedMessage m);
    void askPlayersNumber();
    void askLogin();
    void askInitialDiscard();
    void askInitialResource();

    //Show errors and re-ask input if needed
    void displayTakenNickname();
    void displayNetworkError();  //client-side
    void displayStringMessages(String message);
    void displayPlayersNumChange(String message,boolean join);
    void quittingForProblem(String message);

    //Update User Interface
    void waitingOtherPlayers(String message);

    void updateMarketLight(MarketUpdateMessage m);
    void updateStrongboxLight(StrongboxUpdateMessage m);
    void updateWarehouseLight(WarehouseUpdateMessage m);
    void updateFaithTrack(FaithTrackUpdateMessage m);
    void updateDevCardMarket(DevCardMarketUpdateMessage m);
    void updateLeaderCardsInHand(LeaderInHandUpdateMessage m);
    void updateOpponentsLeaderCardsInHand (OpponentsLeaderCardsInHandUpdateMessage m);
    void updateProductionZone(ProductionZoneUpdateMessage m);
    void updateNicknames (NicknamesUpdateMessage m);
    void updateDevCardResourcesToPay(CardPaymentResourceBufferUpdateMessage m);
    void updateProductionResourcesToPay(ProductionResourceBufferUpdateMessage m);
    void updateBuffer(MarbleBufferUpdateMessage m);
    void updatePlaceNewCard(PlacementDevCardMessage m);
    void updateFirstPlayer (FirstPlayerMessage m);

    void displayWinner(String winnerMessage);
    void displayWrongTurn();

    void displayBuffer();
    void displayLeaderCards();
    void displayWarehouse();
    void displayProductionZone();
    void displayStartTurn(StartTurnMessage m);
    void displayResourcesToPayForCard();
    void displayProductionResourcesPayment();

    void updatePlayersNumber(int num);
    void updateNumOfResourcesToAdd(int num);

    void updateLorenzoLight(LorenzoUpdateMessage m);

}
