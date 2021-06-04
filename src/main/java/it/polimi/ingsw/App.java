package it.polimi.ingsw;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.networking.Server;
import it.polimi.ingsw.view.CLIpackage.CLI;
import it.polimi.ingsw.view.GUIpackage.GUI;
import javafx.application.Application;

public class App {
    public static void main(String[] args) {

        if (args.length > 0) {
            if (args[0].equals("-cli")) {
                //start CLI ClientApp
                CLI cli = new CLI();

                NetworkHandler clientNetworkHandler = new NetworkHandler(cli);
                cli.addObserver(clientNetworkHandler);

                new Thread(clientNetworkHandler).start();
                new Thread(cli).start();
            }
            else if (args[0].equals("-server")){
                Server server;

                server = new Server();
                server.run();

            }
        } else //start GUI ClientApp
            Application.launch(GUI.class);
    }
}
