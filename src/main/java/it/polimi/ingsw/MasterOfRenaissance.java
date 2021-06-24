package it.polimi.ingsw;

import it.polimi.ingsw.networking.socketGame.Server;
import it.polimi.ingsw.view.CLIpackage.CLI;
import it.polimi.ingsw.view.GUIpackage.GUI;
import javafx.application.Application;

public class MasterOfRenaissance {
    public static void main(String[] args) {

        if (args.length > 0) {
            if (args[0].equals("-cli")) {
                //start CLI ClientApp
                CLI cli = new CLI();
                cli.run();
            }
            else if (args[0].equals("-server")) {
                //start ServerApp
                Server server;
                int port = Integer.parseInt(args[1]);
                server = new Server(port);
                server.run();
            }
        } else {
            //start GUI ClientApp
            Application.launch(GUI.class);
        }
    }
}
