package it.polimi.ingsw.networking.message;

public class WinnerMessage extends Server2Client{

    private static final long serialVersionUID = -8705473146703255833L;
    private String winner;

    public WinnerMessage(String winner) {
        this.winner = winner;
    }

    public String getMessage() {
        return "The winner is " + winner;
    }
}
