package it.polimi.ingsw.view;

import it.polimi.ingsw.networking.message.ClientAcceptedMessage;
import it.polimi.ingsw.networking.message.StartTurnMessage;
import it.polimi.ingsw.networking.message.updateMessage.*;

public interface View {


    void registerClient(ClientAcceptedMessage m);
    void askPlayersNumber();
    void askNickname();
    void chooseMarketPosition(String position);
    void askInitialDiscard();
    void askLeaderAction();
    void askSwapType();
    void askComboOfSlots();
    void askHowToPayDevCard();
    void askDevCardSlotPosition();

    //Show errors and re-ask input if needed
    void displayTakenNickname();
    void displayNetworkError();  //client-side
    void displayStringMessages(String message);
    //Update User Interface
    void waiting();

    void updateMarketLight(MarketUpdateMessage m);
    void updateStrongboxLight(StrongboxUpdateMessage m);
    void updateWarehouseLight(WarehouseUpdateMessage m);
    void updateBuffer(MarbleBufferUpdateMessage m);
    void updateFaithTrack(FaithTrackUpdateMessage m);
    void updateDevCardMarket(DevCardMarketUpdateMessage m);
    void updateLeaderCardsInHand(LeaderInHandUpdateMessage m);
    void updateProductionZone(ProductionZoneUpdateMessage m);
    void updateNicknames (NicknamesUpdateMessage m);
    void displayWinner(String winnerMessage);
    void displayWrongTurn();

    void displayBuffer();
    void displayMarket();
    void displayDevCardsAvailable();
    void displayLeaderCards();
    void displayWarehouse();
    void displayPersonalBoard(String nickname);
    void displayProductionZone();
    void displayTable();
    void displayStartTurn(StartTurnMessage m);

    void updatePlayersNumber(int num);
    void updateNumOfResourcesToAdd(int num);

    void updateLorenzoLight(LorenzoUpdateMessage m);

}
