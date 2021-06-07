package it.polimi.ingsw.networking.message;

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
