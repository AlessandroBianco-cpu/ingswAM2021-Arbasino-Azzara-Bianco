package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.client.LightModel.market.BufferLight;
import it.polimi.ingsw.client.LightModel.market.MarbleLight;
import it.polimi.ingsw.networking.message.updateMessage.*;

import java.util.List;

public class PlayerLight {

    private String nickname;
    private StrongboxLight strongbox;
    private WarehouseLight warehouse;
    private FaithTrackLight faithTrack;
    private BufferLight buffer;
    private LeaderCardsInHandLight leaderCardsInHand;
    private ProductionZoneLight productionZone;

    public PlayerLight(String nickname) {
        this.nickname = nickname;
        this.strongbox = new StrongboxLight();
        this.warehouse = new WarehouseLight();
        this.faithTrack = new FaithTrackLight();
        this.buffer = new BufferLight();
        this.leaderCardsInHand = new LeaderCardsInHandLight();
        this.productionZone = new ProductionZoneLight();
    }

    public void updateBuffer(List<MarbleLight> buffer) {
        this.buffer.updateBuffer(buffer);
    }

    public void updateStrongbox(StrongboxUpdateMessage m){
        strongbox.update(m);
    }

    public void updateFaithTrack(FaithTrackUpdateMessage m){
        faithTrack.update(m);
    }

    public void updateWarehouse(WarehouseUpdateMessage m){warehouse.updateWarehouseLight(m);}

    public void updateLeaderCardsInHand(LeaderInHandUpdateMessage m){
        leaderCardsInHand.updateLeaderInHand(m);
    }

    public void updateProductionZone(ProductionZoneUpdateMessage m){
        productionZone.updateProductionZoneLight(m);
    }

    public void printBuffer(){
        buffer.print();
    }

    public void printLeaderCardsInHand(){
        leaderCardsInHand.print();
    }

    public void printWarehouse(){
        warehouse.print();
    }

    public void printStrongbox(){strongbox.print();}

    public void printProductionZone(){productionZone.print();}

    public void printFaithTrack(){faithTrack.print();}

    public String getNickname() {
        return nickname;
    }

}
