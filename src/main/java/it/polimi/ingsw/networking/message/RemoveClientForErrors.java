package it.polimi.ingsw.networking.message;

public class RemoveClientForErrors extends Server2Client{
    private static final long serialVersionUID = -5151418769491123378L;
    private String message;

    public RemoveClientForErrors(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
}
