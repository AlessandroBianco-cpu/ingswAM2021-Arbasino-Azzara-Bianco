package it.polimi.ingsw.networking.message;

public class PlayerIsRejoiningMessage extends Server2Client {
    private static final long serialVersionUID = -6866986254919977634L;

    private String message;

    public PlayerIsRejoiningMessage(String message) { this.message = message; }

    public String getMessage() {
        return message;
    }
}
