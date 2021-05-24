package it.polimi.ingsw.networking;

import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.networking.message.PingMessage;
import it.polimi.ingsw.observer.ConnectionObservable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static it.polimi.ingsw.utils.StringToPrint.*;

/**
 * Handles network connection on the Server side
 */
public class ClientHandler extends ConnectionObservable {

    private Socket socket;
    private boolean connected;
    private boolean active;
    private boolean myTurn;
    private Message answer;
    private PingMessage pingMessage;
    private boolean answerReady;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;


    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.connected = true;
        this.active = true;
        this.myTurn = false;
        this.answerReady = false;
        this.pingMessage = new PingMessage();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isConnected() {
        return connected;
    }

    public void send(Object object){

        if (object instanceof String) {
            String s = ((String) object);
            switch (s) {
                case startTurnMessage:
                    myTurn = true;
                    return;
                case endTurnMessage:
                    myTurn = false;
                    return;
            }
        }
            try {
                socketOut.reset();
                socketOut.writeObject(object);
                socketOut.flush();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
    }


    /**
     * Closes connection with client
     */
    public synchronized void closeConnection() {
        try {
            connected = false;
            socket.close();
            System.out.println("[SERVER] Connection closed with client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a ping message from Server to Client
     */
    public void pingToClient() {
        Thread t = new Thread(() -> {
            while (connected) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    socketOut.writeObject(pingMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * Loops read from client: when a message is read, answerReady is set true. If the client is unreachable, server is notified
     */
    public void readFromClient() {
        Thread t = new Thread(() -> {
            while (connected) {
                try {
                    socket.setSoTimeout(500000);
                    Message fromClient = null;
                    try {
                        fromClient = (Message) socketIn.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if( !(fromClient instanceof PingMessage) ) {
                        if (myTurn) {
                            System.out.println("[SERVER] I read a new message from current player");
                            answer = fromClient;
                            answerReady = true;
                            synchronized (this) {
                                notifyAll();
                            }
                        } else {
                            System.out.println("[SERVER] I send a wrongTurn message");
                            send(wrongTurnMessage);
                        }
                    }
                } catch (IOException | NullPointerException | IllegalArgumentException e) {
                    System.out.println("[SERVER] Client unreachable");
                    e.printStackTrace();
                    notifyDisconnection(this);
                    break;
                }
            }
        });
        t.start();
    }

    /**
     * Returns client message: waits until a message is received
     * @return
     */
    public Message read() {
        synchronized (this) {
            while (!answerReady) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        answerReady = false;
        return answer;
    }


    /**
     * Initializes socket and starts ping management
     */
    public void run() {

        try {
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());
            System.out.println("[SERVER] New ClientSockets created");
        } catch (IOException e) {
            e.printStackTrace();
        }
        readFromClient();
        pingToClient();
    }


}
