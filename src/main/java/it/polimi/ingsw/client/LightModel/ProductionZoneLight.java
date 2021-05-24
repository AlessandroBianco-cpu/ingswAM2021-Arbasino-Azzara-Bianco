package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.Cards.ExtraDevCard;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.updateMessage.ProductionZoneUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public class ProductionZoneLight {
    private DevCard[] cardsInProductionSlot;
    private List<ExtraDevCard> activatedExtraCards;
    private final ParserForModel parser = new ParserForModel();

    public ProductionZoneLight() {
        cardsInProductionSlot = new DevCard[3];
        activatedExtraCards = new ArrayList<>();
    }

    public void updateProductionZoneLight(ProductionZoneUpdateMessage message){
        cardsInProductionSlot = message.getCardsInProductionSlot();
        activatedExtraCards = message.getActivatedExtraCards();
    }

    private void displayTopCorner(DevCard card){
        if(card != null)
            System.out.print(parser.parseCardColor(card.getColor()) + "╔═══════════╗" + ConsoleColors.RESET);
        else
            System.out.print("╔═══════════╗");
    }

    private void displayTopCorner(ExtraDevCard card){
        if(card != null)
            System.out.print("╔═══════════╗");
        else
            System.out.print("");
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

    private void displayIndex(ExtraDevCard card, int index){
        if(card != null) {
            if (index < 10)
                System.out.print("║ index: " + index + "  ║");
            else
                System.out.print("║ index: " + index + " ║");
        }
        else
            System.out.print("");
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

    private void displayResource(ExtraDevCard card, String typeOfList){
        if( card != null){
            if(typeOfList.equals("OUTPUT")){
                System.out.print("║   ?   " + parser.parseQuantityResource(new QuantityResource(ResourceType.FAITH,1)) + "   ║");
            }else if(typeOfList.equals("INPUT")){
                QuantityResource resource = new QuantityResource(card.getAbilityResource(),1);
                System.out.print("║     " + parser.parseQuantityResource(resource) + "     ║");
            }else
                System.out.print("║           ║");

        }else
            System.out.print("");
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

    private void displayPoints(ExtraDevCard card){
        if( card != null){
            int vp = card.getVictoryPoints();
            if(vp<10)
                System.out.print("║    vp" + vp + "    ║" );
            else
                System.out.print("║    vp" + vp + "   ║");
        }else
            System.out.print("");
    }

    private void displayArrows(DevCard card){
        if ( card != null){
            System.out.print(parser.parseCardColor(card.getColor()) + "║" + ConsoleColors.RESET + "   ↓ ↓ ↓   " + parser.parseCardColor(card.getColor()) + "║"+ ConsoleColors.RESET);
        }
        else
            System.out.print("║           ║");
    }

    private void displayArrows(ExtraDevCard card){
        if ( card != null){
            System.out.print("║   ↓ ↓ ↓   ║");
        }
        else
            System.out.print("");
    }

    private void displayBottom(DevCard card){
        if(card != null)
            System.out.print(parser.parseCardColor(card.getColor()) + "╚═══════════╝" + ConsoleColors.RESET);
        else
            System.out.print("╚═══════════╝");
    }

    private void displayBottom(ExtraDevCard card){
        if(card != null)
            System.out.print("╚═══════════╝");
        else
            System.out.print("");
    }

    public void print(){
        System.out.print("╔═══════════╗");
        int i;
        for(i=0; i<3; i++){
            displayTopCorner(cardsInProductionSlot[i]);
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayTopCorner(card);
        }
        System.out.println();

        System.out.print("║ index: 0  ║");
        for(i=0; i<3; i++){
            displayIndex(cardsInProductionSlot[i],i+1 );
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayIndex(card, i+1);
            i++;
        }
        System.out.println();

        System.out.print("║           ║");
        for(i=0; i<3; i++){
            displayResource(cardsInProductionSlot[i],"COST");
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayResource(card, "NOTHING");
        }
        System.out.println();

        System.out.print("║           ║");
        for(i=0; i<3; i++){
            displayLevelAndPoints(cardsInProductionSlot[i]);
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayPoints(card);
        }
        System.out.println();

        System.out.print("║   ?   ?   ║");
        for(i=0; i<3; i++){
            displayResource(cardsInProductionSlot[i], "INPUT" );
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayResource(card, "INPUT");
        }
        System.out.println();

        System.out.print("║   ↓ ↓ ↓   ║");
        for(i=0; i<3; i++){
            displayArrows(cardsInProductionSlot[i]);
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayArrows(card);
        }
        System.out.println();

        System.out.print("║     ?     ║");
        for(i=0; i<3; i++){
            displayResource(cardsInProductionSlot[i], "OUTPUT" );
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayResource(card, "OUTPUT");
        }
        System.out.println();

        System.out.print("╚═══════════╝");

        for(i=0; i<3; i++){
            displayBottom(cardsInProductionSlot[i]);
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayBottom(card);
        }
        System.out.println();
    }
}
