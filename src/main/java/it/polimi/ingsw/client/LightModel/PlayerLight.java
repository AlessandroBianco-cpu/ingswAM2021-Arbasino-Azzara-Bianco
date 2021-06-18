package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.client.LightModel.market.BufferLight;
import it.polimi.ingsw.client.LightModel.market.MarbleLight;
import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.networking.message.PlacementDevCardMessage;
import it.polimi.ingsw.networking.message.updateMessage.*;
import it.polimi.ingsw.utils.ConsoleColors;

import java.util.List;

/**
 * Lightweight representation of the player. It is stored client-side
 */
public class PlayerLight {

    private final String nickname;
    private final StrongboxLight strongbox;
    private final WarehouseLight warehouse;
    private final FaithTrackLight faithTrack;
    private final BufferLight buffer;
    private final LeaderCardsInHandLight leaderCardsInHand;
    private final ProductionZoneLight productionZone;
    private final ResourceBufferLight resourceBufferLight;
    private DevCard boughtCard;
    private boolean inkwell;

    public PlayerLight(String nickname) {
        this.nickname = nickname;
        this.strongbox = new StrongboxLight();
        this.warehouse = new WarehouseLight();
        this.faithTrack = new FaithTrackLight();
        this.buffer = new BufferLight();
        this.leaderCardsInHand = new LeaderCardsInHandLight();
        this.productionZone = new ProductionZoneLight();
        this.resourceBufferLight = new ResourceBufferLight();
        this.boughtCard = null;
        this.inkwell = false;
    }

    // ------------------------ GETTERS ------------------------
    public String getNickname() {
        return nickname;
    }

    public boolean hasInkwell() { return inkwell; }

    public LeaderCardsInHandLight getLeaderCardsInHand() {
        return leaderCardsInHand;
    }

    public WarehouseLight getWarehouse() { return warehouse; }

    public DevCard getBoughtCard() { return boughtCard; }

    public StrongboxLight getStrongbox() { return strongbox; }

    public FaithTrackLight getFaithTrack() {
        return faithTrack;
    }

    public ProductionZoneLight getProductionZone() {
        return productionZone;
    }

    public BufferLight getBuffer() { return buffer; }

    public ResourceBufferLight getResourceBufferLight() { return resourceBufferLight; }

    public void printBoughtCard() {
        ParserForModel parser = new ParserForModel();
        List<QuantityResource> list;
        String color = parser.parseCardColor(boughtCard.getColor());
        if( boughtCard != null){
            System.out.println(color + "╔═══════════╗" + ConsoleColors.RESET);
            System.out.println(color + "║           ║" + ConsoleColors.RESET);
            list = boughtCard.getCost();
            System.out.print(color + "║  " + ConsoleColors.RESET);
            for (int i = 0; i < 3; i++) {
                if (i < list.size()) {
                    System.out.print(parser.parseQuantityResource(list.get(i)) + "  ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println(color + "║" + ConsoleColors.RESET);
            int vp = boughtCard.getVictoryPoints();
            if (vp < 10)
                System.out.println(color + "║" + ConsoleColors.RESET + " lv" + boughtCard.getLevel() + "   vp" + vp + color + " ║" + ConsoleColors.RESET);
            else
                System.out.println(color + "║" + ConsoleColors.RESET + " lv" + boughtCard.getLevel() + "  vp" + vp + color + " ║" + ConsoleColors.RESET);
            list = boughtCard.getProductionPowerInput();
            System.out.print(color + "║  " + ConsoleColors.RESET);
            for (int i = 0; i < 3; i++) {
                if (i < list.size()) {
                    System.out.print(parser.parseQuantityResource(list.get(i)) + "  ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println(color + "║" + ConsoleColors.RESET);
            System.out.println(color + "║" + ConsoleColors.RESET + "   ↓ ↓ ↓   " + parser.parseCardColor(boughtCard.getColor()) + "║" + ConsoleColors.RESET);
            list = boughtCard.getProductionPowerOutput();
            System.out.print(color + "║  " + ConsoleColors.RESET);
            for (int i = 0; i < 3; i++) {
                if (i < list.size()) {
                    System.out.print(parser.parseQuantityResource(list.get(i)) + "  ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println(color + "║" + ConsoleColors.RESET);
            System.out.println(color + "╚═══════════╝" + ConsoleColors.RESET);
        }
    }

    public void setInkwell(boolean inkwell) { this.inkwell = inkwell; }

    // ------------------------ PRINTERS ------------------------
    public void printBuffer(){
        buffer.print();
    }

    public void printLeaderCardsInHand(){
        leaderCardsInHand.print();
    }

    public void printOpponentLeaderCardsInHand(){leaderCardsInHand.opponentPrint();}

    public void printWarehouse(){
        warehouse.print();
    }

    public void printStrongbox(){strongbox.print();}

    public void printProductionZone(){productionZone.print();}

    public void printFaithTrack(){faithTrack.print();}

    public void printResourceBuffer(){resourceBufferLight.print();}

    // ------------------------ UPDATES ------------------------
    public void updateBuffer(List<MarbleLight> buffer) {
        this.buffer.updateBuffer(buffer);
    }

    public void updateResourcesBuffer(CardPaymentResourceBufferUpdateMessage m){
        resourceBufferLight.updateResourceBufferLight(m);
    }

    public void updateResourcesBuffer(ProductionResourceBufferUpdateMessage m){
        resourceBufferLight.updateResourceBufferLight(m);
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

    public void updateOpponentsLeaderCardsInHand(OpponentsLeaderCardsInHandUpdateMessage m){leaderCardsInHand.updateOpponentLeaderInHand(m);}

    public void updateProductionZone(ProductionZoneUpdateMessage m){
        productionZone.updateProductionZoneLight(m);
    }

    public void updateBoughtCard(PlacementDevCardMessage m){
        this.boughtCard = m.getBoughtCard();
    }

}
