package it.polimi.ingsw.networking.message;

/**
 * Packet sent from Server to client to tell the players the name of the winner
 */
public class WinnerMessage extends Broadcast{
    private static final long serialVersionUID = -8705473146703255833L;

    private final String winner;

    public WinnerMessage(String winner) {
        this.winner = winner;
    }

    public String getMessage() {
        return winner;
    }
}
