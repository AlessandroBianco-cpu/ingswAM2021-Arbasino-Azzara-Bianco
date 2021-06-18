package it.polimi.ingsw.client.LightModel;

import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.model.Cards.Requirement;
import it.polimi.ingsw.model.QuantityResource;
import it.polimi.ingsw.networking.message.updateMessage.LeaderInHandUpdateMessage;
import it.polimi.ingsw.networking.message.updateMessage.OpponentsLeaderCardsInHandUpdateMessage;
import it.polimi.ingsw.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

/**
 * Lightweight representation of the leader cards owned by the player. It is store client-side
 */
public class LeaderCardsInHandLight {
    private List<LeaderCard> cards;
    private List<LeaderCard> activeCards;
    private int sizeOfTotalLeadersInHand; //int value meant to be used to hide some informations when displaying opponent's leader cards
    private final ParserForModel parser = new ParserForModel();

    public int getSizeOfTotalLeadersInHand() {
        return sizeOfTotalLeadersInHand;
    }

    public LeaderCardsInHandLight() {
        this.cards = new ArrayList<>();
        this.activeCards = new ArrayList<>();
    }

    /**
     * Updates the state of the cards
     * @param message updated cards situation
     */
    public void updateLeaderInHand(LeaderInHandUpdateMessage message){
        this.cards = message.getCards();
        for(LeaderCard card : cards)
            if (card.isActive() && !(idIsInActiveCardsList(card)))
                activeCards.add(card);
    }

    /**
     * Method used to retrieve if the id is already in the list of an active list
     * @param cardToFind LeaderCard to look for
     * @return true if contained, false otherwise
     */
    private boolean idIsInActiveCardsList(LeaderCard cardToFind){
        for(LeaderCard leaderCard : activeCards)
            if (leaderCard.getId() == cardToFind.getId())
                return true;
        return false;
    }

    public void updateOpponentLeaderInHand(OpponentsLeaderCardsInHandUpdateMessage message){
        this.cards = message.getActiveCards();
        for(LeaderCard card : cards)
            if (card.isActive() && !(idIsInActiveCardsList(card)))
                activeCards.add(card);
        this.sizeOfTotalLeadersInHand = message.getNumberOfCards();
    }

    // ------------------------ GETTERS ------------------------

    public List<LeaderCard> getCards() {
        return cards;
    }

    public List<LeaderCard> getActiveCards() {
        return activeCards;
    }

    // ------------------------ PRINTERS ------------------------
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
                System.out.print(parser.parseActiveCard(card) +"║     " + ConsoleColors.RESET + parser.parseQuantityResource(card.getRequirements().get(0).getResource()) + parser.parseActiveCard(card) +"     ║" + ConsoleColors.RESET );
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

    private void backTop(){
        System.out.print("╔═══════════╗");
    }

    private void backOne(){
        System.out.print("║\\~ ~ ~ ~ ~/║");
    }

    private void backTwo(){
        System.out.print("║|\\~ ~ ~ ~/|║");
    }

    private void backCenter(){
        System.out.print("║||{{ : }}||║");
    }

    private void backThree(){
        System.out.print("║|/~ ~ ~ ~\\|║");
    }

    private void backFour(){
        System.out.print("║/~ ~ ~ ~ ~\\║");
    }

    private void backBottom(){
        System.out.print("╚═══════════╝");
    }

    public void print(){
        for (LeaderCard card : cards) {
            displayTopCorner(card);
        }
        System.out.println();
        for(int i = 0; i<cards.size(); i++){
            displayIndex(cards.get(i), (i+1));
        }
        System.out.println();
        for (LeaderCard card : cards) {
            displayRequirement(card);
        }
        System.out.println();
        for (LeaderCard card : cards) {
            displayPoints(card);
        }
        System.out.println();
        for (LeaderCard card : cards) {
            displayCardType(card);
        }
        System.out.println();
        for (LeaderCard card : cards) {
            displayActive(card);
        }
        System.out.println();
        for (LeaderCard card : cards) {
            displayPower(card);
        }
        System.out.println();
        for (LeaderCard card : cards) {
            displayBottom(card);
        }
        System.out.println();
    }

    public void opponentPrint(){
        int activeCards = cards.size();
        for (int i=0; i<sizeOfTotalLeadersInHand; i++) {
            if(i < activeCards)
                displayTopCorner(cards.get(i));
            else
                backTop();
        }
        System.out.println();
        for (int i=0; i<sizeOfTotalLeadersInHand; i++) {
            if(i < activeCards)
                displayIndex(cards.get(i), (i+1));
            else
                backOne();
        }
        System.out.println();
        for (int i=0; i<sizeOfTotalLeadersInHand; i++) {
            if(i < activeCards)
                displayRequirement(cards.get(i));
            else
                backTwo();
        }
        System.out.println();
        for (int i=0; i<sizeOfTotalLeadersInHand; i++) {
            if(i < activeCards)
                displayPoints(cards.get(i));
            else
                backCenter();
        }
        System.out.println();
        for (int i=0; i<sizeOfTotalLeadersInHand; i++) {
            if(i < activeCards)
                displayCardType(cards.get(i));
            else
                backCenter();
        }
        System.out.println();
        for (int i=0; i<sizeOfTotalLeadersInHand; i++) {
            if(i < activeCards)
                displayActive(cards.get(i));
            else
                backThree();
        }
        System.out.println();
        for (int i=0; i<sizeOfTotalLeadersInHand; i++) {
            if(i < activeCards)
                displayPower(cards.get(i));
            else
                backFour();
        }
        System.out.println();
        for (int i=0; i<sizeOfTotalLeadersInHand; i++) {
            if(i < activeCards)
                displayBottom(cards.get(i));
            else
                backBottom();
        }
        System.out.println();
    }
}

