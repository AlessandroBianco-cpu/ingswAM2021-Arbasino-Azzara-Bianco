package it.polimi.ingsw.networking.message;

public class StartTurnMessage extends Server2Client{

    private static final long serialVersionUID = 3557258901834940946L;

    private String currentPlayerNickname;

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
