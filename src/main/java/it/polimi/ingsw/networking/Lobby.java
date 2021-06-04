package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.MultiController;
import it.polimi.ingsw.controller.SingleController;
import it.polimi.ingsw.controller.UserInputManager;
import it.polimi.ingsw.model.GameMode.Game;
import it.polimi.ingsw.model.GameMode.MultiPlayerGame;
import it.polimi.ingsw.model.GameMode.SinglePlayerGame;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.networking.message.ClientAcceptedMessage;
import it.polimi.ingsw.networking.message.GameStartedMessage;
import it.polimi.ingsw.networking.message.S2CPlayersNumberMessage;
import it.polimi.ingsw.networking.message.WaitingMessage;
import it.polimi.ingsw.observer.ConnectionObserver;
import it.polimi.ingsw.observer.LobbyObservable;

import java.util.*;

public class Lobby  extends LobbyObservable implements ConnectionObserver {

    private final VirtualView virtualView;
    private final UserInputManager userInputManager;

    private Map<String, ClientHandler> clientNames;
    private List<ClientHandler> clients;
    private List<String> playersNames;
    private List<Player> activePlayers;
    private final Object lock;
    private int playersNumber;
    private boolean ready;

    public Lobby() {
        virtualView = new VirtualView();
        userInputManager = new UserInputManager();
        clientNames = new HashMap<>();
        clients = new ArrayList<>();
        playersNames = new ArrayList<>();
        activePlayers = new ArrayList<>();
        lock = new Object();
        playersNumber = -1;
        ready = false;

        virtualView.addObserver(userInputManager);
    }

    /**
     * Removes the current client from the list of connected clients
     * @param client current client
     */
    public synchronized void deregisterConnection(ClientHandler client) {
        System.out.println("[SERVER] Unregistering client...");
        clients.remove(client);
        System.out.println("[SERVER] Client unregistered!");
    }

    /**
     * Manages disconnection: if the client disconnected is active, all the clients will be disconnected;
     * else the client disconnected is removed from the server's clients' list
     * @param client disconnected client
     */
    @Override
    public void updateDisconnection(ClientHandler client) {
        if (client.isActive()) {
            if (client.isConnected()) {
                if (ready) {
                    for (ClientHandler ch : clients) {
                        ch.closeConnection();
                    }
                } else {
                    client.closeConnection();
                    deregisterConnection(client);
                    if (playersNumber == -1) {
                        notifyPlayersNumber(this);
                    }
                }
            }
        }
        else {
            deregisterConnection(client);
        }
    }


    /**
     * Clients addition manager: adds a client in the lobby list and if needed asks playersNumber
     * @param client client added
     */
    public void addClient(ClientHandler client) {

            synchronized (lock) {
                client.addObserver(this);
                clients.add(client);

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
                    ready = true;
                }
                lock.notifyAll();
            }

            if (ready)
                setPlayersNickname();
    }

    /**
     * Asks and sets the number of players to the first user connected
     * @param client current client
     */
    private void setPlayersNumber(ClientHandler client) {
        client.setMyTurn(true);
        virtualView.requestPlayersNum(client);
        playersNumber = userInputManager.getPlayersNumber();
        System.out.println("[SERVER] The game will have " + playersNumber + " players");

        if (playersNumber > 1) {
            client.send(new WaitingMessage("Waiting for other players"));
            client.setMyTurn(false);
        }
        else {
            ready = true;
        }
    }

    /**
     * Asks nickname to the players, then starts the creation of the match
     */
    private void setPlayersNickname() {
        System.out.println("[SERVER] NickName setting phase");

        for (ClientHandler client : clients) {
            setNickname(client);
        }
        createNewGame();
    }

    /**
     * Asks and sets the current client's nickname
     * @param client current client
     */
    private void setNickname(ClientHandler client) {

        client.setMyTurn(true);
        virtualView.requestNickname(client);
        String nickname = userInputManager.getNickname();

        while (playersNames.contains(nickname)) {
            virtualView.errorTakenNickname(client);
            nickname = userInputManager.getNickname();
        }

        //add user to the list of connected users
        playersNames.add(nickname);
        clientNames.put(nickname, client);
        virtualView.addClient(nickname, client);
        activePlayers.add(new Player(nickname));

        System.out.println("[SERVER] " + nickname + " registered!");

        client.send(new ClientAcceptedMessage(nickname));
        client.setMyTurn(false);
    }


    /**
     * Sets up game creating model, then notifies the server that will start the match
     *
     */
    private void createNewGame() {

        for (Player player : activePlayers) {
            player.addObserver(virtualView);
        }
        Collections.shuffle(activePlayers);
        System.out.println("[SERVER] " + activePlayers.get(0).getNickname() + " is the first player of the match");
        if (activePlayers.size() == 1)
            startSinglePlayerGame(activePlayers.get(0));
        else
            startMultiPlayerGame(activePlayers);
    }


    /**
     * Creates SingleGame and SingleController and starts a new match
     * @param singlePlayer is the player of the singleGame
     */
    private void startSinglePlayerGame(Player singlePlayer){

        SinglePlayerGame game = new SinglePlayerGame();
        singlePlayer.setGame(game);
        game.addPlayers(activePlayers);

        List<Player> tmpPlayers = game.getPlayers();
        //adding PlayerItemsObservers
        for(Player p : tmpPlayers){
            p.getPersonalBoard().getStrongbox().addObserver(virtualView);
            p.getPersonalBoard().getFaithTrack().addObserver(virtualView);
            p.getPersonalBoard().getWarehouse().addObserver(virtualView);
            p.getPersonalBoard().addObserver(virtualView);
        }
        game.getLorenzoIlMagnifico().addObserver(virtualView);
        game.getMarket().addObserver(virtualView);
        game.getDevCardMarket().addObserver(virtualView);

        List<String> nicknames = new ArrayList<>();
        nicknames.add(singlePlayer.getNickname());
        virtualView.setUp(nicknames,game.getDevCardMarket(),game.getMarket()); //setting the modelLight
        SingleController controller = new SingleController(game,userInputManager,virtualView,singlePlayer);

        // distribution of leader cards
        controller.distributeLeaderCard();
        System.out.println("[SERVER] Leader cards chosen by the player");

        System.out.println("[SERVER] Single player game starts");
        virtualView.sendToCurrentPlayer(new GameStartedMessage());
        controller.play();
    }

    /**
     * Creates MultiGame and MultiController and starts a new match
     * @param sortedPlayers multiPlayerGame players list
     */
    private void startMultiPlayerGame(List<Player> sortedPlayers) {
        System.out.println("[SERVER] Multiplayer games settings phase");
        Game game = new MultiPlayerGame();

        for(Player p : activePlayers)
            p.setGame(game);

        game.addPlayers(sortedPlayers);

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

        List<String> nicknames = new ArrayList<>();
        for(Player p : sortedPlayers) {
            nicknames.add(p.getNickname());
        }

        virtualView.setUp(nicknames,game.getDevCardMarket(),game.getMarket()); //setting the modelLight
        MultiController controller = new MultiController(game, userInputManager, virtualView, sortedPlayers);

        // distribution of leader cards
        controller.distributeLeaderCard();
        System.out.println("[SERVER] Leader cards chosen by all players");
        // distribution of initial resources
        System.out.println("[SERVER] Initial resources chosen by all players");
        controller.distributeInitialResource();

        System.out.println("[SERVER] Set-Up completed, game starts");
        virtualView.sendBroadcast(new GameStartedMessage());
        controller.play();
    }

    public boolean isReady() {
        return ready;
    }

}
