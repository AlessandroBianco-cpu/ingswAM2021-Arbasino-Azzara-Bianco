package it.polimi.ingsw;

import it.polimi.ingsw.networking.socketGame.Server;
import it.polimi.ingsw.view.CLIpackage.CLI;
import it.polimi.ingsw.view.GUIpackage.GUI;
import javafx.application.Application;

import static it.polimi.ingsw.utils.StaticUtils.DEFAULT_SERVER_PORT;

/**
 * Main class for the application
 */
public class MasterOfRenaissance {

    public static void main(String[] args) {

        if (args.length > 0) {
            if (args[0].equals("-cli")) {
                //start CLI ClientApp
                CLI cli = new CLI();
                cli.run();
            } else if (args[0].equals("-server")) {
                int port;
                if(args[1].equals("-port"))
                    port = Integer.parseInt(args[2]);
                else
                    port = Integer.parseInt(DEFAULT_SERVER_PORT);

                Server server;
                server = new Server(port);
                server.run();
            }
        } else {
            //start GUI ClientApp
            Application.launch(GUI.class);
        }
    }
}
