package it.polimi.ingsw.networking.message.updateMessage;

import it.polimi.ingsw.networking.message.Broadcast;

/**
 * Packet used to update the status of the Strongbox of the player whose nickname corresponds to the one in the message.
 */
public class StrongboxUpdateMessage extends Broadcast {
    private static final long serialVersionUID = -4108888313766422964L;

    private final String nickname;
    private final int coins;
    private final int servants;
    private final int shields;
    private final int stones;

    public StrongboxUpdateMessage(String nickname, int coins, int servants, int shields, int stones) {
        this.nickname = nickname;
        this.coins = coins;
        this.servants = servants;
        this.shields = shields;
        this.stones = stones;
    }

    public String getNickname() {
        return nickname;
    }

    public int getCoins() {
        return coins;
    }

    public int getServants() {
        return servants;
    }

    public int getShields() {
        return shields;
    }

    public int getStones() {
        return stones;
    }
}
