package it.polimi.ingsw.client;

import it.polimi.ingsw.networking.message.*;
import it.polimi.ingsw.networking.message.updateMessage.*;
import it.polimi.ingsw.observer.UiObserver;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

/**
 * Class used to implement the network communication between Client-Server. It is stored client-side
 * It is charged to establish the connection with the server, read messages from the server and send messages to the server
 */
public class NetworkHandler implements Runnable, UiObserver {
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private final View view;
    private boolean connected = false;
    private boolean ready = false;

    public NetworkHandler(View view) {
        this.view = view;
    }

    /**
     * Manages all types of messages sent from Server
     * @param inputObject message from server
     */
    private void manageInputFromServer(Object inputObject) {

        if(inputObject instanceof PingMessage){
            return;
        }

        if (inputObject instanceof ClientAcceptedMessage)
            view.registerClient((ClientAcceptedMessage) inputObject);

        else if(inputObject instanceof StartTurnMessage)
            view.displayStartTurn((StartTurnMessage) inputObject);

        else if(inputObject instanceof PlayerIsQuittingMessage)
            view.displayPlayersNumChange(((PlayerIsQuittingMessage) inputObject).getMessage(),false);

        else if(inputObject instanceof PlayerIsRejoiningMessage)
            view.displayPlayersNumChange(((PlayerIsRejoiningMessage) inputObject).getMessage(),true);

        else if(inputObject instanceof WaitingMessage)
            view.waitingOtherPlayers(((WaitingMessage) inputObject).getMessage());

        else if(inputObject instanceof MarbleBufferUpdateMessage)
            view.updateBuffer((MarbleBufferUpdateMessage) inputObject);

        else if (inputObject instanceof CardPaymentResourceBufferUpdateMessage)
            view.updateDevCardResourcesToPay((CardPaymentResourceBufferUpdateMessage) inputObject);

        else if (inputObject instanceof PlacementDevCardMessage)
            view.updatePlaceNewCard((PlacementDevCardMessage) inputObject);

        else if (inputObject instanceof ProductionResourceBufferUpdateMessage)
            view.updateProductionResourcesToPay((ProductionResourceBufferUpdateMessage) inputObject);

        else if(inputObject instanceof MarketUpdateMessage)
            view.updateMarketLight((MarketUpdateMessage) inputObject);

        else if (inputObject instanceof StrongboxUpdateMessage)
            view.updateStrongboxLight((StrongboxUpdateMessage) inputObject);

        else if (inputObject instanceof FaithTrackUpdateMessage)
            view.updateFaithTrack((FaithTrackUpdateMessage) inputObject);

        else if (inputObject instanceof WarehouseUpdateMessage)
            view.updateWarehouseLight((WarehouseUpdateMessage) inputObject);

        else if (inputObject instanceof LorenzoUpdateMessage)
            view.updateLorenzoLight((LorenzoUpdateMessage) inputObject);

        else if (inputObject instanceof ProductionZoneUpdateMessage)
            view.updateProductionZone((ProductionZoneUpdateMessage) inputObject);

        else if (inputObject instanceof DevCardMarketUpdateMessage)
            view.updateDevCardMarket((DevCardMarketUpdateMessage) inputObject);

        else if (inputObject instanceof NicknamesUpdateMessage)
            view.updateNicknames((NicknamesUpdateMessage) inputObject);

        else if(inputObject instanceof ChooseResourcesMessage) {
            ChooseResourcesMessage m = (ChooseResourcesMessage) inputObject;
            view.updateNumOfResourcesToAdd(m.getNumOfRes());
        }
        else if (inputObject instanceof LeaderInHandUpdateMessage)
            view.updateLeaderCardsInHand((LeaderInHandUpdateMessage) inputObject);

        else if (inputObject instanceof OpponentsLeaderCardsInHandUpdateMessage)
            view.updateOpponentsLeaderCardsInHand ((OpponentsLeaderCardsInHandUpdateMessage) inputObject);

        else if(inputObject instanceof ClientInputResponse)
            view.displayStringMessages( ((ClientInputResponse) inputObject).getErrorMessage());

        else if (inputObject instanceof WinnerMessage)
            view.displayWinner(((WinnerMessage) inputObject).getMessage());

        else if (inputObject instanceof WrongTurnMessage)
            view.displayWrongTurn();

        else if (inputObject instanceof GameStartedMessage)
            view.gameStarted();

        else if (inputObject instanceof SetPlayersNumMessage)
            view.askPlayersNumber();

        else if (inputObject instanceof SetNicknameMessage)
            view.askLogin();

        else if (inputObject instanceof SetLeadersMessage)
            view.askInitialDiscard();

        else if (inputObject instanceof FirstPlayerMessage)
            view.updateFirstPlayer((FirstPlayerMessage) inputObject);

        else if (inputObject instanceof TakenNameMessage)
            view.displayTakenNickname();

        else if (inputObject instanceof RemoveClientForErrors)
            view.quittingForProblem(((RemoveClientForErrors)inputObject).getMessage());

        else if (inputObject instanceof S2CPlayersNumberMessage){
            view.updatePlayersNumber(((S2CPlayersNumberMessage) inputObject).getNum());

        } else {
            throw new IllegalArgumentException();
        }
    }


    /**
     * Sends a ping message from Client to Server
     */
    public void pingToServer() {
        Thread t = new Thread(() -> {
            int counter = 0;
            while (connected) {
                try {
                    Thread.sleep(50);
                    counter++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (counter > 100) {
                        counter = 0;
                        updateMessage(new PingMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * Initializes socket and starts ping management
     */
    @Override
    public void run() {

        synchronized (this) {
            while (!ready) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        connected = true;
        try {
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        pingToServer();

        try{
            while (true) {
                try {
                    socket.setSoTimeout(30000);
                    Object inputObject = socketIn.readObject();
                    manageInputFromServer(inputObject);
                    // close socket if winMessage is received or server is full or a player left game in initial set-up phase
                    if((inputObject instanceof WinnerMessage) || (inputObject instanceof RemoveClientForErrors))
                        break;
                } catch (IOException | ClassNotFoundException e) {
                    view.displayNetworkError();
                    break;
                }
            }
        } catch(NoSuchElementException e) {
            view.displayNetworkError();
        } finally {
            closeConnection();
        }
    }


    /**
     * Send a client2server message to the server
     * @param message message prepared by CLI
     */
    @Override
    public void updateMessage(Message message) throws IOException {
        try {
            //vedere se senza il reset il server gira meglio
            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public synchronized void closeConnection() {
        connected = false;
        try {
            System.out.println("Closing client-side connection...");
            socketOut.close();
            socketIn.close();
            socket.close();
            System.out.println("Connection closed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries connecting to the server
     * @param ip server ip
     * @param port server port
     */
    @Override
    public void updateConnection(String ip, String port) {

        try {
            socket = new Socket(ip, Integer.parseInt(port));
            ready = true;
            synchronized (this) {
                notifyAll();
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Server unreachable");
            System.exit(0);
        }
        System.out.println("Connection established");
    }

}

