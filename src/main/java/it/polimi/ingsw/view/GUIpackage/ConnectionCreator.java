package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.observer.NetworkHandler;

public interface ConnectionCreator {

    void createSocketNetworkHandler();

    void createLocalNetworkHandler();

    NetworkHandler getNetworkHandler();
}
