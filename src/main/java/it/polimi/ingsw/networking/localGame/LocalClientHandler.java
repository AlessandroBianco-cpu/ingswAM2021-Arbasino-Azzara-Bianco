package it.polimi.ingsw.networking.localGame;

import it.polimi.ingsw.networking.ClientHandler;
import it.polimi.ingsw.networking.message.Message;
import it.polimi.ingsw.networking.message.PingMessage;
import it.polimi.ingsw.networking.message.WrongTurnMessage;
import it.polimi.ingsw.observer.ConnectionObservable;

import java.util.List;

public class LocalClientHandler extends ConnectionObservable implements ClientHandler {

    private String userNickname;
    private volatile boolean connected;
    private volatile boolean myTurn;
    private volatile boolean answerReady;
    private Message answer;
    private final List<Object> C2SMessages;
    private final List<Object> S2CMessages;

    public LocalClientHandler(List<Object>C2SMessages,List<Object>S2CMessages) {
        this.C2SMessages = C2SMessages;
        this.S2CMessages = S2CMessages;
        this.connected = true;
        this.myTurn = false;
        this.answerReady = false;
    }

    public void setMyTurn (boolean myTurn) {
        this.myTurn = myTurn;
    }

    /**
     * This method is used to send message from server to a specific client
     * @param object is the serializable object to send
     */
    public void send(Object object){
        synchronized (S2CMessages){
            S2CMessages.add(object);
        }
    }

    /**
     * Closes connection with client
     */
    public void closeConnection() {
        //handle the case of a disconnection before the login
        if (userNickname == null) {
            synchronized (this) {
                answer = null;
                answerReady = true;
                notifyAll();
            }
        }
        connected = false;
        synchronized (C2SMessages){
            C2SMessages.clear();
        }
        synchronized (S2CMessages){
            S2CMessages.clear();
        }
        System.out.println("[SERVER] "+userNickname+"-socket connection closed server-side");
        System.out.println();
    }

    /**
     * Loops read from client: when a message is read, answerReady is set true. If the client is unreachable, server is notified
     */
    public void readFromClient() {
        Thread t = new Thread(() -> {
            while (connected) {
                try {
                    Message fromClient;
                    synchronized (C2SMessages){
                        if (C2SMessages.size() > 0){
                            fromClient = (Message) C2SMessages.get(0);
                            C2SMessages.remove(0);
                            if( !(fromClient instanceof PingMessage) ) {
                                if (myTurn) {
                                    answer = fromClient;
                                    answerReady = true;
                                } else {
                                    send(new WrongTurnMessage());
                                }
                            }
                        }
                    }
                } catch (NullPointerException | IllegalArgumentException e) {
                    System.out.println("[SERVER] "+userNickname+"-socket connection closed client-side");
                    closeConnection();
                    notifyDisconnection(this);
                    break;
                }
            }
        });
        t.start();
    }

    /**
     * Returns client message: waits until a message is received
     * @return message sent from client
     */
    public Message read() {

            while (!answerReady) {
                ;
            }
        answerReady = false;
        return answer;
    }

    /**
     * Initializes socket and starts ping management
     */
    public void run() {
        System.out.println("[SERVER] New ClientSocket created");
        readFromClient();
    }

    public String getUserNickname() { return userNickname; }

    public void setUserNickname(String userNickname) { this.userNickname = userNickname; }

}
