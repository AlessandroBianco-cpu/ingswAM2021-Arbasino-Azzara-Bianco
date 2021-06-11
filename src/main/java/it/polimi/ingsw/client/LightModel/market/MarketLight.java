package it.polimi.ingsw.client.LightModel.market;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.updateMessage.MarketUpdateMessage;

import java.util.List;

/**
 * Lightweight representation of the Marble Market
 */
public class MarketLight {

    private final int ROW_SIZE = 4;
    private final int COL_SIZE = 3;

    private final MarbleLight[][] marbleMatrix;
    private MarbleLight marbleLeft;

    public MarketLight() {
        marbleMatrix = new MarbleLight[COL_SIZE][ROW_SIZE];
    }

    /**
     * Updates the status of the market
     * @param message message with the updated market
     */
    public void updateMarketLight(MarketUpdateMessage message){
        List<ResourceType> marbleUpdate = message.getMarketStatus();
        for(int i = 0; i < COL_SIZE; i++)
            for (int j = 0; j < ROW_SIZE; j++)
                marbleMatrix[i][j] = parseResource(marbleUpdate.remove(0));

        marbleLeft = parseResource(message.getMarbleLeft());
    }

    // ------------------------ GETTERS ------------------------

    public MarbleLight[][] getMarbleMatrix() {
        return marbleMatrix;
    }

    public MarbleLight getMarbleLeft() {
        return marbleLeft;
    }

    // ------------------------ PRINTER ------------------------

    /**
     * Shows the status of the marbleMarket
     */
    public void print(){
        System.out.println("Market");
        for (int i = 0; i < COL_SIZE; i++){
            System.out.print(i+1 + " ");
            for (int j = 0; j < ROW_SIZE; j++){
                System.out.print(marbleMatrix[i][j].toString() + " ");
            }
            System.out.println();
        }
        System.out.print("  ");
        System.out.print("\u20091 ");
        System.out.print("\u20092 ");
        System.out.print("\u20093 ");
        System.out.print("\u20094 \n");

        System.out.print("Marble Left -> " + marbleLeft.toString() + "\n");

    }

    private MarbleLight parseResource(ResourceType resource){
        switch (resource){
            case SERVANT:
                return new PurpleMarbleLight();
            case COIN:
                return new YellowMarbleLight();
            case SHIELD:
                return new BlueMarbleLight();
            case STONE:
                return new GreyMarbleLight();
            case FAITH:
                return new RedMarbleLight();
            default:
                return new WhiteMarbleLight();
        }
    }
}
