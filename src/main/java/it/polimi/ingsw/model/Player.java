package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Board.PersonalBoard;
import it.polimi.ingsw.model.Cards.*;
import it.polimi.ingsw.model.GameMode.Game;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.model.MarbleMarket.Market;
import it.polimi.ingsw.model.MarbleMarket.WhiteMarble;
import it.polimi.ingsw.observer.PlayerItemsObservable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player extends PlayerItemsObservable implements LeaderCardPowerAdder {

    private final String nickname;
    private boolean active;
    private final List<ResourceType> convertWhiteCards;
    private final List<QuantityResource> discountCards;
    private PersonalBoard personalBoard;
    private final List<Marble> buffer;
    private final List<LeaderCard> leaders;
    private Game game;
    private int victoryPoints;
    private Market market;
    private DevCardMarket devCardMarket;

    public Player(String nickname) {
        this.active = true;
        this.convertWhiteCards = new ArrayList<>();
        this.nickname = nickname;
        buffer = new LinkedList<>();
        this.discountCards = new ArrayList<>();
        this.leaders = new ArrayList<>();
        this.victoryPoints = 0;
    }

    // ------------------------ SETTERS ------------------------
    public void setGame(Game game) {
        this.game = game;
        this.market = game.getMarket();
        this.devCardMarket = game.getDevCardMarket();
        this.personalBoard = new PersonalBoard(this, game);
    }

    public void setActive(boolean active) { this.active = active; }

    // ------------------------ GETTERS ------------------------
    public boolean isActive() { return active; }

    public String getNickname() {
        return this.nickname;
    }

    public List<Marble> getBuffer() {
        return buffer;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public List<LeaderCard> getLeaders() {
        return leaders;
    }

    public int getTotalOfResources(){
        return personalBoard.getTotalNumberOfResources();
    }

    public int getTotalVictoryPoints(){
        return this.victoryPoints
                + personalBoard.getFaithTrack().getPositionScore()
                + personalBoard.getFaithTrack().getVaticanReportFaithPoints()
                + getTotalOfResources() / 5;
    }

    // ------------------------ PLAYER'S ACTIONS ------------------------

    /**
     * Adds a leader card in player's hand
     * @param l card to add
     */
    public void addLeaderCard(LeaderCard l) {
        leaders.add(l);
    }

    /**
     * Method used to update player position n spaces forward
     * @param n number of spaces player has to move forward
     */
    public void moveForwardNPositions(int n) {
        personalBoard.getFaithTrack().moveForwardNPositions(n);
    }

    /**
     * This method activates a Leader Card.
     * It should be invoked after canActivateLeaderCard returns true
     */
    public void activateLeader(int index) {
        leaders.get(index).activateCard(this);
        increaseVictoryPoints(leaders.get(index).getVictoryPoints());
        notifyLeaderCards(leaders);
    }

    /**
     * This method is used to check if player can activate the leader card chosen
     * @param index is the index of the card to activate (0,1)
     */
    public boolean canActivateLeaderCard(int index) {
        if(leaders.size() == 0 || (index < 0 || index > leaders.size() - 1))
            return false;
        for (Requirement r : leaders.get(index).getRequirements()) {
            if (r.isResourceRequirement()) {
                if (!(personalBoard.getGeneralResource().hasEnoughResources(r.getResource())))
                    return false;
            } else if (r.isCardRequirement()) {
                if (!(personalBoard.satisfiedCardRequirement((CardRequirement) r)))
                    return false;
            }
        }
        return true;
    }

    /**
     * Method used to perform a discard of a card in the initial phase of the game
     * @param index index of the card to remove
     */
    public void initialDiscardLeaderCard(int index) {
        leaders.remove(index);
    }

    /**
     * This method is used to add the resources taken by the market row chosen by the player
     *
     * @param rowIndex is the index of row (0,1,2)
     */
    public List<Marble> insertMarbleInRow(int rowIndex) {
        marbleFilter(market.insertMarbleInRow(rowIndex));
        notifyMarbleBuffer(buffer);
        return buffer;
    }

    /**
     * This method is used to add the resources taken by the market column chosen by the player
     * @param colIndex is the index of col (0,1,2,3)
     */
    public List<Marble> insertMarbleInCol(int colIndex) {
        marbleFilter(market.insertMarbleInCol(colIndex));
        notifyMarbleBuffer(buffer);
        return buffer;
    }

    /**
     * This method adds to the buffer only the marbles that can be added to supply or
     * white marbles that can be converted
     * @param marbles list of marbles returned from the market
     */
    public void marbleFilter(List<Marble> marbles){
        for (Marble marble : marbles) {
            if(marble.convertResource().canAddToFaithTrack())
                moveForwardNPositions(1);
            else if( !(marble.isWhiteMarble() && convertWhiteCards.size() == 0))
                buffer.add(marble);
            //if the marble can't be converted, it get automatically discarded
        }
    }

    /**
     * Adds the power of a ConvertWhite LeaderCard. It adds the card in a list inside the player class
     * @param givenCard ConvertWhite LeaderCard activated
     */
    @Override
    public void addConvertWhiteCardPower(ConvertWhiteCard givenCard) {
        convertWhiteCards.add(givenCard.getAbilityResource());
    }

    /**
     * Adds the power of a Discount LeaderCard. It adds the card in a list inside the player class
     * @param givenCard Discount LeaderCard activated
     */
    @Override
    public void addDiscountCardPower(DiscountCard givenCard) {
        discountCards.add(new QuantityResource(givenCard.getAbilityResource(), givenCard.getDiscountAmount()));
    }

    /**
     * Adds the power of a ExtraDepot LeaderCard. It adds a new slot in the warehouse
     * @param givenCard ExtraDepot LeaderCard activated
     */
    @Override
    public void addExtraDepotCardPower(ExtraDepotCard givenCard) {
        personalBoard.getWarehouse().addExtraDepot(givenCard.getAbilityResource());
    }

    /**
     * Adds the power of a ExtraDevelopment LeaderCard. It adds the card in a new production slot inside the PB
     * @param givenCard ExtraDevelopment LeaderCard activated
     */
    @Override
    public void addExtraDevCardPower(ExtraDevCard givenCard) {
        personalBoard.addExtraDevSlot(givenCard);
    }

    public void notifyLastRound(){
        game.activateLastRound();
    }

    /**
     * This method starts a vatican report
     * @param vaticanReportNumber is the number of vatican report that was activated
     */
    public void vaticanReport(int vaticanReportNumber) {
        personalBoard.vaticanReport(vaticanReportNumber);
    }

    /**
     * Performs the action of discarding a leader card. After it is discarded, the player will earn a VP
     * @param leaderCardIndex index of the card to discard
     * @return true if can be removed, false otherwise
     */
    public boolean discardLeaderCard(int leaderCardIndex) {
        //In bound check
        if(leaders.size() == 0 || leaderCardIndex < 0 || leaderCardIndex > leaders.size()-1)
            return false;
        if (!leaders.get(leaderCardIndex).isActive()) {
            leaders.remove(leaderCardIndex);
            this.moveForwardNPositions(1);
            notifyLeaderCards(leaders);
            return true;
        }
        return false;
    }

    /**
     * This method is used to know whether a player can buy a given card in the card market.
     * It applies the discounts from the leader cards if present
     * @param cardMarketIndex index of the card to buy in the market
     * @return true if can be bought, false otherwise
     */
    public boolean canBuyDevCardFromMarket(int cardMarketIndex) {
        if (cardMarketIndex < 0 || cardMarketIndex > 11)
            return false;
        DevCard cardToBuy = devCardMarket.getDevCardFromIndex(cardMarketIndex);
        if (cardToBuy == null || !personalBoard.satisfiedDevCardInsertionLevel(cardToBuy))
            return false;
        return personalBoard.satisfiedResourceRequirement(
                getDevCardCostDiscounted(cardMarketIndex));
    }

    /**
     * This method is used to get the price of a devCard applying all the possibles discounts
     * that can be achieved through the DiscountLeaderCards
     * @param cardMarketIndex index of the card to discount
     * @return the cost of the DevCard discounted
     */
    public List<QuantityResource> getDevCardCostDiscounted(int cardMarketIndex){
        List<QuantityResource> cost = devCardMarket.getDevCardFromIndex(cardMarketIndex).getCost();
        List<QuantityResource> finalCost = new ArrayList<>();

        for (QuantityResource q : cost){
            if (!(isResourceTypeContainedInList(finalCost, q.getResourceType()))){
                QuantityResource tmpQuantity = new QuantityResource(q.getResourceType(), q.getQuantity());
                if (applyTemporaryDiscount(tmpQuantity).getQuantity() > 0)
                    finalCost.add(tmpQuantity);
            }
        }
        return finalCost;
    }

    /**
     * This method is used to get the cost discounted of a single quantity resource applying the discount
     * of the DiscountLeaderCard
     * @param quantityToDiscount resource to discount
     * @return resource discounted
     */
    private QuantityResource applyTemporaryDiscount(QuantityResource quantityToDiscount){
        for (QuantityResource discountQuantity : discountCards){
            if (quantityToDiscount.getResourceType() == discountQuantity.getResourceType()){
                quantityToDiscount.increase(discountQuantity.getQuantity()); //It's an increase because the discount of the DiscountLeaderCard is a negative number
            }
        }
        return quantityToDiscount;
    }

    /**
     * This method is used to know if a resource is contained in a list of QuantityResource
     * @param list list of resources in which we have to look for
     * @param resourceType resource type to look for
     * @return true if contained, false otherwise
     */
    private boolean isResourceTypeContainedInList(List<QuantityResource> list, ResourceType resourceType){
        for(QuantityResource q : list){
            if (q.getResourceType() == resourceType)
                return true;
        }
        return false;
    }

    /**
     * Returns a list of cards player can buy from the DevCardMarket. It is used to know which of the cards
     * in the market player can buy before he actually buys it
     * @return a list of indexes of cards that player can potentially buy
     */
    public List<Integer> devCardsPlayerCanBuy() {
        List<Integer> canBuyIndexes = new ArrayList<>();
        for (int i = 0; i < devCardMarket.getNumberOfDecks(); i++) {
            if (canBuyDevCardFromMarket(i)) {
                canBuyIndexes.add(i);
            }
        }
        return canBuyIndexes;
    }

    /**
     * Increases player's VPs
     * @param amount amount of points to increase
     */
    public void increaseVictoryPoints(int amount){
        victoryPoints += amount;
    }

    /**
     * Method used to know if the given resource can be added in the given shelf
     * @param resourceType is the resource to add
     * @param shelf is the chosen index to put the resource
     */
    public boolean canAddResourceInWarehouse(ResourceType resourceType, int shelf){
        if(!(resourceType.canAddToSupply()))
            return false;
        return personalBoard.canAddResourceInWarehouse(resourceType,shelf);
    }

    /**
     * This method adds the given resources in the given shelf index
     * Must be called only if canAddResourceInWarehouse returns true
     * @param resourceType is the resource to add
     * @param shelf is the chosen index to put the resource
     */
    public void addResourceInWarehouse(ResourceType resourceType, int shelf){
        personalBoard.addResourceInWarehouse(resourceType,shelf);
    }

    /**
     * Method used to know if a resource in the buffer can be added in a certain shelf
     * @param bufferIndex index of the marble in the buffer
     * @param shelf index of the shelf of the warehouse
     * @return true if can be added, false otherwise
     */
    public boolean canAddResourceInWarehouseFromBuffer(int bufferIndex, int shelf){
        if (bufferIndex < 0 || (bufferIndex > buffer.size()-1))
            return false;
        Marble toConvert = buffer.get(bufferIndex);
        return canAddResourceInWarehouse(toConvert.getResourceType(), shelf);
    }

    /**
     * Method used to add a resource from the buffer to a shelf of the warehouse
     * It must be called only if canAddResourceInWarehouseFromBuffer returns true
     * @param bufferIndex index of the marble in the buffer
     * @param shelf index of the shelf of the warehouse
     */
    public void addResourceInWarehouseFromBuffer(int bufferIndex, int shelf){
        Marble toConvert = buffer.get(bufferIndex);
        buffer.remove(bufferIndex);
        addResourceInWarehouse(toConvert.convertResource(),shelf);
        notifyMarbleBuffer(buffer);
    }

    /**
     * Method used to know if the given resource can be added in an extra depot
     * @param resourceType is the resource to add
     */
    public boolean canAddResourceInExtraDepot(ResourceType resourceType){
        if(!(resourceType.canAddToSupply()))
            return false;
        return personalBoard.canAddResourceInExtraDepot(resourceType);
    }

    /**
     * Method used to know if a resource in the buffer can be added in an extra depot
     * @param bufferIndex index of the marble in the buffer
     * @return true if can be added, false otherwise
     */
    public boolean canAddResourceInExtraDepotFromBuffer(int bufferIndex){
        if (bufferIndex < 0 || (bufferIndex > buffer.size()-1))
            return false;
        Marble toConvert = buffer.get(bufferIndex);
        return canAddResourceInExtraDepot(toConvert.getResourceType());
    }

    /**
     * Adds a resource from the buffer in an extra Depot.
     * It must be called only if canAddResourceInExtraDepotFromBuffer returns true
     * @param bufferIndex index of the marble in the buffer
     */
    public void addResourceInExtraDepotFromBuffer(int bufferIndex){
        Marble toConvert = buffer.get(bufferIndex);
        buffer.remove(bufferIndex);
        personalBoard.addResourceInExtraDepot(toConvert.convertResource());
        notifyMarbleBuffer(buffer);
    }

    /**
     * Method used to know if a marble in the buffer can be discarded
     * @param bufferIndex index of the marble in the buffer
     * @return true if can be discarded, false otherwise
     */
    public boolean canDiscardResourceFromBuffer(int bufferIndex){
        return bufferIndex >= 0 && (bufferIndex <= buffer.size() - 1);
    }

    /**
     * Method used to discard if a resource in the buffer can be discarded
     * It must be called only if canDiscardResourceFromBuffer returns true
     * @param bufferIndex index of the marble in the buffer
     */
    public void discardResourceFromBuffer(int bufferIndex){
        Marble toDiscard  = buffer.get(bufferIndex);
        buffer.remove(bufferIndex);
        if(!toDiscard.isWhiteMarble())
            game.advanceAfterDiscard(this);
        notifyMarbleBuffer(buffer);
    }

    /**
     * This method evaluates if a marble can be converted.
     * Should be invoked after selecting a row or a column from the market
     * because after that call White Marbles are already deleted if player
     * does not own convertWhiteCards
     * @param resourceToConvert type of resource in which player wants to convert the white marble
     * @param bufferIndex index of the marble to evaluate
     * @return true or false
     */
    public boolean canConvertWhiteMarble(ResourceType resourceToConvert, int bufferIndex){
        if (bufferIndex < 0 || (bufferIndex > buffer.size()-1))
            return false;
        return ( !(buffer.get(bufferIndex).getResourceType().canAddToSupply())
            && convertWhiteCards.contains(resourceToConvert));
    }

    /**
     * This method converts a given marble into an other
     * @param resourceType type of resource in which player wants to convert the white marble
     * @param bufferIndex index of the marble in the buffer player wants to convert
     */
    public void convertWhiteMarble(ResourceType resourceType, int bufferIndex){
        ((WhiteMarble)buffer.get(bufferIndex)).convertWhiteToResource(resourceType);
        notifyMarbleBuffer(buffer);
    }

    /**
     * @param productionSlotsIndexes list of the indexes of the slots player wants to use for production
     * @return true if the combo is valid, false otherwise
     */
    public boolean canUseDevCards(List<Integer> productionSlotsIndexes){
        return personalBoard.canUseDevCards(productionSlotsIndexes);
    }

    /**
     * @param devCard card player wants to place
     * @param devCardSlotIndex index of the Development Slot in which player wants to put the bought card
     * @return true if the card can be placed, false otherwise
     */
    public boolean canPlaceDevCardOnDevSlot(DevCard devCard, int devCardSlotIndex){
        return personalBoard.canPlaceDevCardOnDevSlot(devCard, devCardSlotIndex);
    }

    /**
     * @param resourceToDiscount type of resource player wants to discard
     * @return true if the discount can be applied, false otherwise
     */
    public boolean canApplyDiscount(ResourceType resourceToDiscount){
        for (QuantityResource q : discountCards){
            if (q.getResourceType() == resourceToDiscount)
                return true;
        }
        return false;
    }

    /**
     * @param resourceToDiscount type of resource player wants to use discount
     * @return the discount that can be applied thanks to the Discount Leader Card
     */
    public int getDiscountAmount(ResourceType resourceToDiscount){
        for (QuantityResource q : discountCards){
            if (q.getResourceType() == resourceToDiscount)
                return q.getQuantity();
        }
        return 0;
    }

}
