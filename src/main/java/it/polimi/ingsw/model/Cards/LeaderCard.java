package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.LeaderCardPowerAdder;
import it.polimi.ingsw.model.ResourceType;

import java.util.List;

public abstract class LeaderCard extends Card {

    protected List <Requirement> requirements;
    protected ResourceType resourceType;
    protected boolean active;

    public LeaderCard(List<Requirement> requirements, ResourceType resourceType, int id, int victoryPoints) {
        this.requirements = requirements;
        this.resourceType = resourceType;
        this.id = id;
        this.victoryPoints = victoryPoints;
        this.active = false;
    }

    public abstract void activateCard(LeaderCardPowerAdder leaderCardPowerAdder);

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public ResourceType getAbilityResource(){
        return resourceType;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isExtraDevCard(){
        return false;
    }

    public boolean isDiscountCard(){
        return false;
    }

    public boolean isConvertWhiteCard() {
        return false;
    }

    public boolean isExtraDepotCard(){
        return false;
    }

    public void attivazioneCartaPerTestareCarteLight(){
        active=true;
    }
}
