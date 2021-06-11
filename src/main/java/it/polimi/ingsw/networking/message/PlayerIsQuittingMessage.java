package it.polimi.ingsw.networking.message;

public class PlayerIsQuittingMessage extends Server2Client {

    private static final long serialVersionUID = -8697060567894646802L;

    private String message;

    public PlayerIsQuittingMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
