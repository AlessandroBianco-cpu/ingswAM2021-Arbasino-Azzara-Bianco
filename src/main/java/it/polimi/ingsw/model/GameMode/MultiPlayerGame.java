package it.polimi.ingsw.model.GameMode;

import it.polimi.ingsw.model.Player;

public class MultiPlayerGame extends Game{

    @Override
    public void advanceAfterDiscard(Player discardingPlayer) {
        for (Player p : players){
            if(!(p.getNickname().equals(discardingPlayer.getNickname()))){
                p.moveForwardNPositions(1);
            }
        }
    }



}
