package it.polimi.ingsw.model.Cards;

public class CardRequirement extends Requirement {

    private int level;
    private int quantity;
    private DevCardColor color;

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
