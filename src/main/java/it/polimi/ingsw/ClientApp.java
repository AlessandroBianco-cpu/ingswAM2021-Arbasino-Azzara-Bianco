package it.polimi.ingsw;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.view.CLIpackage.CLI;

public class ClientApp {
    public static void main(String[] args) {

        //if(args.length > 0) {
            //if(args[0].equals("-cli")) {
                //start CLI ClientApp
                CLI cli = new CLI();

                NetworkHandler clientNetworkHandler = new NetworkHandler(cli);
                cli.addObserver(clientNetworkHandler);

                new Thread(clientNetworkHandler).start();
                new Thread(cli).start();
            //}


        // else {
            //start GUI ClientApp...............................
        //}

    }
}