package it.polimi.ingsw.networking.message;

/**
 * Packet used to tell the client that it's not his turn.
 * It is usually sent in response of client's message when it's not his turn
 */
public class WrongTurnMessage extends Server2Client{

    private static final long serialVersionUID = 4080619396482490788L;
    private final String message = "Wrong turn!";

    public String getMessage() {
        return message;
    }
}
