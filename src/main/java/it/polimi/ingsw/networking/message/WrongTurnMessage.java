package it.polimi.ingsw.networking.message;

public class WrongTurnMessage extends Server2Client{

    private static final long serialVersionUID = 4080619396482490788L;
    private final String message = "Wrong turn!";

    public String getMessage() {
        return message;
    }
}
