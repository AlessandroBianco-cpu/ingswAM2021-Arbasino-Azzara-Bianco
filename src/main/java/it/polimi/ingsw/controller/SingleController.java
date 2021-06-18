package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.playersState.BeforeMainActionState;
import it.polimi.ingsw.controller.playersState.PlayerState;
import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.GameMode.Game;
import it.polimi.ingsw.model.GameMode.SinglePlayerGame;
import it.polimi.ingsw.model.LorenzoIlMagnifico.LorenzoIlMagnifico;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.VirtualView;
import it.polimi.ingsw.networking.message.ChooseLeaderMessage;
import it.polimi.ingsw.networking.message.Message;

import java.util.List;

/**
 * Controller class that handles the Single player game logic
 */
public class SingleController implements Controller{
    private final Game game; //model
    private final UserInputManager uim;
    private final VirtualView virtualView;
    private Player currentPlayer;
    private PlayerState currentState;
    private boolean currentPlayerWantsToEndTurn;
    private final LorenzoIlMagnifico lorenzoIlMagnifico;

    public SingleController(Game game, UserInputManager uim, VirtualView virtualView, Player singlePlayer) {
        this.game = game;
        this.uim = uim;
        this.virtualView = virtualView;
        this.currentPlayer = singlePlayer;
        this.currentState = new BeforeMainActionState(this);
        this.currentPlayerWantsToEndTurn = false;
        lorenzoIlMagnifico = ((SinglePlayerGame) game).getLorenzoIlMagnifico();
    }

    /**
     * Handles the initial distribution of resources
     */
    public void distributeInitialResource() {

    }

    /**
     * This method is used to distribute 4 leader cards for player, each player must discard 2 of them
     */
    public void distributeLeaderCard() {

        currentPlayer.addLeaderCard(game.getLeaderDeck().popFirstCard());
        currentPlayer.addLeaderCard(game.getLeaderDeck().popFirstCard());
        currentPlayer.addLeaderCard(game.getLeaderDeck().popFirstCard());
        currentPlayer.addLeaderCard(game.getLeaderDeck().popFirstCard());


        //handle the discarding of leader card
        virtualView.setCurrentPlayer(currentPlayer.getNickname());
        virtualView.startTurn();

        //manda le nuove 4 carte del giocatore j-esimo
        virtualView.updateLeaderCards(currentPlayer.getLeaders());
        virtualView.requestInitialDiscard();
        ChooseLeaderMessage initialDiscarding = (ChooseLeaderMessage) uim.getActionMessage();

        int initialDiscardingIndex1 = initialDiscarding.getDiscardingIndexes().get(0)-1;
        int initialDiscardingIndex2 = initialDiscarding.getDiscardingIndexes().get(1)-1;

        if (initialDiscardingIndex1 > initialDiscardingIndex2){
            currentPlayer.initialDiscardLeaderCard(initialDiscardingIndex1);
            currentPlayer.initialDiscardLeaderCard(initialDiscardingIndex2);
        } else {
            currentPlayer.initialDiscardLeaderCard(initialDiscardingIndex2);
            currentPlayer.initialDiscardLeaderCard(initialDiscardingIndex1);
        }

        virtualView.updateLeaderCards(currentPlayer.getLeaders());
        virtualView.updateStrongboxState(currentPlayer.getPersonalBoard().getStrongbox()); //is only for print initial 50 resources
        virtualView.endTurn();

    }

    //this method is only used in  the multiplayer Controller
    @Override
    public void manageRejoining(String nickname) { }

    @Override
    public void manageDisconnectionInSetUp(String quitNickname) { virtualView.sendDisconnectionInSetUpGame(quitNickname); }

    /**
     * Handles player's turn shifts
     */
    public void play() {

        while (gameHasToRun()) {
            performTurn(currentPlayer);
            if (!game.isLastRound())
                lorenzoIlMagnifico.doAction();
        }
        String winner;
        if(((SinglePlayerGame) game).isLorenzoWinner()){
            winner = "Lorenzo Il Magnifico";
        }else{
            winner = currentPlayer.getNickname();
        }
        virtualView.updateWinner(winner);

    }

    //this method is only used in multiplayer controller
    @Override
    public void manageAEndGameForQuitting() { }

    private boolean gameHasToRun(){
        return (!game.isLastRound()) && !((SinglePlayerGame) game).isLorenzoWinner();
    }

    /**
     * Handles a player's turn whit all command player can/must do
     *
     * @param currentPlayer current player
     */
    public void performTurn(Player currentPlayer) {

        //viene settato il giocatore corrente nella virtual view:essa gestirà l'invio del messaggio all'utente
        virtualView.setCurrentPlayer(currentPlayer.getNickname());
        setCurrentPlayerState(new BeforeMainActionState(this));
        setCurrentPlayerWantToEndTurn(false);
        virtualView.startTurn();

        //gestione della fase di inizio turno (il player non ha ancora eseguito nessuna azione)
        while (!currentPlayerWantsToEndTurn) {
            virtualView.catchMessages();
            Message actionMessage = uim.getActionMessage();
            currentState.performAction(actionMessage);
        }
    }

    @Override
    public void setCurrentPlayerState(PlayerState currentState) {
        this.currentState = currentState;
    }

    @Override
    public void setCurrentPlayerWantToEndTurn(boolean wantToEndTurn) {
        this.currentPlayerWantsToEndTurn = wantToEndTurn;
    }

    @Override
    public void sendErrorToCurrentPlayer(String errorMessage) {
        virtualView.handleClientInputError(errorMessage);
    }

    @Override
    public void sendResponseToCurrentPlayer(String message) {
        virtualView.handleClientInput(message);
    }

    @Override
    public void sendMessageToCurrentPlayer(Message message) {
        virtualView.sendToCurrentPlayer(message);
    }

    @Override
    public List<Marble> insertMarbleInCol(int colIndex) {
        return currentPlayer.insertMarbleInCol(colIndex);
    }

    @Override
    public List<Marble> insertMarbleInRow(int rowIndex) {
        return currentPlayer.insertMarbleInRow(rowIndex);
    }

    @Override
    public boolean canUseDevCards(List<Integer> productionSlotsIndexes){ return currentPlayer.canUseDevCards(productionSlotsIndexes); }

    @Override
    public boolean canActivateLeaderCard(int index) {
        return currentPlayer.canActivateLeaderCard(index);
    }

    @Override
    public void activateLeader(int index) {
        currentPlayer.activateLeader(index);
    }

    @Override
    public boolean discardLeaderCard(int index) {
        return currentPlayer.discardLeaderCard(index);
    }

    @Override
    public boolean swap(int indexFrom, int indexTo) { return currentPlayer.getPersonalBoard().swap(indexFrom, indexTo); }

    @Override
    public boolean canMoveFromWarehouseToExtraDepot(int depotFrom, int extraDepotTo, int quantity) {
        return currentPlayer.getPersonalBoard().canMoveFromWarehouseToExtraDepot(depotFrom, extraDepotTo, quantity);
    }

    @Override
    public void moveFromWarehouseToExtraDepot(int depotFrom, int extraDepotTo, int quantity) {
        currentPlayer.getPersonalBoard().moveFromWarehouseToExtraDepot(depotFrom, extraDepotTo, quantity);
    }

    @Override
    public boolean canMoveFromExtraDepotToWarehouse(int extraDepotFrom, int depotTo, int quantity) {
        return currentPlayer.getPersonalBoard().canMoveFromExtraDepotToWarehouse(extraDepotFrom, depotTo, quantity);
    }

    @Override
    public void moveFromExtraDepotToWarehouse(int extraDepotFrom, int depotTo, int quantity) {
        currentPlayer.getPersonalBoard().moveFromExtraDepotToWarehouse(extraDepotFrom, depotTo, quantity);
    }

    @Override
    public boolean canBuyDevCardFromMarket(int devCardMarketIndex) {
        return currentPlayer.canBuyDevCardFromMarket(devCardMarketIndex);
    }

    @Override
    public List<QuantityResource> getResourceToPayForProduction(List<Integer> activeSlots) {
        return currentPlayer.getPersonalBoard().sumProductionPowerInputs(activeSlots);
    }

    @Override
    public boolean playerHasEnoughResourcesInWarehouse(QuantityResource inWarehouse) {
        return currentPlayer.getPersonalBoard().warehouseHasEnoughResources(inWarehouse);
    }

    @Override
    public boolean playerHasEnoughResourcesInStrongbox(QuantityResource inStrongbox) {
        return currentPlayer.getPersonalBoard().strongboxHasEnoughResources(inStrongbox);
    }

    @Override
    public boolean playerHasEnoughResourcesInExtraDepot(QuantityResource inExtraDepot) {
        return currentPlayer.getPersonalBoard().extraDepotHasEnoughResources(inExtraDepot);
    }

    @Override
    public void removeResourceFromPlayersResourceSpots(int fromStrongbox, int fromWarehouse, int fromExtraDepot, ResourceType resourceType) {
        currentPlayer.getPersonalBoard().removeResourcesInResourceSpots(fromStrongbox, fromWarehouse, fromExtraDepot, resourceType);
    }

    @Override
    public void addProducedResources(List<Integer> activeSlots) {
        currentPlayer.getPersonalBoard().addProducedResources(activeSlots);
    }

    @Override
    public boolean canDiscardResourceFromBuffer(int bufferIndex) {
        return currentPlayer.canDiscardResourceFromBuffer(bufferIndex);
    }

    @Override
    public void discardResourceFromBuffer(int bufferIndex) {
        currentPlayer.discardResourceFromBuffer(bufferIndex);
    }

    @Override
    public boolean canAddResourceInWarehouseFromBuffer(int bufferIndex, int shelf) {
        return currentPlayer.canAddResourceInWarehouseFromBuffer(bufferIndex, shelf);
    }

    @Override
    public void addResourceInWarehouseFromBuffer(int bufferIndex, int shelf) {
        currentPlayer.addResourceInWarehouseFromBuffer(bufferIndex, shelf);
    }

    @Override
    public boolean canAddResourceInExtraDepotFromBuffer(int bufferIndex) {
        return currentPlayer.canAddResourceInExtraDepotFromBuffer(bufferIndex);
    }

    @Override
    public void addResourceInExtraDepotFromBuffer(int bufferIndex) {
        currentPlayer.addResourceInExtraDepotFromBuffer(bufferIndex);
    }

    @Override
    public boolean canConvertWhiteMarble(ResourceType resourceToConvert, int bufferIndex) {
        return currentPlayer.canConvertWhiteMarble(resourceToConvert, bufferIndex);
    }

    @Override
    public void convertWhiteMarble(ResourceType resourceType, int bufferIndex) {
        currentPlayer.convertWhiteMarble(resourceType, bufferIndex);
    }

    @Override
    public List<QuantityResource> getDevCardCostFromMarketIndex(int marketIndex) {
        return game.getDevCardRequirementsFromIndex(marketIndex);
    }

    @Override
    public boolean satisfiesDevCardInsertionRule(DevCard devCard, int devSlotIndex) {
        return currentPlayer.canPlaceDevCardOnDevSlot(devCard, devSlotIndex);
    }

    @Override
    public DevCard getDevCardFromDevCardMarketIndex(int devCardMarketIndex) {
        return game.getDevCardFromIndex(devCardMarketIndex);
    }

    @Override
    public DevCard popDevCardFromDevCardMarketIndex(int devCardMarketIndex) {
        return game.popDevCardFromIndex(devCardMarketIndex);
    }

    @Override
    public void addBoughtDevCard(DevCard devCard, int devSlotIndex) {
        currentPlayer.getPersonalBoard().addDevCardInSlot(devCard, devSlotIndex);
    }

    @Override
    public boolean canApplyDiscount(ResourceType resourceType) {
        return currentPlayer.canApplyDiscount(resourceType);
    }

    @Override
    public int getDiscountAmount(ResourceType resourceToDiscount) { return currentPlayer.getDiscountAmount(resourceToDiscount); }

    @Override
    public void setBasePowerInput(ResourceType firstInput, ResourceType secondInput) { currentPlayer.getPersonalBoard().setBasePowerInput(firstInput,secondInput); }

    @Override
    public void setBasePowerOutput(ResourceType output) {
        currentPlayer.getPersonalBoard().setBasePowerOutput(output);
    }

    @Override
    public void setLeaderPowerOutput(int leaderSlotIndex, ResourceType output) {
        currentPlayer.getPersonalBoard().setLeaderPowerOutput(leaderSlotIndex, output);
    }
}
