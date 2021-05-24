package it.polimi.ingsw.networking.message;

import java.io.Serializable;

public class WrongTurnMessage extends Server2Client implements Serializable {

    private static final long serialVersionUID = 4080619396482490788L;
    private String message = "Wrong turn!";

    public String getMessage() {
        return message;
    }
}
