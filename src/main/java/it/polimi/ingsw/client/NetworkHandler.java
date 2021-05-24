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

import static it.polimi.ingsw.utils.StringToPrint.*;

public class NetworkHandler implements Runnable, UiObserver {
    Socket socket;
    ObjectInputStream socketIn;
    ObjectOutputStream socketOut;
    private final View view;
    private boolean ready = false;

    public NetworkHandler(View view) {
        this.view = view;
    }


    /**
     * Server messages' manager
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

        else if(inputObject instanceof MarbleBufferUpdateMessage)
            view.updateBuffer((MarbleBufferUpdateMessage) inputObject);

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
            view.displayStringMessages("Remember you must choose " +m.getNumOfRes()+ " resources. Type \"initialRes\"");
        }
        else if (inputObject instanceof LeaderInHandUpdateMessage)
            view.updateLeaderCardsInHand((LeaderInHandUpdateMessage) inputObject);

        else if(inputObject instanceof ClientInputResponse)
            view.displayStringMessages( ((ClientInputResponse) inputObject).getErrorMessage());

        else if (inputObject instanceof WinnerMessage)
            view.displayWinner( ((WinnerMessage) inputObject).getMessage());

        else if (inputObject instanceof Integer) {
            int num = ((Integer) inputObject);
            view.updatePlayersNumber(num);
        }

        else if (inputObject instanceof String) {
            if (! (inputObject.equals("")) ) {
                String stringMessage = (String) inputObject;
                switch (stringMessage) {
                    case wrongTurnMessage:
                        view.displayWrongTurn();
                        break;
                    case setPlayersNumMessage:
                        view.displayStringMessages("Remember you must set the players number. Type \"setting\" ");
                        break;
                    case setNicknameMessage:
                        view.displayStringMessages("Remember you must set your nickname. Type \"nick\"");
                        break;
                    case setLeadersMessage:
                        view.displayStringMessages("Remember you must discard two of your leaders. Type \"initialDis\"");
                        break;
                    case takenNameMessage:
                        view.displayTakenNickname();
                        break;
                    case waitPlayersMessage:
                        view.displayStringMessages(waitOtherPlayers);
                        break;
                    case waitMessage:
                        view.waiting();
                        break;
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }


    /**
     * Sends a ping message from Client to Server
     */
    public void pingToServer() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    updateMessage(new PingMessage());
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
                    socket.setSoTimeout(500000);
                    Object inputObject = socketIn.readObject();
                    manageInputFromServer(inputObject);
                    // close socket if winMessage is received
                    if (inputObject instanceof WinnerMessage)
                        break;
                } catch (IOException | ClassNotFoundException e) {
                    view.displayNetworkError();
                    break;
                }
            }
        } catch(NoSuchElementException e) {
            view.displayNetworkError();
        } finally {
            try {
                socketIn.close();
                socketOut.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Send a client2server message to the server
     * @param message message prepared by CLI
     */
    @Override
    public void updateMessage(Message message) throws IOException {
        try {
            //TODO vedere se senza il reset il server gira meglio
            //socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
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

