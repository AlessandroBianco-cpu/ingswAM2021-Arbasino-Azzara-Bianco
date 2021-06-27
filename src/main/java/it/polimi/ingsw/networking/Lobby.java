package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.MultiController;
import it.polimi.ingsw.controller.SingleController;
import it.polimi.ingsw.controller.UserInputManager;
import it.polimi.ingsw.model.GameMode.Game;
import it.polimi.ingsw.model.GameMode.MultiPlayerGame;
import it.polimi.ingsw.model.GameMode.SinglePlayerGame;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.networking.message.GameStartedMessage;
import it.polimi.ingsw.networking.message.S2CPlayersNumberMessage;
import it.polimi.ingsw.networking.message.WaitingMessage;
import it.polimi.ingsw.networking.message.updateMessage.FirstPlayerMessage;
import it.polimi.ingsw.networking.socketGame.EndGameObserver;
import it.polimi.ingsw.observer.ConnectionObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This method mage the setting of number of players and the setting up, then creates a controller to start the new game
 */
public class Lobby implements ConnectionObserver {

    private final VirtualView virtualView;
    private final UserInputManager userInputManager;
    private EndGameObserver endGameObserver;

    private final List<ClientHandler> clients;
    private final List<Player> gamePlayers;
    private Controller controller;
    private final Object lock;
    private final int ID;
    private int playersNumber;
    private boolean lobbyIsReady; //lobby have the right number of players
    private boolean lobbyIsSettingUp; //distribution of cards and initial setting phase

    public Lobby(int ID, EndGameObserver waitingRoom){
        virtualView = new VirtualView(ID);
        userInputManager = new UserInputManager();
        this.endGameObserver = waitingRoom;
        clients = new ArrayList<>();
        gamePlayers = new ArrayList<>();
        this.ID = ID;
        lock = new Object();
        playersNumber = -1;
        lobbyIsReady = false;
        lobbyIsSettingUp = false;

        virtualView.addObserver(userInputManager);
    }

    /**
     * This constructor is used for a local game lobby
     * @param ID is the serial ID of a local lobby
     */
    public Lobby(int ID) {
        virtualView = new VirtualView(ID);
        userInputManager = new UserInputManager();
        clients = new ArrayList<>();
        gamePlayers = new ArrayList<>();
        this.ID = ID;
        lock = new Object();
        lobbyIsSettingUp = false;

        virtualView.addObserver(userInputManager);
    }

    public int getLobbyID() {
        return ID;
    }

    public boolean isLobbyIsReady() { return lobbyIsReady; }

    /**
     * Removes the current client from the list of connected clients
     * @param client current client
     */
    public synchronized void deregisterConnection(ClientHandler client) {
        System.out.println("[SERVER] Unregistering client...");
        virtualView.removeClient(client.getUserNickname(),client);
        gamePlayers.remove(getPlayerByNickname(client.getUserNickname()));
        clients.remove(client);
        System.out.println("[SERVER] "+client.getUserNickname()+"'s client unregistered!");
        System.out.println();
    }

    /**
     * Search the game player by nickname
     */
    public Player getPlayerByNickname(String nickname) {
        for (Player p : gamePlayers) {
            if (p.getNickname().equals(nickname))
                return p;
        }
        return null;
    }

    /**
     * Calculate how many player are active
     * @return number of active player
     */
    private int numOfActivePlayers() {
        int count = 0;
        for (Player p : gamePlayers) {
            if (p.isActive())
                count++;
        }
        return count;
    }

    /**
     * Manages disconnection: handles the disconnection in base of game situation
     * @param client disconnected client
     */
    @Override
    public void updateDisconnection(ClientHandler client) {

        if (!controller.getGameEnded()) {
            if (lobbyIsSettingUp)
                updateDisconnectionInSettingPhase(client);
            else if (lobbyIsReady)
                updateDisconnectionInStartedGame(client);
                //if we are waiting for other player and someone disconnects
            else
                updateDisconnectionInWaitingRoom(client);
        }
        else {
            deregisterConnection(client);
            if (clients.size() == 0 || numOfActivePlayers() == 0)
                endGameObserver.manageEndGame(ID);
        }
    }

    /**
     * This method check for active player with the same nickname in this lobby
     * @param nickname is the string to check
     * @return true/false
     */
    public boolean checkActivePlayerInLobby(String nickname) {
        for (Player p : gamePlayers)
            if (p.getNickname().equals(nickname) && p.isActive())
                return true;

        return false;
    }

    /**
     * Clients addition manager: adds a client in the lobby list and if needed asks playersNumber
     * @param client client added
     */
    public void addClient(ClientHandler client) {

        synchronized (lock) {
            client.addObserver(this);
            clients.add(client);
            String nick = client.getUserNickname();
            virtualView.addClient(nick, client);
            gamePlayers.add(new Player(nick));

            if (clients.size() != playersNumber) {
                if (clients.size() == 1 && playersNumber == -1) {
                    setPlayersNumber(client);
                } else {
                    client.send(new WaitingMessage("Waiting for other players"));
                }
            }
            else {
                client.send(new S2CPlayersNumberMessage(playersNumber));
                client.send(new WaitingMessage("Waiting for other players"));
                lobbyIsReady = true;
            }
            lock.notifyAll();
        }

        if (lobbyIsReady)
            createNewGame();
    }

    public void addClientAndStartLocalGame(ClientHandler client){
        playersNumber = 1;
        lobbyIsReady = true;

        synchronized (lock) {
            client.addObserver(this);
            clients.add(client);
            String nick = client.getUserNickname();
            virtualView.addClient(nick, client);
            gamePlayers.add(new Player(nick));

            lock.notifyAll();
        }

        if (lobbyIsReady)
            createNewGame();
    }

    /**
     * Asks and sets the number of players to the first user connected
     * @param client current client
     */
    private void setPlayersNumber(ClientHandler client) {
        client.setMyTurn(true);
        virtualView.requestPlayersNum(client);
        playersNumber = userInputManager.getPlayersNumber();
        System.out.println("[LOBBY #"+ID+"] The game will have " + playersNumber + " players");
        System.out.println();

        if (playersNumber > 1) {
            client.send(new WaitingMessage("Waiting for other players"));
            client.setMyTurn(false);
        }
        else {
            lobbyIsReady = true;
        }
    }

    /**
     * Sets up game creating model, then notifies the server that will start the match
     *
     */
    private void createNewGame() {
        lobbyIsSettingUp = true;
        for (Player player : gamePlayers) {
            player.addObserver(virtualView);
        }
        Collections.shuffle(gamePlayers);
        System.out.println("[LOBBY #"+ID+"] " + gamePlayers.get(0).getNickname() + " is the first player of the match");
        System.out.println();

        if (gamePlayers.size() == 1)
            startSinglePlayerGame();
        else
            startMultiPlayerGame();
    }

    /**
     * Creates SingleGame and SingleController and starts a new match
     */
    private void startSinglePlayerGame() {

        System.out.println("[LOBBY #"+ID+"] Single player game settings phase");
        SinglePlayerGame game = new SinglePlayerGame();

        observerSettings(game);
        game.getLorenzoIlMagnifico().addObserver(virtualView);

        List<String> nicknames = new ArrayList<>();
        nicknames.add(gamePlayers.get(0).getNickname());
        virtualView.setUp(nicknames,game.getDevCardMarket(),game.getMarket()); //setting the modelLight
        controller = new SingleController(game,userInputManager,virtualView,gamePlayers.get(0));

        // distribution of leader cards
        controller.distributeLeaderCard();
        System.out.println("[LOBBY #"+ID+"] Leader cards chosen by the player");
        virtualView.sendToCurrentPlayer(new FirstPlayerMessage(gamePlayers.get(0).getNickname()));
        System.out.println("[LOBBY #"+ID+"] Single player game starts");
        virtualView.sendToCurrentPlayer(new GameStartedMessage());
        lobbyIsSettingUp = false;
        controller.play();
    }

    /**
     * Creates MultiGame and MultiController and starts a new match
     */
    private void startMultiPlayerGame() {

        System.out.println("[LOBBY #"+ID+"] Multiplayer game settings phase");
        Game game = new MultiPlayerGame();

        observerSettings(game);
        List<String> nicknames = new ArrayList<>();

        for(Player p : gamePlayers)
            nicknames.add(p.getNickname());

        virtualView.setUp(nicknames,game.getDevCardMarket(),game.getMarket()); //setting the modelLight
        controller = new MultiController(game, userInputManager, virtualView, gamePlayers);

        // distribution of leader cards
        controller.distributeLeaderCard();
        System.out.println("[LOBBY #"+ID+"] Leader cards chosen by all players");
        // distribution of initial resources
        controller.distributeInitialResource();
        System.out.println("[LOBBY #"+ID+"] Initial resources chosen by all players");
        virtualView.sendBroadcast(new FirstPlayerMessage(gamePlayers.get(0).getNickname()));
        virtualView.sendBroadcast(new GameStartedMessage());
        System.out.println("[LOBBY #"+ID+"] Set-Up completed, game starts");
        lobbyIsSettingUp = false;
        controller.play();
    }

    /**
     * Handle the disconnection of a player in an already started match
     * @param disconnectedClient is the clientHandler of the disconnected player
     */
    private void updateDisconnectionInStartedGame(ClientHandler disconnectedClient) {
        getPlayerByNickname(disconnectedClient.getUserNickname()).setActive(false);
        virtualView.sendToEveryoneExceptQuitPlayer(disconnectedClient.getUserNickname());
        //is a single player started match...manage it
        if(numOfActivePlayers() == 0) {
            deregisterConnection(disconnectedClient);
            endGameObserver.manageEndGame(ID);
        }
        //there is only one active player... he wins
        else if (numOfActivePlayers() == 1) {
            System.out.println("[LOBBY #"+ID+"] Closing lobby: only one active player in started game");
            controller.manageAEndGameForQuitting();
        }
        //advance turn in current game
        else if (virtualView.getCurrentPlayer().equals(disconnectedClient.getUserNickname()))
           controller.play();
    }

    /**
     * Handle the disconnection of a player in setUp phase
     * @param disconnectedClient is the clientHandler of the disconnected player
     */
    private void updateDisconnectionInSettingPhase(ClientHandler disconnectedClient) {
        System.out.println("[LOBBY #"+ID+"] Closing lobby: a client disconnects in set-up phase");
        System.out.println();
        deregisterConnection(disconnectedClient);
        if (playersNumber == 1) {
            endGameObserver.manageEndGame(ID);
        }
        else {
            controller.manageDisconnectionInSetUp(disconnectedClient.getUserNickname());
        }
    }

    /**
     * Handles the disconnection of a client in waiting room with other clients
     * @param disconnectedClient is the clientHandler of the disconnected player
     */
    private void updateDisconnectionInWaitingRoom(ClientHandler disconnectedClient) {
        System.out.println("[LOBBY #"+ID+"] Removing "+disconnectedClient.getUserNickname()+" from lobby...");
        System.out.println();
        deregisterConnection(disconnectedClient);
        if(clients.size() == 0) {
            endGameObserver.manageEndGame(ID);
        }
        else  {
            endGameObserver.managePreGameDisconnection(disconnectedClient);
        }
    }

    /**
     * This method is used to set the observer to the gamePlayers
     * @param game is the created instance of game
     */
    private void observerSettings(Game game) {
        for(Player p : gamePlayers)
            p.setGame(game);

        game.addPlayers(gamePlayers);
        List<Player> tmpPlayers = game.getPlayers();
        //adding PlayerItemsObservers
        for(Player p : tmpPlayers){
            p.getPersonalBoard().getStrongbox().addObserver(virtualView);
            p.getPersonalBoard().getFaithTrack().addObserver(virtualView);
            p.getPersonalBoard().getWarehouse().addObserver(virtualView);
            p.getPersonalBoard().addObserver(virtualView);
        }

        game.getMarket().addObserver(virtualView);
        game.getDevCardMarket().addObserver(virtualView);

    }

    /**
     * This method is used to reJoin a player that left
     * @param ch is the new client handler
     */
    public void rejoinPlayer(ClientHandler ch) {
        ch.send(new WaitingMessage("Server is joining you in the match..."));
        System.out.println("[SERVER] Rejoining a player in the match...");
        System.out.println();
        ch.addObserver(this);
        virtualView.addClient(ch.getUserNickname(),ch);
        virtualView.sendToEveryoneExceptRejoiningPlayer(ch);

        if (getPlayerByNickname(ch.getUserNickname()) != null) {
            getPlayerByNickname(ch.getUserNickname()).setActive(true);
            controller.manageRejoining(ch.getUserNickname());
        }
    }

}
