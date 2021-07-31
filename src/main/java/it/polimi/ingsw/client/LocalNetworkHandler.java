package it.polimi.ingsw.client;

import it.polimi.ingsw.networking.localGame.LocalGameServer;
import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.networking.message.RemoveClientForErrors;
import it.polimi.ingsw.networking.message.WinnerMessage;
import it.polimi.ingsw.observer.NetworkHandler;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Network handler used in local SP game
 */
public class LocalNetworkHandler implements Runnable, NetworkHandler {

    private final ServerMessagesManager serverMessagesManager;
    private final View view;

    private final List<Object> C2SMessages;
    private final List<Object> S2CMessages;

    public LocalNetworkHandler(View view) {
        this.view = view;
        this.S2CMessages = Collections.synchronizedList(new LinkedList<>());
        this.C2SMessages = Collections.synchronizedList(new LinkedList<>());
        serverMessagesManager = new ServerMessagesManager(view);
    }

    /**
     * This method sends the method to the local server
     * @param message message to send
     */
    @Override
    public void updateMessage(Message message) throws IOException {
        synchronized (C2SMessages){
            C2SMessages.add(message);
            C2SMessages.notifyAll();
        }
    }

    /**
     * This method is used to set that the client is ready to connect to the server
     */
    @Override
    public void updateConnection(String ip, String port) {
        System.out.println("[LOCAL HANDLER]: Local connection established");
    }


    /**
     * Closes the connection between client and sever. It deletes the elements of the lists used by
     * LocalNetworkHandler and LocalClientHandler to exchange messages
     */
    @Override
    public void closeConnection() {
        synchronized (C2SMessages){
            C2SMessages.clear();
            C2SMessages.notifyAll();
        }
        synchronized (S2CMessages){
            S2CMessages.clear();
            S2CMessages.notifyAll();
        }
        System.out.println("[LOCAL HANDLER]: Local connection closed.");
    }

    private void createLocalGame(){
        try{
            System.out.println("[LOCAL HANDLER]: Creating a local game...");
            LocalGameServer localGameServer = new LocalGameServer(C2SMessages, S2CMessages);
            new Thread(localGameServer).start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void listenToServer(){
        try{
            while (true) {
                synchronized (S2CMessages){
                    if (S2CMessages.size() > 0){
                        Object inputObject = S2CMessages.get(0);
                        S2CMessages.remove(0);
                        serverMessagesManager.manageInputFromServer(inputObject);
                        if((inputObject instanceof WinnerMessage) || (inputObject instanceof RemoveClientForErrors))
                            break;
                    }
                    if (S2CMessages.isEmpty())
                        S2CMessages.wait();
                }
            }
        } catch(NoSuchElementException | InterruptedException e) {
            view.displayNetworkError();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void run() {
        createLocalGame();
        listenToServer();
    }
}
