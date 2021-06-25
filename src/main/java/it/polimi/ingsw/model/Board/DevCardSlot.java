package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Cards.CardRequirement;
import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.model.QuantityResource;

import java.util.ArrayList;
import java.util.List;

/**
 * The class represents a DevCardSlot of the Personal Board. There can be up to 3 cards per slot and
 * a card can be added to the Slot if and only if the card is a level higher than the one on the Slot
 */
public class DevCardSlot extends ProductionSlot {

    private final List<DevCard> cards;

    public DevCardSlot() {
        this.cards = new ArrayList<>();
    }

    /**
     * @return the card on top of the slot if present, null otherwise
     */
    public DevCard getTopCard () {
        if(cards.size()>0)
            return cards.get(cards.size() - 1);
        return null;
    }

    /**
     * Adds a card in the slot
     * @param card card to add
     */
    public void addCard (DevCard card) {
        cards.add(card);
    }

    @Override
    public List<QuantityResource> getProductionPowerInput() {
        return getTopCard().getProductionPowerInput();
    }

    @Override
    public List<QuantityResource> getProductionPowerOutput() {
        return getTopCard().getProductionPowerOutput();
    }

    @Override
    public int numberOfCardsSatisfyingCardRequirement(CardRequirement cardRequirement){
        int counter = 0;
        for (DevCard c : cards){
            if (c.getColor() == cardRequirement.getColor() && c.getLevel() >= cardRequirement.getLevel())
                counter++;
        }
        return counter;
    }

    /**
     * @return the level of the card on top of the deck if present, 0 otherwise
     */
    public int getTopCardLevel(){
        return cards.size();
    }

    @Override
    boolean hasCards() {
        return (cards.size() > 0);
    }

    public List<DevCard> getCards() {
        return cards;
    }
}
