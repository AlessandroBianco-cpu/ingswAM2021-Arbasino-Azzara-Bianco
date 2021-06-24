package it.polimi.ingsw.networking.socketGame;

import it.polimi.ingsw.networking.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static it.polimi.ingsw.utils.StaticUtils.DEFAULT_SIZE;

/**
 * This class implements the Server Socket used to establish connections with clients
 */
public class Server implements Runnable {

    private ServerSocket serverSocket;
    private final int port;
    private final WaitingRoom waitingRoom;

    public Server(int port) {
        this.port = port;
        this.waitingRoom = new WaitingRoom(DEFAULT_SIZE);
    }

    /**
     * Manages clients' reception and creates new clientHandlers
     */
    public void run() {
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("SERVER SOCKET CLOSED!");
            }

            while (true) {
                try {
                    // Create socket
                    System.out.println("[SERVER] Waiting for client connection...");
                    Socket newSocket = serverSocket.accept();
                    System.out.println("[SERVER] Client connected to server");
                    ClientHandler socketClientHandler = new SocketClientHandler(newSocket);
                    socketClientHandler.run();
                    //the waitingRoom handles new client
                    waitingRoom.addClientToList(socketClientHandler);

                } catch (IOException e) {
                    System.out.println("[SERVER] Restarting server...");
                    return;
                }
            }
    }


}
