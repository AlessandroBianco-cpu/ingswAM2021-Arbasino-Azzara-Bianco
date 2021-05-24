package it.polimi.ingsw.model.Cards;

import java.io.Serializable;

public abstract class Card implements Serializable {

    protected int id;
    protected int victoryPoints;

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
