package it.polimi.ingsw.model.Cards;

import java.io.Serializable;

/**
 * Class used to represent a Card
 */
public abstract class Card implements Serializable {

    protected int id;
    protected int victoryPoints;

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getId() { return id; }

}
