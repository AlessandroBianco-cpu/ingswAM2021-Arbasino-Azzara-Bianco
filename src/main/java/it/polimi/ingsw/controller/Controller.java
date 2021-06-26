package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.playersState.PlayerState;
import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.Message;

import java.util.List;

public interface Controller {

    /**
     * Handles the initial distribution of resources
     */
    void distributeInitialResource();

    /**
     * This method is used to distribute 4 leader cards per player. Each player has to discard 2 of the cards
     */
    void distributeLeaderCard();

    /**
     * Handles player's turn shifts
     */
    void play();

    void manageRejoining(String nickname);

    void manageAEndGameForQuitting();

    void manageDisconnectionInSetUp(String quitNickname);

    void performTurn(Player currentPlayer);

    boolean getGameEnded();

    void setCurrentPlayerState(PlayerState state);

    void setCurrentPlayerWantToEndTurn(boolean wantToEndTurn);

    void sendErrorToCurrentPlayer(String errorMessage);
    void sendResponseToCurrentPlayer(String message);
    void sendMessageToCurrentPlayer(Message message);

    // ---------------------------------- Methods calling model ------------------------------
    List<Marble> insertMarbleInCol(int colIndex);
    List<Marble> insertMarbleInRow(int rowIndex);
    boolean canUseDevCards(List<Integer> productionSlotsIndexes);
    boolean canActivateLeaderCard(int index);
    void activateLeader(int index);
    boolean discardLeaderCard(int index);
    boolean swap(int indexFrom, int indexTo);

    boolean canMoveFromWarehouseToExtraDepot(int depotFrom, int extraDepotTo, int quantity);
    void moveFromWarehouseToExtraDepot(int depotFrom, int extraDepotTo, int quantity);
    boolean canMoveFromExtraDepotToWarehouse(int extraDepotFrom, int depotTo, int quantity);
    void moveFromExtraDepotToWarehouse(int extraDepotFrom, int depotTo, int quantity);
    boolean canBuyDevCardFromMarket(int devCardMarketIndex);
    List<QuantityResource> getResourceToPayForProduction(List<Integer> activeSlots);
    boolean playerHasEnoughResourcesInWarehouse(QuantityResource inWarehouse);
    boolean playerHasEnoughResourcesInStrongbox(QuantityResource inStrongbox);
    boolean playerHasEnoughResourcesInExtraDepot(QuantityResource inExtraDepot);
    void removeResourceFromPlayersResourceSpots(int fromStrongbox, int fromWarehouse, int fromExtraDepot, ResourceType resourceType);
    void addProducedResources(List<Integer> activeSlots);
    boolean canDiscardResourceFromBuffer(int bufferIndex);
    void discardResourceFromBuffer(int bufferIndex);
    boolean canAddResourceInWarehouseFromBuffer(int bufferIndex, int shelf);
    void addResourceInWarehouseFromBuffer(int bufferIndex, int shelf);
    boolean canAddResourceInExtraDepotFromBuffer(int bufferIndex);
    void addResourceInExtraDepotFromBuffer(int bufferIndex);
    boolean canConvertWhiteMarble(ResourceType resourceToConvert, int bufferIndex);
    void convertWhiteMarble(ResourceType resourceType, int bufferIndex);
    List<QuantityResource> getDevCardCostFromMarketIndex(int marketIndex);
    boolean satisfiesDevCardInsertionRule(DevCard devCard, int devSlotIndex);
    DevCard getDevCardFromDevCardMarketIndex(int devCardMarketIndex);
    DevCard popDevCardFromDevCardMarketIndex(int devCardMarketIndex);
    void addBoughtDevCard(DevCard devCard, int devSlotIndex);
    boolean canApplyDiscount(ResourceType resourceType);
    int getDiscountAmount(ResourceType resourceToDiscount);
    void setBasePowerInput(ResourceType firstInput, ResourceType secondInput);
    void setBasePowerOutput(ResourceType output);
    void setLeaderPowerOutput(int leaderSlotIndex, ResourceType output);
}
