package it.polimi.ingsw.networking;

import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.networking.message.PingMessage;
import it.polimi.ingsw.networking.message.WrongTurnMessage;
import it.polimi.ingsw.observer.ConnectionObservable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Handles network connection on the Server side
 */
public class ClientHandler extends ConnectionObservable {

    private final Socket socket;
    private String userNickname;
    private volatile boolean connected;
    private Thread readingThread;
    private boolean myTurn;
    private Message answer;
    private PingMessage pingMessage;
    private boolean answerReady;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.connected = true;
        this.myTurn = false;
        this.answerReady = false;
        this.pingMessage = new PingMessage();
    }

    public void setMyTurn (boolean myTurn) {
        this.myTurn = myTurn;
    }

    public boolean isConnected() {
        return connected;
    }

    public void send(Object object){
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
        connected = false;
        try {
            socketOut.close();
            socketIn.close();
            socket.close();
            //readingThread.interrupt();
            System.out.println("[SERVER] "+userNickname+"-socket connection closed server-side");
            System.out.println();
        } catch (IOException e) {
            System.out.println("[SERVER] ERRORE IN CHIUSURA DEL CLIENT: " +userNickname);
            e.printStackTrace();
            System.out.println();
            System.out.println();
        }
    }

    /**
     * Sends a ping message from Server to Client
     */
    public void pingToClient() {
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
                    if(counter > 100) {
                        counter = 0;
                        socketOut.writeObject(pingMessage);
                    }
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
        readingThread = new Thread(() -> {
            while (connected) {
                try {
                    socket.setSoTimeout(30000);
                    Message fromClient = null;
                    try {
                        fromClient = (Message) socketIn.readObject();
                    } catch (ClassNotFoundException e) {
                        System.out.println("[SERVER] client killed");
                    }
                    if( !(fromClient instanceof PingMessage) ) {
                        if (myTurn) {
                            answer = fromClient;
                            answerReady = true;
                            synchronized (this) {
                                notifyAll();
                            }
                        } else {
                            send(new WrongTurnMessage());
                        }
                    }
                } catch (IOException | NullPointerException | IllegalArgumentException e) {
                    System.out.println("[SERVER] "+userNickname+"-socket connection closed client-side");
                    closeConnection();
                    notifyDisconnection(this);
                    break;
                }
            }
        });
        readingThread.start();
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
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readFromClient();
        pingToClient();
    }

    public String getUserNickname() { return userNickname; }

    public void setUserNickname(String userNickname) { this.userNickname = userNickname; }
}

