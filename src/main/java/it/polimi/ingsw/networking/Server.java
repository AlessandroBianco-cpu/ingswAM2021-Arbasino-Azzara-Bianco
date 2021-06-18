package it.polimi.ingsw.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    ServerSocket serverSocket;
    private final int SIZE = 10; // is the No. of lobbies supported
    private final int PORT = 12345;
    private WaitingRoom waitingRoom;

    /**
     * Manages clients' reception and creates new clientHandlers
     */
    public void run() {
            try {
                serverSocket = new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("SERVER SOCKET CLOSED!");
            }

            waitingRoom = new WaitingRoom(SIZE);
            while (true) {
                try {
                    // Create socket
                    System.out.println("[SERVER] Waiting for client connection...");
                    Socket newSocket = serverSocket.accept();
                    System.out.println("[SERVER] Client connected to server");
                    ClientHandler clientHandler = new ClientHandler(newSocket);
                    clientHandler.run();
                    //the waitingRoom handles new client
                    waitingRoom.addClientToList(clientHandler);

                } catch (IOException e) {
                    System.out.println("[SERVER] Restarting server...");
                    return;
                }
            }
    }


}
