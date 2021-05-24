package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.model.Cards.Requirement;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.networking.message.updateMessage.LeaderInHandUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public class LeaderCardsInHandLight {
    private List<LeaderCard> cards;
    private final ParserForModel parser = new ParserForModel();
    public LeaderCardsInHandLight() {
        this.cards = new ArrayList<>();
    }

    public void updateLeaderInHand(LeaderInHandUpdateMessage message){
        this.cards = message.getCards();
    }

    private void displayTopCorner(LeaderCard card){
        if(card != null)
            System.out.print(parser.parseActiveCard(card) + "╔═══════════╗" + ConsoleColors.RESET);
        else
            System.out.print("");
    }

    private void displayIndex(LeaderCard card, int index){
        if(card != null) {
            System.out.print(parser.parseActiveCard(card) + "║  index: " + index + " ║" + ConsoleColors.RESET);
        }
        else
            System.out.print("");
    }

    private void displayRequirement(LeaderCard card){
        if(card != null){
            List<Requirement> list = card.getRequirements();
            if(card.isExtraDepotCard()){
                System.out.print(parser.parseActiveCard(card) +"║     " + ConsoleColors.RESET + parser.parseQuantityResource(card.getRequirements().get(0).getResource()) + parser.parseActiveCard(card) +"     ║" );
            }else{
                System.out.print((parser.parseActiveCard(card) +"║" + ConsoleColors.RESET));
                for (int i = 0; i<2; i++){
                    if (i<list.size()){
                        System.out.print(parser.parseCardColor(list.get(i).getColor()) + "lv" +list.get(i).getLevel()+"#"+ list.get(i).getQuantity()+ ConsoleColors.RESET);
                        if(i == 0)
                            System.out.print(" ");
                    }
                    else{
                        System.out.print("     ");
                    }
                }
                System.out.print(parser.parseActiveCard(card) +"║" + ConsoleColors.RESET);
            }
        }else
            System.out.print("");
    }

    private void displayPoints(LeaderCard card){
        if( card != null){
            int vp = card.getVictoryPoints();
            String color = parser.parseActiveCard(card);
            if(vp<10)
                System.out.print(color + "║" + ConsoleColors.RESET + "    vp" + vp + color + "    ║" + ConsoleColors.RESET);
            else
                System.out.print(color + "║" + ConsoleColors.RESET +"    vp" + vp + color + "   ║" + ConsoleColors.RESET);
        }else
            System.out.print("");
    }

    private void displayCardType(LeaderCard card){
        if(card != null) {
            String color = parser.parseActiveCard(card);
            System.out.print(color + "║" + ConsoleColors.RESET);
            if (card.isConvertWhiteCard()) {
                System.out.print("  convert  ");
            } else if (card.isDiscountCard()) {
                System.out.print("  discount ");
            } else if (card.isExtraDepotCard()) {
                System.out.print(" Xtradepot ");
            } else {
                System.out.print("  develop  ");
            }
            System.out.print(color + "║" + ConsoleColors.RESET);
        }else
            System.out.print("");
    }

    private void displayActive(LeaderCard card){
        if(card != null){
            if(card.isActive()){
                System.out.print(ConsoleColors.YELLOW + "║" + ConsoleColors.RESET + "  active" + ConsoleColors.GREEN +"v"+ ConsoleColors.YELLOW + "  ║" + ConsoleColors.RESET);
            }else{
                System.out.print(ConsoleColors.RESET + "║" + ConsoleColors.RESET + "  active" + ConsoleColors.RED +"x"+ ConsoleColors.RESET + "  ║" + ConsoleColors.RESET);
            }
        }else
            System.out.print("");
    }

    private void displayPower(LeaderCard card){
        if(card != null) {
            String color = parser.parseActiveCard(card);
            System.out.print(color + "║" + ConsoleColors.RESET);
            if (card.isConvertWhiteCard()) {
                System.out.print("  O >> " + parser.parseQuantityResource(new QuantityResource(card.getAbilityResource(), 1))+ "   ");
            } else if (card.isDiscountCard()) {
                System.out.print("    "+ parser.parseQuantityResource(new QuantityResource(card.getAbilityResource(), -1))+ "     ");
            } else if (card.isExtraDepotCard()) {
                System.out.print("  size: " + parser.parseQuantityResource(new QuantityResource(card.getAbilityResource(), 2))+ "  ");
            } else {
                System.out.print(" " + parser.parseQuantityResource(new QuantityResource(card.getAbilityResource(), 1)) + " >> ?+" + ConsoleColors.RED + "1  "+ ConsoleColors.RESET);
            }
            System.out.print(color + "║" + ConsoleColors.RESET);
        }else
            System.out.print("");
    }

    private void displayBottom(LeaderCard card){
        if(card != null)
            System.out.print(parser.parseActiveCard(card) + "╚═══════════╝" + ConsoleColors.RESET);
        else
            System.out.print("");
    }

    public void print(){
        for(int i = 0; i<cards.size(); i++){
            displayTopCorner(cards.get(i));
        }
        System.out.println();
        for(int i = 0; i<cards.size(); i++){
            displayIndex(cards.get(i), (i+1));
        }
        System.out.println();
        for(int i = 0; i<cards.size(); i++){
            displayRequirement(cards.get(i));
        }
        System.out.println();
        for(int i = 0; i<cards.size(); i++){
            displayPoints(cards.get(i));
        }
        System.out.println();
        for(int i = 0; i<cards.size(); i++){
            displayCardType(cards.get(i));
        }
        System.out.println();
        for(int i = 0; i<cards.size(); i++){
            displayActive(cards.get(i));
        }
        System.out.println();
        for(int i = 0; i<cards.size(); i++){
            displayPower(cards.get(i));
        }
        System.out.println();
        for(int i = 0; i<cards.size(); i++){
            displayBottom(cards.get(i));
        }
        System.out.println();
    }
}

