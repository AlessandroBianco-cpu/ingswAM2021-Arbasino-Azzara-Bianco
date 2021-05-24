package it.polimi.ingsw.networking.message;

import java.util.ArrayList;
import java.util.List;

public class ChooseLeaderMessage extends Server2Client{
    private static final long serialVersionUID = -3022333894395349034L;

    private List<Integer> discardingIndexes = new ArrayList<>();

    public ChooseLeaderMessage(List<Integer> discardingIndexes) {
        this.discardingIndexes = discardingIndexes;
    }

    public List<Integer> getDiscardingIndexes() {
        return discardingIndexes;
    }
}
