package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.client.LightModel.market.MarketLight;
import it.polimi.ingsw.model.LorenzoIlMagnifico.ActionToken;
import it.polimi.ingsw.networking.message.PlacementDevCardMessage;
import it.polimi.ingsw.networking.message.updateMessage.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Read-only representation of game data. It is stored client-side
 */
public class ModelLight {

    private final MarketLight marbleMarket;
    private final DevCardMarketLight devCardMarket;
    private final List<PlayerLight> players;
    private String owner;
    private LorenzoLight lorenzo;

    public ModelLight() {
        this.marbleMarket = new MarketLight();
        this.devCardMarket = new DevCardMarketLight();
        this.players = new ArrayList<>();
    }

    public void setOwner(String nickname){ owner = nickname; }

    public void addPlayer(PlayerLight playerLight){
        players.add(playerLight);
    }

    // ------------------------ GETTERS ------------------------

    /**
     * Returns the player corresponding to the given input
     * @param nickname nickname of the player looked for
     * @return the PlayerLight of the player if present in the game, null otherwise
     */
    public PlayerLight getPlayerByNickname(String nickname){
        for (PlayerLight p : players)
            if(p.getNickname().equals(nickname))
                return p;
        return null;
    }

    public MarketLight getMarbleMarket() {
        return marbleMarket;
    }

    public DevCardMarketLight getDevCardMarket() { return devCardMarket; }

    public List<PlayerLight> getPlayers() { return players; }

    public int getLorenzoPosition(){
        return lorenzo.getPosition();
    }

    public int getNumberOfPlayers() { return players.size(); }

    public List<String> getOpponentsNicknames() {
        List<String> list = new ArrayList<>();
        for(PlayerLight p : players) {
            if (!(p.getNickname().equals(owner)))
                list.add(p.getNickname());
        }
        return list;
    }

    public ActionToken getLorenzoLatestUsedToken(){
        return lorenzo.getLastToken();
    }

    // ------------------------ UPDATES ------------------------

    public void updatePlayerStrongbox(StrongboxUpdateMessage m){
        getPlayerByNickname(m.getNickname()).updateStrongbox(m);
    }

    public void updatePlayerFaithTrack(FaithTrackUpdateMessage m){
        getPlayerByNickname(m.getNickname()).updateFaithTrack(m);
    }

    public void updatePlayerLeaderInHands(LeaderInHandUpdateMessage m){
        getPlayerByNickname(m.getNickname()).updateLeaderCardsInHand(m);
    }

    public void updateOpponentsLeaderInHands(OpponentsLeaderCardsInHandUpdateMessage m){
        getPlayerByNickname(m.getNickname()).updateOpponentsLeaderCardsInHand(m);
    }

    public void updateResourcesToPay (CardPaymentResourceBufferUpdateMessage m){
        getPlayerByNickname(owner).updateResourcesBuffer(m);
    }

    public void updateResourcesToPay (ProductionResourceBufferUpdateMessage m){
        getPlayerByNickname(owner).updateResourcesBuffer(m);
    }

    public void updateWarehouse(WarehouseUpdateMessage m){
        getPlayerByNickname(m.getNickname()).updateWarehouse(m);
    }

    public void updateProductionZone(ProductionZoneUpdateMessage m){
        getPlayerByNickname(m.getNickname()).updateProductionZone(m);
    }

    public void updateDevCardMarket(DevCardMarketUpdateMessage m){
        devCardMarket.updateDevCardMarketLight(m);
    }

    public void updateLorenzo(LorenzoUpdateMessage m){
        lorenzo.updateLorenzo(m);
    }

    public void updatePlayersNickname(NicknamesUpdateMessage m) {
        List<String> nicknames = m.getPlayersNickname();

        for (String nickname : nicknames){
            addPlayer(new PlayerLight(nickname));
        }

        if (players.size() == 1){
            lorenzo = new LorenzoLight();
        }
    }

    public void updateBoughtCard(PlacementDevCardMessage m){
        getPlayerByNickname(owner).updateBoughtCard(m);
    }

    // ------------------------ PRINTS ------------------------

    public void printOpponentNicknames(String ownerNickname){
        for (PlayerLight playerLight : players){
            if (!playerLight.getNickname().equals(ownerNickname))
                System.out.println(playerLight.getNickname());
        }
    }

    public void printBuffer(String nickname){
        getPlayerByNickname(nickname).printBuffer();
    }

    public void printDevCardMarket(){
        devCardMarket.print();
    }

    public void printFaithTrack(String nickname){
        getPlayerByNickname(nickname).printFaithTrack();
    }

    public void printLorenzo(){
        if(lorenzo != null)
            lorenzo.print();
    }

    /**
     * Shows the production slots of the player corresponding to the given nickname
     * @param nickname nickname of the player looked for
     */
    public void printProductionZone(String nickname){
        getPlayerByNickname(nickname).printProductionZone();
    }

    /**
     * Shows the strongbox of the player corresponding to the given nickname
     * @param nickname nickname of the player looked for
     */
    public void printStrongbox(String nickname){
        getPlayerByNickname(nickname).printStrongbox();
    }

    /**
     * Shows the warehouse of the player corresponding to the given nickname
     * @param nickname nickname of the player looked for
     */
    public void printWarehouse(String nickname){
        getPlayerByNickname(nickname).printWarehouse();
    }

    /**
     * Shows the warehouse and the strongbox of the player corresponding to the given nickname
     * @param nickname nickname of the player looked for
     */
    public void printStock(String nickname){
        printWarehouse(nickname);
        printStrongbox(nickname);
    }

    /**
     * Shows the personal board of the player corresponding to the given nickname
     * @param nickname nickname of the player looked for
     */
    public void printPersonalBoard(String nickname){
        printFaithTrack(nickname);
        printProductionZone(nickname);
        printStock(nickname);
    }

    /**
     * Shows the leader cards possessed by the player player looked for
     * @param nickname nickname of the player looked for
     */
    public void printLeaderCardInHand(String nickname){getPlayerByNickname(nickname).printLeaderCardsInHand();}

    public void printOpponentLeaderCardInHand(String nickname){getPlayerByNickname(nickname).printOpponentLeaderCardsInHand();}

    public void printBoughtCard(){
        getPlayerByNickname(owner).printBoughtCard();
    }
}
