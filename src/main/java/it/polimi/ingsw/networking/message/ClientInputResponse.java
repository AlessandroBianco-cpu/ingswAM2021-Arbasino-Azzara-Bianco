package it.polimi.ingsw.networking.message;

/**
 * Packet used to send the player a response to one of his commands
 */
public class ClientInputResponse extends Server2Client{

    private final String errorMessage;

    public ClientInputResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
