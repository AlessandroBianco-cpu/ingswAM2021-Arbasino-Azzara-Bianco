package it.polimi.ingsw.model.GameMode;

import it.polimi.ingsw.model.Player;

public class MultiPlayerGame extends Game{

    /**
     * Method called after a player discards a non-white marble during a market action.
     * Each player, except the one who discarded the marble, will advance one space in the faith track
     * @param discardingPlayer player who discarded the marble
     */
    @Override
    public void advanceAfterDiscard(Player discardingPlayer) {
        for (Player p : players){
            if(!(p.getNickname().equals(discardingPlayer.getNickname()))){
                p.moveForwardNPositions(1);
            }
        }
    }



}
