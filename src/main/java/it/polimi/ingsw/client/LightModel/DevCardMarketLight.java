package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.networking.message.updateMessage.DevCardMarketUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

import java.util.List;

import static it.polimi.ingsw.utils.StaticUtils.NUMBER_OF_DEV_DECKS;

/**
 * Lightweight representation of the DevCardMarket stored client-side
 */
public class DevCardMarketLight {
    private final DevCard[] grid;
    private final ParserForModel parser = new ParserForModel();

    public DevCardMarketLight() {
        grid = new DevCard[NUMBER_OF_DEV_DECKS];
    }

    /**
     * Updates the state of the class
     * @param message updated state
     */
    public void updateDevCardMarketLight(DevCardMarketUpdateMessage message){
        DevCard[] update = message.getDevCardMarketStatus();
        for (int i = 0; i < NUMBER_OF_DEV_DECKS; i++)
            grid[i] = update[i];
    }

    public DevCard[] getGrid() {
        return grid;
    }

    // ------------------------ PRINTER METHODS ------------------------

    private void displayTopCorner(DevCard card){
        if(card != null)
            System.out.print(parser.parseCardColor(card.getColor()) + "╔═══════════╗" + ConsoleColors.RESET);
        else
            System.out.print("╔═══════════╗");
    }

    private void displayIndex(DevCard card, int index){
        if(card != null) {
            if (index < 10)
                System.out.print(parser.parseCardColor(card.getColor()) + "║ index: " + index + "  ║" + ConsoleColors.RESET);
            else
                System.out.print(parser.parseCardColor(card.getColor()) + "║ index: " + index + " ║" + ConsoleColors.RESET);
        }
        else
            System.out.print("║  index: " + index + " ║");
    }

    private void displayResource(DevCard card, String typeOfList){
        List<QuantityResource> list;
        if( card != null){
            if(typeOfList.equals("COST")){
                list = card.getCost();
            }else if(typeOfList.equals("INPUT")){
                list = card.getProductionPowerInput();
            }else
                list = card.getProductionPowerOutput();
            System.out.print(parser.parseCardColor(card.getColor()) +"║  " + ConsoleColors.RESET);
            for (int i = 0; i<3; i++){
                if (i<list.size()){
                    System.out.print(parser.parseQuantityResource(list.get(i)) + "  " );
                }else{
                    System.out.print("   ");
                }
            }
            System.out.print(parser.parseCardColor(card.getColor()) +"║" + ConsoleColors.RESET);
        }else
            System.out.print("║           ║");
    }

    private void displayLevelAndPoints(DevCard card){
        if( card != null){
            int vp = card.getVictoryPoints();
            String color = parser.parseCardColor(card.getColor());
            if(vp<10)
                System.out.print(color + "║" + ConsoleColors.RESET + " lv"+ card.getLevel() +"   vp" + vp + color + " ║" + ConsoleColors.RESET);
            else
                System.out.print(color + "║" + ConsoleColors.RESET + " lv"+ card.getLevel() +"  vp" + vp + color + " ║" + ConsoleColors.RESET);
        }else
            System.out.print("║           ║");
    }

    private void displayArrows(DevCard card){
        if ( card != null){
            System.out.print(parser.parseCardColor(card.getColor()) + "║" + ConsoleColors.RESET + "   ↓ ↓ ↓   " + parser.parseCardColor(card.getColor()) + "║"+ ConsoleColors.RESET);
        }
        else
            System.out.print("║           ║");
    }

    private void displayBottom(DevCard card){
        if(card != null)
            System.out.print(parser.parseCardColor(card.getColor()) + "╚═══════════╝" + ConsoleColors.RESET);
        else
            System.out.print("╚═══════════╝");
    }

    public void print(){

        displayTopCorner(grid[0]);
        displayTopCorner(grid[1]);
        displayTopCorner(grid[2]);
        displayTopCorner(grid[3]);
        System.out.println();
        displayIndex(grid[0],1);
        displayIndex(grid[1],2);
        displayIndex(grid[2],3);
        displayIndex(grid[3],4);
        System.out.println();
        displayResource(grid[0], "COST");
        displayResource(grid[1], "COST");
        displayResource(grid[2], "COST");
        displayResource(grid[3], "COST");
        System.out.println();
        displayLevelAndPoints(grid[0]);
        displayLevelAndPoints(grid[1]);
        displayLevelAndPoints(grid[2]);
        displayLevelAndPoints(grid[3]);
        System.out.println();
        displayResource(grid[0], "INPUT");
        displayResource(grid[1], "INPUT");
        displayResource(grid[2], "INPUT");
        displayResource(grid[3], "INPUT");
        System.out.println();
        displayArrows(grid[0]);
        displayArrows(grid[1]);
        displayArrows(grid[2]);
        displayArrows(grid[3]);
        System.out.println();
        displayResource(grid[0], "OUTPUT");
        displayResource(grid[1], "OUTPUT");
        displayResource(grid[2], "OUTPUT");
        displayResource(grid[3], "OUTPUT");
        System.out.println();
        displayBottom(grid[0]);
        displayBottom(grid[1]);
        displayBottom(grid[2]);
        displayBottom(grid[3]);
        System.out.println();

        displayTopCorner(grid[4]);
        displayTopCorner(grid[5]);
        displayTopCorner(grid[6]);
        displayTopCorner(grid[7]);
        System.out.println();
        displayIndex(grid[4],5);
        displayIndex(grid[5],6);
        displayIndex(grid[6],7);
        displayIndex(grid[7],8);
        System.out.println();
        displayResource(grid[4], "COST");
        displayResource(grid[5], "COST");
        displayResource(grid[6], "COST");
        displayResource(grid[7], "COST");
        System.out.println();
        displayLevelAndPoints(grid[4]);
        displayLevelAndPoints(grid[5]);
        displayLevelAndPoints(grid[6]);
        displayLevelAndPoints(grid[7]);
        System.out.println();
        displayResource(grid[4], "INPUT");
        displayResource(grid[5], "INPUT");
        displayResource(grid[6], "INPUT");
        displayResource(grid[7], "INPUT");
        System.out.println();
        displayArrows(grid[4]);
        displayArrows(grid[5]);
        displayArrows(grid[6]);
        displayArrows(grid[7]);
        System.out.println();
        displayResource(grid[4], "OUTPUT");
        displayResource(grid[5], "OUTPUT");
        displayResource(grid[6], "OUTPUT");
        displayResource(grid[7], "OUTPUT");
        System.out.println();
        displayBottom(grid[4]);
        displayBottom(grid[5]);
        displayBottom(grid[6]);
        displayBottom(grid[7]);
        System.out.println();

        displayTopCorner(grid[8]);
        displayTopCorner(grid[9]);
        displayTopCorner(grid[10]);
        displayTopCorner(grid[11]);
        System.out.println();
        displayIndex(grid[8],9);
        displayIndex(grid[9],10);
        displayIndex(grid[10],11);
        displayIndex(grid[11],12);
        System.out.println();
        displayResource(grid[8], "COST");
        displayResource(grid[9], "COST");
        displayResource(grid[10], "COST");
        displayResource(grid[11], "COST");
        System.out.println();
        displayLevelAndPoints(grid[8]);
        displayLevelAndPoints(grid[9]);
        displayLevelAndPoints(grid[10]);
        displayLevelAndPoints(grid[11]);
        System.out.println();
        displayResource(grid[8], "INPUT");
        displayResource(grid[9], "INPUT");
        displayResource(grid[10], "INPUT");
        displayResource(grid[11], "INPUT");
        System.out.println();
        displayArrows(grid[8]);
        displayArrows(grid[9]);
        displayArrows(grid[10]);
        displayArrows(grid[11]);
        System.out.println();
        displayResource(grid[8], "OUTPUT");
        displayResource(grid[9], "OUTPUT");
        displayResource(grid[10], "OUTPUT");
        displayResource(grid[11], "OUTPUT");
        System.out.println();
        displayBottom(grid[8]);
        displayBottom(grid[9]);
        displayBottom(grid[10]);
        displayBottom(grid[11]);
        System.out.println();

    }
}

