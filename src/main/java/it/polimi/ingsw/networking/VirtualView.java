package it.polimi.ingsw.networking;

import it.polimi.ingsw.client.LightModel.ParserForModel;
import it.polimi.ingsw.client.LightModel.market.MarbleLight;
import it.polimi.ingsw.model.Board.FaithTrack;
import it.polimi.ingsw.model.Board.PersonalBoard;
import it.polimi.ingsw.model.Board.Strongbox;
import it.polimi.ingsw.model.Board.Warehouse;
import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.model.DevCardMarket;
import it.polimi.ingsw.model.LorenzoIlMagnifico.LorenzoIlMagnifico;
import it.polimi.ingsw.model.MarbleMarket.Marble;
import it.polimi.ingsw.model.MarbleMarket.Market;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.*;
import it.polimi.ingsw.networking.message.updateMessage.*;
import it.polimi.ingsw.observer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class implements the server's VirtualView, used to send messages to the game clients
 */
public class VirtualView extends ViewObservable implements MarketObserver, PlayerItemsObserver, StrongboxObserver, FaithTrackObserver, WarehouseObserver, DevCardMarketObserver, LorenzoObserver, ProductionZoneObserver {

    private final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private String currentPlayer;
    private final int lobbyID;
    private final Object lock;

    public VirtualView(int lobbyID) {
        this.lobbyID = lobbyID;
        this.lock = new Object();
    }

    /**
     * Adds the client and its nickname to the clients map
     * @param name player nickname
     * @param client recipient client
     */
    public void addClient(String name, ClientHandler client) {
        synchronized (lock) {
            clients.put(name, client);
            lock.notifyAll();
        }
    }

    /**
     * Remove the client and its nickname to the clients map
     * @param name player nickname
     * @param client recipient client
     */
    public void removeClient(String name, ClientHandler client){
        synchronized (lock) {
            clients.remove(name, client);
            lock.notifyAll();
        }
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the client as current player
     * @param currentPlayer current player
     */
    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer = currentPlayer;
        clients.get(currentPlayer).setMyTurn(true);
    }

    /**
     * Asks the lobby size
     * @param firstClient first player in the lobby
     */
    public void requestPlayersNum(ClientHandler firstClient) {

        int num;
        System.out.println("[SERVER] Sending setting_num message");
        firstClient.send(new SetPlayersNumMessage());
        Message message = firstClient.read();

        while (! (message instanceof NumOfPlayerMessage)) {
            firstClient.send(new ClientInputResponse("Remember you must set the players number"));
            message = firstClient.read();
        }

        num = ((NumOfPlayerMessage) message).getNumPlayers();
        while (num < 1 || num > 4) {
            firstClient.send(new SetPlayersNumMessage());
            message = firstClient.read();
            num = ((NumOfPlayerMessage) message).getNumPlayers();
            }

        notifyPlayersNumber(num);
    }

    /**
     * This method handle the messages used for the initial_leader_discard
     */
    public void requestInitialDiscard() {

        clients.get(currentPlayer).send(new SetLeadersMessage());
        sendToEveryoneExceptCurrentPlayer(new WaitingMessage("Other players are choosing leader cards..."));
        Message discardingMessage =  clients.get(currentPlayer).read();

        while (! (discardingMessage instanceof ChooseLeaderMessage)) {
            clients.get(currentPlayer).send(new ClientInputResponse("Remember you must choose your leaders!"));
            System.out.println("[SERVER] player can't send initial_discarding message");
            discardingMessage =  clients.get(currentPlayer).read();
        }
        notifyNewMessage(discardingMessage);
    }

    /**
     * This method handle the messages used for the initial_resources_setting
     * @param num is the num of resources that player can add
     */
    public void requestInitialResources(int num) {

        clients.get(currentPlayer).send(new ChooseResourcesMessage(num));
        sendToEveryoneExceptCurrentPlayer(new WaitingMessage("Other players are choosing starting resources"));
        Message resourcesMessage = clients.get(currentPlayer).read();

        while (! (resourcesMessage instanceof ChooseResourcesMessage)) {
            clients.get(currentPlayer).send(new ClientInputResponse("Remember you must choose your resources!!"));
            System.out.println("[SERVER] player can't send initial_resources message");
            resourcesMessage =  clients.get(currentPlayer).read();
        }
        notifyNewMessage(resourcesMessage);
    }

    /**
     * This method is used at the starting of the match to send all the main information of the game
     * @param nicknames is the list of players' nickname
     * @param market is the game's developmentCard Market
     * @param marbles is the game's marbleMarket
     */
    public void setUp(List<String> nicknames, DevCardMarket market, Market marbles){
        updatePlayersNickname(nicknames);
        updateDevCardMarketState(market);
        updateMarketState(marbles);
    }

    /**
     * This method is used to wait for a message from the currentPlayer
     */
    public void catchMessages(){
        Message m = clients.get(currentPlayer).read();
        if (m != null)
            notifyNewMessage(m);
    }

    public void updateWinner(String winner) {
        System.out.println("[LOBBY #"+lobbyID +"] The winner is " + winner +", game ended");
        sendBroadcast(new WinnerMessage(winner));
        System.out.println();
    }

    /**
     * This method send a broadcast message with the name of the current player
     */
    public void startTurn() {
        for (ClientHandler clientHandler : clients.values()) {
            clientHandler.send(new it.polimi.ingsw.networking.message.StartTurnMessage(currentPlayer));
        }
    }

    public void endTurn(){
        clients.get(currentPlayer).setMyTurn(false);
    }

    /**
     * This two methods handle the sending of the response Server-side
     * @param errorMessage is a string to printf Client-side
     */
    public void handleClientInputError(String errorMessage){
        //send a error response to the current client
        clients.get(currentPlayer).send(new ClientInputResponse(errorMessage));
    }

    public void handleClientInput(String input){
        clients.get(currentPlayer).send(new ClientInputResponse(input));
    }

    public void handlesWinningForQuitting(String nickname) {
        System.out.println("[LOBBY #"+lobbyID +"] " +nickname+ " is the last player of lobby..");
        updateWinner(nickname);
    }

    /**
     * This method send a message to all clients
     * @param m is the message
     */
    public void sendBroadcast(Message m){
        synchronized (lock) {
            for (ClientHandler c : clients.values())
                c.send(m);
            lock.notifyAll();
        }
    }

    /**
     * This method send a message to all clients except the current player
     * @param m is the message
     */
    public void sendToEveryoneExceptCurrentPlayer(Message m){
        for(String s : clients.keySet()){
            if (!(s.equals(currentPlayer)))
                clients.get(s).send(m);
        }
    }

    /**
     * This method send a message to all clients except the disconnected player
     * @param playerNick is the nickname of disconnected player
     */
    public void sendToEveryoneExceptQuitPlayer(String playerNick) {
        synchronized (lock) {
            clients.remove(playerNick);
            lock.notifyAll();
        }
        sendBroadcast(new PlayerIsQuittingMessage(playerNick + " left the game!"));
    }

    /**
     * This method send a message to all clients except the disconnected player which is rejoining
     * @param ch is the client name to add in virtual view list
     */
    public void sendToEveryoneExceptRejoiningPlayer(ClientHandler ch) {
        for(String s : clients.keySet())
            if(!s.equals(ch.getUserNickname()))
                clients.get(s).send(new PlayerIsRejoiningMessage(ch.getUserNickname() + " is trying to re-join in the match..."));
    }

    /**
     *This method send a message to the current player
     * @param m is the message
     */
    public void sendToCurrentPlayer(Message m){clients.get(currentPlayer).send(m);}

    public void sendDisconnectionInSetUpGame(String quitPlayerNickname) {
        //send to other client a message that close theirs connections
        sendBroadcast(new RemoveClientForErrors(quitPlayerNickname+" left the game in set-up phase, please reconnect in another match to play"));
    }

    /**
     * This method is used to send the right state of game to the rejoining player
     * @param reJoiningNick is the re-joining player's nickname
     * @param m message to send
     */
    public void sendToRejoiningPlayer(String reJoiningNick, Object m) { clients.get(reJoiningNick).send(m); }

    public MarketUpdateMessage createMarketUpdateMessage(Market market){
        ResourceType marbleLeft = market.getMarbleLeft().getResourceType();
        List<ResourceType> marbleList = new ArrayList<>();

        for (int i=0; i<3; i++)
            for (int j=0; j<4; j++)
                marbleList.add(market.getMarbleByIndexes(i,j).getResourceType());

        return new MarketUpdateMessage(marbleList, marbleLeft);
    }

    @Override
    public void updateMarketState(Market market) {
        sendBroadcast(createMarketUpdateMessage(market));
    }

    public MarbleBufferUpdateMessage createMarbleBufferUpdateMessage(List<Marble> marbleLinkedList){
        ParserForModel parser = new ParserForModel();
        List <MarbleLight> marbleLightList = new ArrayList<>();
        for(Marble m : marbleLinkedList){
            marbleLightList.add(parser.parserFromMarbleToLight(m));
        }

        return new MarbleBufferUpdateMessage(marbleLightList);
    }

    @Override
    public void updateMarbleBuffer(List<Marble> marbleLinkedList) {
        clients.get(currentPlayer).send(createMarbleBufferUpdateMessage(marbleLinkedList));
    }

    public LeaderInHandUpdateMessage createLeaderInHandUpdateMessage(String nickname, List<LeaderCard> leaderCards){
        return new LeaderInHandUpdateMessage(leaderCards, nickname);
    }

    public OpponentsLeaderCardsInHandUpdateMessage createOpponentsLeaderCardsInHandUpdateMessage(String nickname, List<LeaderCard> leaderCards){
        List<LeaderCard> activeLeaders = new ArrayList<>();
        for (LeaderCard leaderCard : leaderCards)
            if (leaderCard.isActive())
                activeLeaders.add(leaderCard);

        return new OpponentsLeaderCardsInHandUpdateMessage(activeLeaders, leaderCards.size(), nickname);
    }

    @Override
    public void updateLeaderCards(List<LeaderCard> leaderCards) {
        //send to current
        sendToCurrentPlayer(createLeaderInHandUpdateMessage(currentPlayer, leaderCards));
        sendToEveryoneExceptCurrentPlayer(createOpponentsLeaderCardsInHandUpdateMessage(currentPlayer, leaderCards));
    }

    public StrongboxUpdateMessage createStrongboxUpdateMessage(String nickname, Strongbox strongbox){
        return new StrongboxUpdateMessage(nickname, strongbox.getNumResource(ResourceType.COIN),
                strongbox.getNumResource(ResourceType.SERVANT),
                strongbox.getNumResource(ResourceType.SHIELD),
                strongbox.getNumResource(ResourceType.STONE));
    }

    @Override
    public void updateStrongboxState(Strongbox strongbox) { sendBroadcast(createStrongboxUpdateMessage(currentPlayer, strongbox)); }

    public FaithTrackUpdateMessage createFaithTrackUpdateMessage(FaithTrack faithTrack){
        return new FaithTrackUpdateMessage(faithTrack.getOwner(), faithTrack.getPosition(),
                faithTrack.isFirstVaticanSectionPointsAchieved(),
                faithTrack.isSecondVaticanSectionPointsAchieved(),
                faithTrack.isThirdVaticanSectionPointsAchieved());
    }

    @Override
    public void updateFaithTrack(FaithTrack faithTrack) {
        sendBroadcast(createFaithTrackUpdateMessage(faithTrack));
    }

    public WarehouseUpdateMessage createWarehouseUpdateMessage(String nickname, Warehouse warehouse){
        return new WarehouseUpdateMessage(nickname, warehouse.getDepots());
    }

    @Override
    public void updateWarehouseState(Warehouse warehouse) {
        sendBroadcast(createWarehouseUpdateMessage(currentPlayer, warehouse));
    }

    public NicknamesUpdateMessage createNicknamesUpdateMessage(List<String> nicknames){
        return new NicknamesUpdateMessage(nicknames);
    }

    public void updatePlayersNickname(List<String> nicknames) {
        sendBroadcast(createNicknamesUpdateMessage(nicknames));
    }

    public DevCardMarketUpdateMessage createDevCardMarketUpdateState(DevCardMarket devCardMarket){
        return new DevCardMarketUpdateMessage(devCardMarket.getTopCards());
    }

    @Override
    public void updateDevCardMarketState(DevCardMarket devCardMarket) { sendBroadcast(createDevCardMarketUpdateState(devCardMarket)); }

    public LorenzoUpdateMessage createLorenzoUpdateMessage(LorenzoIlMagnifico lorenzoIlMagnifico){
        return new LorenzoUpdateMessage(lorenzoIlMagnifico.getPosition(), lorenzoIlMagnifico.getLastTokenExecuted());
    }

    @Override
    public void updateLorenzoState(LorenzoIlMagnifico lorenzoIlMagnifico) {
        clients.get(currentPlayer).send(createLorenzoUpdateMessage(lorenzoIlMagnifico));
    }

    @Override
    public void updateLorenzoPosition(LorenzoIlMagnifico lorenzoIlMagnifico) {
        clients.get(currentPlayer).send(createLorenzoUpdateMessage(lorenzoIlMagnifico));
    }

    public ProductionZoneUpdateMessage createProductionZoneUpdateMessage(PersonalBoard personalBoard){
        return new ProductionZoneUpdateMessage(personalBoard.getOwner().getNickname(), personalBoard.getProductionSlotByIndex(1), personalBoard.getProductionSlotByIndex(2), personalBoard.getProductionSlotByIndex(3), personalBoard.getTopExtraDevCardsInSlots());
    }

    @Override
    public void updateProductionZoneState(PersonalBoard personalBoard) { sendBroadcast(createProductionZoneUpdateMessage(personalBoard)); }

}
