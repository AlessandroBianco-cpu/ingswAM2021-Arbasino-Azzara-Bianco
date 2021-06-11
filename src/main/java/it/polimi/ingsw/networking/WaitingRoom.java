package it.polimi.ingsw.networking;

import it.polimi.ingsw.networking.message.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WaitingRoom implements EndGameObserver {
    private int lobbySerial = 0;
    private boolean joinCurrent;
    private List<Lobby> lobbyList;
    private Map<String, Integer> nicknamesDB = new ConcurrentHashMap<>();
    private final Object lock;

    public WaitingRoom() {
        lock = new Object();
        lobbyList = new ArrayList<>();
    }

    /**
     * Hnaldes the client login phase, check if new client is trying to registering with the same nickname of an already registered client
     * @param clientToLogIn is the client handler to handle
     * @return the
     */
    public String loginUser(ClientHandler clientToLogIn) {

        clientToLogIn.setMyTurn(true);
        clientToLogIn.send(new SetNicknameMessage());
        Message nicknameMessage = clientToLogIn.read();

        //check-up this branch
        while (! (nicknameMessage instanceof LoginSettingMessage)) {
            clientToLogIn.send(new ClientInputResponse("Remember you must set the nickname"));
            System.out.println("[SERVER] wrong message sent from client");
            nicknameMessage = clientToLogIn.read();
        }

        String nickname = ((LoginSettingMessage) nicknameMessage).getNickname();
        joinCurrent = ((LoginSettingMessage) nicknameMessage).wantToJoin();

        while ( (nicknamesDB.containsKey(nickname) && !joinCurrent) || (nicknamesDB.containsKey(nickname) && getLobbyByID(nicknamesDB.get(nickname)).checkActivePlayerInLobby(nickname)) ) {
            clientToLogIn.send(new TakenNameMessage());
            nicknameMessage = clientToLogIn.read();
            nickname = ((LoginSettingMessage) nicknameMessage).getNickname();
            joinCurrent = ((LoginSettingMessage) nicknameMessage).wantToJoin();
        }

        System.out.println("[SERVER] " + nickname + " registered!");
        clientToLogIn.send(new ClientAcceptedMessage(nickname));
        clientToLogIn.setMyTurn(false);

        return nickname;
    }

    /**
     * Adds
     * @param client current client
     */
    public void addClientToList(ClientHandler client) {
        client.send(new WaitingMessage("Server's adding you on waiting room..."));
        synchronized (lock) {
            String selectedNickname = loginUser(client);
            client.setUserNickname(selectedNickname);
            lock.notifyAll();
        }
        //aggiungere una costante final
        if (!joinCurrent && lobbyList.size() < 5) {
            createNewLobby(client);
        } else {
            manageJoiningInLobby(client);
        }
    }

    private void addInNotReadyLobby(ClientHandler ch) {
        for(Lobby l : lobbyList) {
            if (!l.isLobbyIsReady()) {
                nicknamesDB.put(ch.getUserNickname(),l.getLobbyID());
                l.addClient(ch);
                return;
            }
        }
        if(lobbyList.size() < 5)
            createNewLobby(ch);
        else
            ch.send(new RemoveClientForErrors("This server is full of players, please retry to enter later..."));
    }

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
            ClientHandler ch = newClient;
            lobbySerial++;
            Lobby lobby = new Lobby(lobbySerial, this);
            lobbyList.add(lobby);
            nicknamesDB.put(ch.getUserNickname(),lobby.getLobbyID());
            lobby.addClient(ch);
        });
        t.start();
    }

    @Override
    public void managePreGameDisconnection(ClientHandler ch) { nicknamesDB.remove(ch.getUserNickname()); }

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
