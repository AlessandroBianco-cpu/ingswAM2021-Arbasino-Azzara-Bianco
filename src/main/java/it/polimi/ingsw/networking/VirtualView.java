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

import static it.polimi.ingsw.utils.StringToPrint.*;

public class VirtualView extends ViewObservable implements MarketObserver, PlayerItemsObserver, StrongboxObserver, FaithTrackObserver, WarehouseObserver, DevCardMarketObserver, LorenzoObserver, ProductionZoneObserver {

    private Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private String currentPlayer;

    /**
     * Adds the client and its nickname to the clients map
     * @param name player nickname
     * @param client recipient client
     */
    public void addClient(String name, ClientHandler client){
        clients.put(name, client);
    }

    /**
     * Sets the client as current player
     * @param currentPlayer current player
     */
    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer = currentPlayer;
        clients.get(currentPlayer).send(startTurnMessage);
    }

    /**
     * Asks the lobby size
     * @param firstClient first player in the lobby
     */
    public void requestPlayersNum(ClientHandler firstClient) {

        int num = -1;
        System.out.println("[SERVER] Sending setting_num message");
        firstClient.send(setPlayersNumMessage);
        Message message = firstClient.read();

        while (! (message instanceof NumOfPlayerMessage)) {
            firstClient.send(new ClientInputResponse("Remember you must set the players number"));
            message = firstClient.read();
        }

        num = ((NumOfPlayerMessage) message).getNumPlayers();
        while (num < 1 || num > 4) {
            firstClient.send(setPlayersNumMessage);
            message = firstClient.read();
            num = ((NumOfPlayerMessage) message).getNumPlayers();
            }

        notifyPlayersNumber(num);
    }


    /**
     * This method handle the messages used for the nickname_setting
     * @param currClient is che current ClientHandler
     */
    public void requestNickname(ClientHandler currClient) {

        currClient.send(setNicknameMessage);
        Message nicknameMessage = currClient.read();

        while (! (nicknameMessage instanceof SettingNicknameMessage)) {
            currClient.send(new ClientInputResponse("Remember you must set the nickname"));
            System.out.println("[SERVER] player can't send setting_nickname message");
            nicknameMessage = currClient.read();
        }

        String nickname = ((SettingNicknameMessage) nicknameMessage).getNickname();
        notifyNickname(nickname);
    }

    /**
     * This method handle the messages used for the initial_leader_discard
     */
    public void requestInitialDiscard() {

        clients.get(currentPlayer).send(setLeadersMessage);
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
     * Sends an error message during the nickname choice
     * @param currClient player addressee
     */
    public void errorTakenNickname(ClientHandler currClient) {
        currClient.send(takenNameMessage);
        Message nicknameMessage = currClient.read();
        String nickname = ((SettingNicknameMessage) nicknameMessage).getNickname();
        notifyNickname(nickname);
    }

    /**
     * This method is used to wait for a message from the currentPlayer
     */
    public void catchMessages(){
        Message m = clients.get(currentPlayer).read();
        notifyNewMessage(m);
    }

    //TODO DA FARE OBSERVER PER IL GAME
    public void updateWinner(String winner) {
        System.out.println("[SERVER] The winner is " + winner);
        for (ClientHandler clientHandler : clients.values()){
            clientHandler.send(new WinnerMessage(winner));
        }
    }

    /**
     * This method send a broadcast message with the name of the current player
     */
    public void startTurn() {
        for (ClientHandler clientHandler : clients.values()) {
            clientHandler.send(new StartTurnMessage(currentPlayer));
        }
    }

    public void endTurn(){
        clients.get(currentPlayer).send(endTurnMessage);
    }


    /**
     * This two methods handle the sending of the response Server-side
     * @param errorMessage is a string to printf Client-side
     */
    public void handleClientInputError(String errorMessage){
        //mandi il messaggio al client
        clients.get(currentPlayer).send(new ClientInputResponse(errorMessage));
    }

    public void handleClientInputError(){
        //mandi il messaggio al client
        clients.get(currentPlayer).send(new ClientInputResponse());
    }


    /**
     * This method send a message to all clients
     * @param m is the message
     */
    private void sendBroadcast(Message m){
        for(ClientHandler c : clients.values())
            c.send(m);
    }

    @Override
    public void updateMarketState(Market market) {
        ResourceType marbleLeft = market.getMarbleLeft().getResourceType();
        List<ResourceType> marbleList = new ArrayList<>();

        for (int i=0; i<3; i++)
            for (int j=0; j<4; j++)
                marbleList.add(market.getMarbleByIndexes(i,j).getResourceType());

        sendBroadcast(new MarketUpdateMessage(marbleList,marbleLeft));
    }

    @Override
    public void updateMarbleBuffer(List<Marble> marbleLinkedList) {
        ParserForModel parser = new ParserForModel();
        List <MarbleLight> marbleLightList = new ArrayList<>();
        for(Marble m : marbleLinkedList){
            marbleLightList.add(parser.parserFromMarbleToLight(m));
        }
        clients.get(currentPlayer).send(new MarbleBufferUpdateMessage(marbleLightList));
    }

    @Override
    public void updateLeaderCards(List<LeaderCard> leaderCards) {
        sendBroadcast(new LeaderInHandUpdateMessage(leaderCards, currentPlayer));
    }

    @Override
    public void updateStrongboxState(Strongbox strongbox) {
        sendBroadcast(new StrongboxUpdateMessage(currentPlayer, strongbox.getNumResource(ResourceType.COIN),
                                                 strongbox.getNumResource(ResourceType.SERVANT),
                                                 strongbox.getNumResource(ResourceType.SHIELD),
                                                 strongbox.getNumResource(ResourceType.STONE)));
    }

    @Override
    public void updateFaithTrack(FaithTrack faithTrack) {
        sendBroadcast(new FaithTrackUpdateMessage(currentPlayer, faithTrack.getPosition(),
                faithTrack.isFirstVaticanSectionPointsAchieved(),
                faithTrack.isSecondVaticanSectionPointsAchieved(),
                faithTrack.isThirdVaticanSectionPointsAchieved()));
    }

    @Override
    public void updateWarehouseState(Warehouse warehouse) {
        sendBroadcast(new WarehouseUpdateMessage(currentPlayer, warehouse.getDepots()));
    }


    public void updatePlayersNickname(List<String> nicknames) {
        sendBroadcast(new NicknamesUpdateMessage(nicknames));
    }

    @Override
    public void updateDevCardMarketState(DevCardMarket devCardMarket) {
        sendBroadcast(new DevCardMarketUpdateMessage(devCardMarket.getTopCards()));
    }

    @Override
    public void updateLorenzoState(LorenzoIlMagnifico lorenzoIlMagnifico) {
        clients.get(currentPlayer).send(new LorenzoUpdateMessage(lorenzoIlMagnifico.getPosition(), lorenzoIlMagnifico.getLastTokenExecuted()));
    }

    @Override
    public void updateLorenzoPosition(LorenzoIlMagnifico lorenzoIlMagnifico) {
        clients.get(currentPlayer).send(new LorenzoUpdateMessage(lorenzoIlMagnifico.getPosition(), null));
    }

    @Override
    public void updateProductionZoneState(PersonalBoard personalBoard) {
        sendBroadcast(new ProductionZoneUpdateMessage(personalBoard.getOwner().getNickname(), personalBoard.getTopDevCardsInSlots(), personalBoard.getTopExtraDevCardsInSlots()));
    }
}
