package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.client.LightModel.market.MarketLight;
import it.polimi.ingsw.networking.message.updateMessage.*;

import java.util.ArrayList;
import java.util.List;

public class ModelLight {

    private MarketLight marbleMarket;
    private DevCardMarketLight devCardMarket;
    private List<PlayerLight> players;
    private LorenzoLight lorenzo;

    public ModelLight() {
        this.marbleMarket = new MarketLight();
        this.devCardMarket = new DevCardMarketLight();
        this.players = new ArrayList<>();
    }

    public PlayerLight getPlayerByNickname(String nickname){
        for (PlayerLight p : players)
            if(p.getNickname().equals(nickname))
                return p;
        return null;
    }

    public void updatePlayerStrongbox(StrongboxUpdateMessage m){
        getPlayerByNickname(m.getNickname()).updateStrongbox(m);
    }

    public void updatePlayerFaithTrack(FaithTrackUpdateMessage m){
        getPlayerByNickname(m.getNickname()).updateFaithTrack(m);
    }

    public void updatePlayerLeaderInHands(LeaderInHandUpdateMessage m){
        getPlayerByNickname(m.getNickname()).updateLeaderCardsInHand(m);
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

    public MarketLight getMarbleMarket() {
        return marbleMarket;
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

    public void printProductionZone(String nickname){
        getPlayerByNickname(nickname).printProductionZone();
    }

    public void printStrongbox(String nickname){
        getPlayerByNickname(nickname).printStrongbox();
    }

    public void printWarehouse(String nickname){
        getPlayerByNickname(nickname).printWarehouse();
    }

    public void printStock(String nickname){
        printWarehouse(nickname);
        printStrongbox(nickname);
    }

    public void printPersonalBoard(String nickname){
        printFaithTrack(nickname);
        printProductionZone(nickname);
        printStock(nickname);
    }


    public void printLeaderCardInHand(String nickname){getPlayerByNickname(nickname).printLeaderCardsInHand();}

    public void addPlayer(PlayerLight playerLight){
        players.add(playerLight);
    }

    public void updatePlayersNickname(NicknamesUpdateMessage m) {
        List<String> nicknames = m.getPlayersNickname();

        for (String nickname : nicknames){
            addPlayer(new PlayerLight(nickname));
            //System.out.println(nickname + " aggiunto nel modelLight");
        }

        if (players.size() == 1){
            lorenzo = new LorenzoLight();
        }
    }


}
