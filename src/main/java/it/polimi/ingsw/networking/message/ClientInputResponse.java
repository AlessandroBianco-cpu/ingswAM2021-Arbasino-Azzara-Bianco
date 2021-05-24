package it.polimi.ingsw.networking.message;

public class ClientInputResponse extends Server2Client{

    private String errorMessage;
    private boolean Ok;

    public ClientInputResponse() {
        this.errorMessage = "WRONG INPUT!";
    }

    public ClientInputResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isOk() {
        return Ok;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
