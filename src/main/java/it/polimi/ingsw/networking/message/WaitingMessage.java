package it.polimi.ingsw.networking.message;

/**
 * Packet used to notify the client that he has to wait the completion of an opponent's action .
 */
public class WaitingMessage extends Server2Client{
    private static final long serialVersionUID = -8052457110874505491L;

    private final String message;

    public WaitingMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
