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

public class LocalNetworkHandler implements Runnable, NetworkHandler {

    private final ServerMessagesManager serverMessagesManager;
    private final View view;

    private final List<Object> C2SMessages;
    private final List<Object> S2CMessages;

    private volatile boolean ready;

    public LocalNetworkHandler(View view) {
        this.view = view;
        this.ready = false;
        this.S2CMessages = Collections.synchronizedList(new LinkedList<>());
        this.C2SMessages = Collections.synchronizedList(new LinkedList<>());
        serverMessagesManager = new ServerMessagesManager(view);
    }

    @Override
    public void updateMessage(Message message) throws IOException {
        synchronized (C2SMessages){
            C2SMessages.add(message);
        }
    }

    @Override
    public void updateConnection(String ip, String port) {
        ready = true;
        System.out.println("[LOCAL HANDLER]: Local connection established");
    }


    @Override
    public void closeConnection() {

        synchronized (C2SMessages){
            C2SMessages.clear();
        }
        synchronized (S2CMessages){
            S2CMessages.clear();
        }
        System.out.println("[LOCAL HANDLER]: Local connection closed.");
    }

    @Override
    public void run() {
        synchronized (this) {
            while (!ready) {
            }
        }

        try{
            System.out.println("[LOCAL HANDLER]: Creating a local game...");
            LocalGameServer localGameServer = new LocalGameServer(C2SMessages, S2CMessages);
            new Thread(localGameServer).start();
        } catch (IOException e){
            e.printStackTrace();
        }

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
                }
            }
        } catch(NoSuchElementException e) {
            view.displayNetworkError();
        } finally {
            closeConnection();
        }
    }
}
