package it.polimi.ingsw.networking.localGame;

import it.polimi.ingsw.networking.ClientHandler;
import it.polimi.ingsw.networking.Lobby;
import it.polimi.ingsw.networking.message.ClientAcceptedMessage;

import java.io.FileNotFoundException;
import java.util.List;

import static it.polimi.ingsw.utils.StaticUtils.DEFAULT_LOCAL_LOBBY_ID;
import static it.polimi.ingsw.utils.StaticUtils.LOCAL_GAME_USERNAME;

/**
 * Class used to create a "server" used in local games.
 * It creates a lobby and starts a SP game using a ClientHandler of LocalClientHandler type
 */
public class LocalGameServer implements Runnable{

    private final ClientHandler clientHandler;
    private final Lobby lobby;

    public LocalGameServer(List<Object> C2SMessages, List<Object>S2CMessages) throws FileNotFoundException {
        this.clientHandler = new LocalClientHandler(C2SMessages, S2CMessages);
        clientHandler.run();
        this.lobby = new Lobby(DEFAULT_LOCAL_LOBBY_ID);
    }

    /**
     * Sets the username of the player and starts a SP game
     */
    @Override
    public void run() {
        clientHandler.send(new ClientAcceptedMessage(LOCAL_GAME_USERNAME));
        clientHandler.setUserNickname(LOCAL_GAME_USERNAME);
        lobby.addClientAndStartLocalGame(clientHandler);
    }


}
