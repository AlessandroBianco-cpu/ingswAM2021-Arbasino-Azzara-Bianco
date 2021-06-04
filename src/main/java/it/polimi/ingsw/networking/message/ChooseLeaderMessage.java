package it.polimi.ingsw.networking.message;

import java.util.List;

/**
 * Packet used to send to the server the will of the player to discard some leaderCards (initial stage of the game)
 */
public class ChooseLeaderMessage extends Server2Client{
    private static final long serialVersionUID = -3022333894395349034L;

    private final List<Integer> discardingIndexes;

    public ChooseLeaderMessage(List<Integer> discardingIndexes) {
        this.discardingIndexes = discardingIndexes;
    }

    public List<Integer> getDiscardingIndexes() {
        return discardingIndexes;
    }
}
