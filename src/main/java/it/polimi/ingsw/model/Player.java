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

    private String nickname;
    private List<ResourceType> convertWhiteCards;
    private List<QuantityResource> discountCards;
    private PersonalBoard personalBoard;
    private List<Marble> buffer;
    private List<LeaderCard> leaders;
    private Game game;
    private int victoryPoints;
    private Market market;
    private DevCardMarket devCardMarket;

    public Player(String nickname) {
        this.convertWhiteCards = new ArrayList<>();
        this.nickname = nickname;
        buffer = new LinkedList<>();
        this.discountCards = new ArrayList<>();
        this.leaders = new ArrayList<>();
        this.victoryPoints = 0;
    }

    public void setGame(Game game) {
        this.game = game;
        this.market = game.getMarket();
        this.devCardMarket = game.getDevCardMarket();
        this.personalBoard = new PersonalBoard(this, game);
    }


    public String getNickname() {
        return this.nickname;
    }

    public List<Marble> getBuffer() {
        return buffer;
    }

    /**
     * Method used to update player position n spaces forward
     * @param n number of spaces player has to move forward
     */
    public void moveForwardNPositions(int n) {
        personalBoard.getFaithTrack().moveForwardNPositions(n);
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public List<LeaderCard> getLeaders() {
        return leaders;
    }

    public void addLeaderCard(LeaderCard l) {
        leaders.add(l);
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

    public List<ResourceType> getConvertWhiteCards() {
        return convertWhiteCards;
    }

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
            if(marble.convertResource().canAddToFaithTrack()){
                moveForwardNPositions(1);
            }else if(marble.isWhiteMarble() && convertWhiteCards.size() == 0){
                ; /* if the marble can't be converted gets automatically "discarded" */
            }else{
                buffer.add(marble);
            }
        }
    }

    @Override
    public void addConvertWhiteCardPower(ConvertWhiteCard givenCard) {
        convertWhiteCards.add(givenCard.getAbilityResource());
    }

    @Override
    public void addDiscountCardPower(DiscountCard givenCard) {
        discountCards.add(new QuantityResource(givenCard.getAbilityResource(), givenCard.getDiscountAmount()));
    }

    @Override
    public void addExtraDepotCardPower(ExtraDepotCard givenCard) {
        personalBoard.getWarehouse().addExtraDepot(givenCard.getAbilityResource());
    }

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

    public boolean canActivateProductionAction() {
        return personalBoard.canActivateProductionAction();
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

    public List<Integer> getSlotIndexesPlayerCanPlaceDevCard(DevCard devCard) {
        return personalBoard.getSlotIndexesPlayerCanPlaceDevCard(devCard);
    }

    public void increaseVictoryPoints(int amount){
        victoryPoints += amount;
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

    public boolean canAddResourceInWarehouse(ResourceType resourceType, int shelf){
        if(!(resourceType.canAddToSupply()))
            return false;
        return personalBoard.canAddResourceInWarehouse(resourceType,shelf);
    }

    public void addResourceInWarehouse(ResourceType resourceType, int shelf){
        personalBoard.addResourceInWarehouse(resourceType,shelf);
    }

    public boolean canAddResourceInWarehouseFromBuffer(int bufferIndex, int shelf){
        if (bufferIndex < 0 || (bufferIndex > buffer.size()-1))
            return false;
        Marble toConvert = ((LinkedList<Marble>)buffer).get(bufferIndex);
        return canAddResourceInWarehouse(toConvert.getResourceType(), shelf);
    }

    public void addResourceInWarehouseFromBuffer(int bufferIndex, int shelf){
        Marble toConvert = ((LinkedList<Marble>)buffer).get(bufferIndex);
        ((LinkedList<Marble>)buffer).remove(bufferIndex);
        addResourceInWarehouse(toConvert.convertResource(),shelf);
        notifyMarbleBuffer(buffer);
    }

    public boolean canAddResourceInExtraDepot(ResourceType resourceType){
        if(!(resourceType.canAddToSupply()))
            return false;
        return personalBoard.canAddResourceInExtraDepot(resourceType);
    }

    public boolean canAddResourceInExtraDepotFromBuffer(int bufferIndex){
        if (bufferIndex < 0 || (bufferIndex > buffer.size()-1))
            return false;
        Marble toConvert = ((LinkedList<Marble>)buffer).get(bufferIndex);
        return canAddResourceInExtraDepot(toConvert.getResourceType());
    }

    public void addResourceInExtraDepotFromBuffer(int bufferIndex){
        Marble toConvert = ((LinkedList<Marble>)buffer).get(bufferIndex);
        ((LinkedList<Marble>)buffer).remove(bufferIndex);
        personalBoard.addResourceInExtraDepot(toConvert.convertResource());
        notifyMarbleBuffer(buffer);
    }

    public boolean canDiscardResourceFromBuffer(int bufferIndex){
        return bufferIndex >= 0 && (bufferIndex <= buffer.size() - 1);
    }

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
     * @param bufferIndex index of the marble to evaluate
     * @return true or false
     */
    public boolean canConvertWhiteMarble(int bufferIndex){
        if (bufferIndex < 0 || (bufferIndex > buffer.size()-1))
            return false;
        return !(buffer.get(bufferIndex).getResourceType().canAddToSupply());
    }

    public void convertWhiteMarble(ResourceType resourceType, int bufferIndex){
        ((WhiteMarble)buffer.get(bufferIndex)).convertWhiteToResource(resourceType);
        notifyMarbleBuffer(buffer);
    }

    public boolean canUseDevCards(List<Integer> productionSlotsIndexes){
        return personalBoard.canUseDevCards(productionSlotsIndexes);
    }

    public boolean canPlaceDevCardOnDevSlot(DevCard devCard, int devCardSlotIndex){
        return personalBoard.canPlaceDevCardOnDevSlot(devCard, devCardSlotIndex);
    }

    public boolean canApplyDiscount(ResourceType resourceToDiscount){
        for (QuantityResource q : discountCards){
            if (q.getResourceType() == resourceToDiscount)
                return true;
        }
        return false;
    }

    public int getDiscountAmount(ResourceType resourceToDiscount){
        for (QuantityResource q : discountCards){
            if (q.getResourceType() == resourceToDiscount)
                return q.getQuantity();
        }
        return 0;
    }

}
