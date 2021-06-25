package it.polimi.ingsw.model.Cards;

/**
 * This class represents a requirement of card type (i.e LVL1 Green Card)
 */
public class CardRequirement extends Requirement {

    private final int level;
    private final int quantity;
    private final DevCardColor color;

    public CardRequirement(int level, int quantity, DevCardColor color) {
        this.level = level;
        this.quantity = quantity;
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    public int getQuantity() {
        return quantity;
    }

    public DevCardColor getColor() {
        return color;
    }

    @Override
    public boolean isCardRequirement() {
        return true;
    }
}
