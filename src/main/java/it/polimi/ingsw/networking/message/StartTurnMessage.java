package it.polimi.ingsw.networking.message;

/**
 * Packet sent from players to the players in order to tell them whose player the turn is
 */
public class StartTurnMessage extends Server2Client{
    private static final long serialVersionUID = 3557258901834940946L;

    private final String currentPlayerNickname;

    public StartTurnMessage(String currentPlayerNickname) {
        this.currentPlayerNickname = currentPlayerNickname;
    }

    public String getMessage(){
        return "It's " + currentPlayerNickname + "'s turn";
    }

    public String getCurrentPlayerNickname() {
        return currentPlayerNickname;
    }
}
