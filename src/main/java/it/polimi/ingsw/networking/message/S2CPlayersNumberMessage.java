package it.polimi.ingsw.networking.message;

/**
 * Packet used to send the clients the number of the player of the game
 */
public class S2CPlayersNumberMessage extends Server2Client{
    private static final long serialVersionUID = 5123918677093365071L;
    private final int num;

    public S2CPlayersNumberMessage(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
