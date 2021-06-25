package it.polimi.ingsw.client;

import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.networking.message.PingMessage;
import it.polimi.ingsw.networking.message.RemoveClientForErrors;
import it.polimi.ingsw.networking.message.WinnerMessage;
import it.polimi.ingsw.observer.NetworkHandler;
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
public class SocketNetworkHandler implements Runnable, NetworkHandler {
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private final View view;
    private volatile boolean connected;
    private volatile boolean ready;
    private final ServerMessagesManager serverMessagesManager;

    public SocketNetworkHandler(View view) {
        this.view = view;
        this.serverMessagesManager = new ServerMessagesManager(view);
        connected = false;
        ready = false;
    }

    /**
     * Sends a ping message from Client to Server
     */
    private void pingToServer() {
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
                    serverMessagesManager.manageInputFromServer(inputObject);
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

            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Closes connection of the client
     */
    @Override
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

