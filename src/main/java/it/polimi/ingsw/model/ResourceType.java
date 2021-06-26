package it.polimi.ingsw.model;

public enum ResourceType {
    SHIELD (true, false),
    COIN(true, false),
    SERVANT(true, false),
    STONE(true, false),
    FAITH(false, true),
    NOTHING(false, false);

    private final boolean canAddToSupply;
    private final boolean canAddToFaithTrack;

    ResourceType(boolean canAddToSupply, boolean canAddToFaithTrack) {
        this.canAddToSupply = canAddToSupply;
        this.canAddToFaithTrack = canAddToFaithTrack;
    }

    public boolean canAddToSupply() {
        return canAddToSupply;
    }

    public boolean canAddToFaithTrack() {
        return canAddToFaithTrack;
    }
}
