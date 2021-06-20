package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.Cards.ExtraDevCard;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.updateMessage.ProductionZoneUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

/**
 * Lightweight representation of the Development slots. It is stored client-side
 */
public class ProductionZoneLight {
    private List<DevCard> firstSlot;
    private List<DevCard> secondSlot;
    private List<DevCard> thirdSlot;
    private List<ExtraDevCard> activatedExtraCards;
    private final ParserForModel parser = new ParserForModel();

    public ProductionZoneLight() {
        activatedExtraCards = new ArrayList<>();
        firstSlot = new ArrayList<>();
        secondSlot = new ArrayList<>();
        thirdSlot = new ArrayList<>();
    }

    /**
     * Updates the state of the slots
     * @param message updated slots
     */
    public void updateProductionZoneLight(ProductionZoneUpdateMessage message){
        firstSlot = message.getFirstDevSlot();
        secondSlot = message.getSecondDevSlot();
        thirdSlot = message.getThirdDevSlot();
        activatedExtraCards = message.getActivatedExtraCards();
    }
    
    private List<DevCard> slotByIndex(int index){
        switch(index){
            case 0:
                return firstSlot;
            case 1:
                return secondSlot;
            case 2:
                return thirdSlot;
            default: //can't never return the default value
                return null;
        }
    }
    
    // ------------------------ GETTERS ------------------------


    public List<DevCard> getFirstSlot() {
        return firstSlot;
    }

    public List<DevCard> getSecondSlot() {
        return secondSlot;
    }

    public List<DevCard> getThirdSlot() {
        return thirdSlot;
    }

    private static DevCard getTopCard (List<DevCard> slot) {
        if(slot.size()>0)
            return slot.get(slot.size() - 1);
        return null;
    }

    // ------------------------ PRINTERS ------------------------
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

    private void displayCoveredCards(List<DevCard> slot, int round){
        if(slot.size() <= 1){
            System.out.print("             ");
        }else if(slot.size() == 2){
            if(round == 0){
                String color = parser.parseCardColor(slot.get(0).getColor());
                System.out.print(color + "══════1══════" + ConsoleColors.RESET);
            }else
                System.out.print("             ");
        }else {
            if(round == 0) {
                String color = parser.parseCardColor(slot.get(1).getColor());
                System.out.print(color + "══════2══════" + ConsoleColors.RESET);
            }else{
                String color = parser.parseCardColor(slot.get(1).getColor());
                System.out.print(color + "══════1══════" + ConsoleColors.RESET);
            }
        }


    }

    public void print(){
        System.out.print("╔═══════════╗");
        int i;
        for(i=0; i<3; i++){
            displayTopCorner(getTopCard(slotByIndex(i)));
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayTopCorner(card);
        }
        System.out.println();

        System.out.print("║ index: 0  ║");
        for(i=0; i<3; i++){
            displayIndex(getTopCard(slotByIndex(i)),i+1 );
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayIndex(card, i+1);
            i++;
        }
        System.out.println();

        System.out.print("║           ║");
        for(i=0; i<3; i++){
            displayResource(getTopCard(slotByIndex(i)),"COST");
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayResource(card, "NOTHING");
        }
        System.out.println();

        System.out.print("║           ║");
        for(i=0; i<3; i++){
            displayLevelAndPoints(getTopCard(slotByIndex(i)));
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayPoints(card);
        }
        System.out.println();

        System.out.print("║   ?   ?   ║");
        for(i=0; i<3; i++){
            displayResource(getTopCard(slotByIndex(i)), "INPUT" );
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayResource(card, "INPUT");
        }
        System.out.println();

        System.out.print("║   ↓ ↓ ↓   ║");
        for(i=0; i<3; i++){
            displayArrows(getTopCard(slotByIndex(i)));
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayArrows(card);
        }
        System.out.println();

        System.out.print("║     ?     ║");
        for(i=0; i<3; i++){
            displayResource(getTopCard(slotByIndex(i)), "OUTPUT" );
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayResource(card, "OUTPUT");
        }
        System.out.println();

        System.out.print("╚═══════════╝");

        for(i=0; i<3; i++){
            displayBottom(getTopCard(slotByIndex(i)));
        }
        for(ExtraDevCard card : activatedExtraCards){
            displayBottom(card);
        }
        System.out.println();
        for(i = 0; i<2; i++){
            System.out.print("             ");
            for(int j = 0; j<3; j++){
                displayCoveredCards(slotByIndex(j), i);
            }
            System.out.println();
        }


    }
}
