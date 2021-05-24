package it.polimi.ingsw.networking.message;

public class PingMessage extends Message{

    private static final long serialVersionUID = -7272780555951521919L;

    private final String message = "";

    public String getMessage() {
        return message;
    }
}
