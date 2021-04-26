package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Board.PersonalBoard;
import it.polimi.ingsw.model.Cards.*;
import it.polimi.ingsw.model.GameMode.Game;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.model.MarbleMarket.Market;
import it.polimi.ingsw.model.MarbleMarket.WhiteMarble;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player /* extends ModelObservable */ implements LeaderCardPowerAdder {

    private String nickname;
    private boolean token;
    private List<ResourceType> convertWhiteCards;
    private List<QuantityResource> discountCards;
    private PersonalBoard personalBoard;
    private List<Marble> buffer;
    private List<LeaderCard> leaders;
    private Game game;
    private int victoryPoints;
    private Market market;
    private DevCardMarket devCardMarket;

    public Player(Game game, String nickname, DevCardMarket devCardMarket) {
        this.convertWhiteCards = new ArrayList<>();
        this.game = game;
        this.token = false;
        this.nickname = nickname;
        buffer = new LinkedList<>();
        this.discountCards = new ArrayList<>();
        this.personalBoard = new PersonalBoard(this, game);
        this.leaders = new ArrayList<>();
        this.victoryPoints = 0;
        this.market = game.getMarket();
        this.devCardMarket = devCardMarket;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
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
    }

    /**
     * This method is used to check if player can activate the leader card chosen
     * @param index is the index of the card to activate (0,1)
     */
    public boolean canActivateLeaderCard(int index) {
        if (leaders != null && (index < 0 || index > leaders.size() - 1))
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
        return buffer;
    }

    /**
     * This method is used to add the resources taken by the market column chosen by the player
     *
     * @param colIndex is the index of col (0,1,2,3)
     */
    public List<Marble> insertMarbleInCol(int colIndex) {
        marbleFilter(market.insertMarbleInCol(colIndex));
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
     * This method implements the vatican report check
     *
     * @param vaticanReportNumber is the number of vatican report that was activated
     */
    public void vaticanReport(int vaticanReportNumber) {
        personalBoard.vaticanReport(vaticanReportNumber);
    }

    public void discardLeaderCard(int leaderCardIndex) {
        if (!leaders.get(leaderCardIndex).isActive()) {
            leaders.remove(leaderCardIndex);
            this.victoryPoints++;
        }
    }

    public boolean canActivateProductionAction() {
        return personalBoard.canActivateProductionAction();
    }

    public boolean canBuyDevCardFromMarket(int cardMarketIndex) {
        DevCard cardToBuy = devCardMarket.getDevCardFromIndex(cardMarketIndex);
        if (cardToBuy == null || !personalBoard.satisfiedDevCardInsertionLevel(cardToBuy))
            return false;
        return personalBoard.satisfiedResourceRequirement(devCardMarket.getDevCardFromIndex(cardMarketIndex).getCost());
    }

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

    public void addResourceInWarehouseFromBuffer(int bufferIndex, int shelf){
        Marble toConvert = ((LinkedList<Marble>)buffer).get(bufferIndex);
        ((LinkedList<Marble>)buffer).remove(bufferIndex);
        addResourceInWarehouse(toConvert.convertResource(),shelf);
    }

    public boolean canAddResourceInExtraDepot(ResourceType resourceType){
        if(!(resourceType.canAddToSupply()))
            return false;
        return personalBoard.canAddResourceInExtraDepot(resourceType);
    }

    public void addResourceInExtraDepotFromBuffer(int bufferIndex){
        Marble toConvert = ((LinkedList<Marble>)buffer).get(bufferIndex);
        ((LinkedList<Marble>)buffer).remove(bufferIndex);
        personalBoard.addResourceInExtraDepot(toConvert.convertResource());
    }

    public boolean canDiscardResourceFromBuffer(int bufferIndex){
        if(!(buffer.get(bufferIndex).getResourceType().canAddToSupply()))
            return false;
        Marble marble = ((LinkedList<Marble>)buffer).get(bufferIndex);
        return marble.getResourceType().canAddToSupply();
    }

    public void discardResourceFromBuffer(int bufferIndex){
        ((LinkedList<Marble>)buffer).remove(bufferIndex);
        game.advanceAfterDiscard(this);
    }

    /**
     * This method evaluates if a marble can be converted.
     * Should be invoked after selecting a row or a column from the market
     * because after that call White Marbles are already deleted because player
     * does not own convertWhiteCards
     * @param bufferIndex index of the marble to evaluate
     * @return true or false
     */
    public boolean canConvertWhiteMarble(int bufferIndex){
        return !(buffer.get(bufferIndex).getResourceType().canAddToSupply());
    }


    public void convertWhiteMarble(ResourceType resourceType, int bufferIndex){
        if(resourceType == ResourceType.NOTHING){
            ((LinkedList<Marble>)buffer).remove(bufferIndex);
            return;
        }
        ((WhiteMarble)buffer.get(bufferIndex)).convertWhiteToResource(resourceType);
    }


}
