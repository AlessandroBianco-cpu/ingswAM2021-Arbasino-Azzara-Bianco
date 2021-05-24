package it.polimi.ingsw;
import it.polimi.ingsw.networking.Server;

public class ServerApp {

    public static void main(String[] args) {
        Server server;

        server = new Server();
        server.run();

    }

}
