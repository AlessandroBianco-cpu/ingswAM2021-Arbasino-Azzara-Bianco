package it.polimi.ingsw.networking;

import it.polimi.ingsw.networking.message.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is used to manage the login of clients and the entering in a lobby
 */

public class WaitingRoom implements EndGameObserver {
    private final int MAX_SIZE;
    private int lobbySerial = 0;
    private boolean joinCurrent;
    private List<Lobby> lobbyList;
    private Map<String, Integer> nicknamesDB = new ConcurrentHashMap<>();


    public WaitingRoom(int size) {
        MAX_SIZE = size;
        lobbyList = new ArrayList<>();
    }

    /**
     * Handles the client login phase, check if new client is trying to registering with the same nickname of an already registered client
     * @param clientToLogIn is the client handler to handle
     */
    public void loginUser(ClientHandler clientToLogIn) {
        clientToLogIn.setMyTurn(true);
        clientToLogIn.send(new SetNicknameMessage());
        Message nicknameMessage = clientToLogIn.read();

        if(nicknameMessage == null)
            return;

        String nickname = ((LoginSettingMessage) nicknameMessage).getNickname();
        joinCurrent = ((LoginSettingMessage) nicknameMessage).wantToJoin();

        while ((( nicknamesDB.containsKey(nickname) && !joinCurrent ) || ( nicknamesDB.containsKey(nickname) && getLobbyByID(nicknamesDB.get(nickname)).checkActivePlayerInLobby(nickname)))) {
            clientToLogIn.send(new TakenNameMessage());
            nicknameMessage = clientToLogIn.read();
            if(nicknameMessage == null)
                return;
            nickname = ((LoginSettingMessage) nicknameMessage).getNickname();
            joinCurrent = ((LoginSettingMessage) nicknameMessage).wantToJoin();
        }

        clientToLogIn.setUserNickname(nickname);
        System.out.println("[SERVER] " + nickname + " registered!");
        System.out.println();
        clientToLogIn.send(new ClientAcceptedMessage(nickname));
        clientToLogIn.setMyTurn(false);

    }

    /**
     * Adds
     * @param client current client
     */
    public void addClientToList(ClientHandler client) {
        loginUser(client);
        String selectedNickname = client.getUserNickname();

        if(selectedNickname != null) {
            if (!joinCurrent && lobbyList.size() < MAX_SIZE)
                createNewLobby(client);
             else
                manageJoiningInLobby(client);

        }
    }

    /**
     * Add a client in a not ready lobby, if there isn't notReady lobby, crate a new one with this client
     * @param ch is the clientHandler to handle
     */
    private void addInNotReadyLobby(ClientHandler ch) {
        for(Lobby l : lobbyList) {
            if (!l.isLobbyIsReady()) {
                nicknamesDB.put(ch.getUserNickname(),l.getLobbyID());
                l.addClient(ch);
                return;
            }
        }
        if(lobbyList.size() < MAX_SIZE)
            createNewLobby(ch);
        else
            ch.send(new RemoveClientForErrors("This server is full of players, please retry to enter later..."));
    }

    /**
     * This method get the lobby from the waitingRoom's lobbyList
     * @param ID is the ID of the lobby we want to get
     * @return the lobby
     */
    private Lobby getLobbyByID (Integer ID) {
        for (Lobby lobby : lobbyList)
            if (lobby.getLobbyID() == ID)
                return lobby;
        return null;
    }

    /**
     * Manage a joining in Lobby on a new Thread
     * @param newClient client is the client to add in lobby
     */
    private void manageJoiningInLobby(ClientHandler newClient) {
        Thread t = new Thread(() -> {
            if (nicknamesDB.containsKey(newClient.getUserNickname()) && getLobbyByID(nicknamesDB.get(newClient.getUserNickname())) != null) {
                getLobbyByID(nicknamesDB.get(newClient.getUserNickname())).rejoinPlayer(newClient);
            } else
                addInNotReadyLobby(newClient);
        });
        t.start();
    }

    /**
     * Starts a new lobby on a new thread
     * @param newClient is the client to add in lobby
     */
    private void createNewLobby(ClientHandler newClient) {
        Thread t = new Thread(() -> {
            lobbySerial++;
            Lobby lobby = new Lobby(lobbySerial, this);
            lobbyList.add(lobby);
            nicknamesDB.put(newClient.getUserNickname(),lobby.getLobbyID());
            lobby.addClient(newClient);
        });
        t.start();
    }

    /**
     * Clean the nicknamesDB if a client disconnected and lobby isn't ready
     * @param ch is the clientHandler of the disconnected player
     */
    @Override
    public synchronized void managePreGameDisconnection(ClientHandler ch) {
        nicknamesDB.remove(ch.getUserNickname()); }

    /**
     * Remove all saved nicknames with the closing ID lobby associate
     * @param lobbyID is the ID of the lobby to remove from lobby list
     */
    private synchronized void refreshUsers(int lobbyID) {
            for (String s : nicknamesDB.keySet()) {
                if (nicknamesDB.get(s) == lobbyID) {
                    nicknamesDB.remove(s);
                }
            }
        System.out.println("[WAIT_ROOM] Nicknames database updated");
    }

    /**
     * This method handles the end of a game, close the relative lobby and refresh the Server's nicknameDB
     * @param lobbyID is the ID of lobby to remove from lobbyList
     */
    @Override
    public void manageEndGame(int lobbyID) {
        getLobbyByID(lobbyID).setGameEnded(true);
        refreshUsers(lobbyID);
        System.out.println("[WAIT_ROOM] Removing lobby #"+lobbyID+"...");
        lobbyList.remove(getLobbyByID(lobbyID));
        System.out.println("[WAIT_ROOM] Lobby removed");
        System.out.println("================================================");
        System.out.println();
    }

}
