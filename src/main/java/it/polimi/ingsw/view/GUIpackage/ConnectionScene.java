package it.polimi.ingsw.view.GUIpackage;


import it.polimi.ingsw.observer.NetworkHandlerObservable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

import static it.polimi.ingsw.utils.StaticUtils.DEFAULT_IP_ADDRESS;
import static it.polimi.ingsw.utils.StaticUtils.DEFAULT_SERVER_PORT;

/**
 * Scene that asks for ip and port needed to connect to the server
 */
public class ConnectionScene extends NetworkHandlerObservable {
    private Pane root;
    private final ImageView connectButton;
    private final ImageView localGameButton;

    public ConnectionScene(ConnectionCreator connectionCreator) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/connectionScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextField ipText = (TextField) root.lookup("#IPtext");
        TextField portText = (TextField) root.lookup("#PORTtext");
        connectButton = (ImageView) root.lookup("#connectButton");
        localGameButton = (ImageView) root.lookup("#localGameButton");
        ipText.setText(null);
        portText.setText(null);

        DropShadow shadow = new DropShadow();
        connectButton.setOnMouseEntered(event -> connectButton.setEffect(shadow));
        connectButton.setOnMouseExited(event -> connectButton.setEffect(null));

        connectButton.setOnMouseClicked(event -> {
            connectionCreator.createSocketNetworkHandler();
            addObserver(connectionCreator.getNetworkHandler());
            if (ipText.getText() != null && portText.getText() != null) {
                notifyConnection(ipText.getText(), portText.getText());
            } else
                notifyConnection(DEFAULT_IP_ADDRESS, DEFAULT_SERVER_PORT);

            WaitingScene waitingScene = new WaitingScene("Server is trying to add you in waiting room");
            Platform.runLater(() -> TransitionHandler.setWaitingScene(waitingScene));
            Platform.runLater(TransitionHandler::toWaitingScene);
        });


        localGameButton.setOnMouseEntered(event -> localGameButton.setEffect(shadow));
        localGameButton.setOnMouseExited(event -> localGameButton.setEffect(null));

        localGameButton.setOnMouseClicked(event -> {
            connectionCreator.createLocalNetworkHandler();
            addObserver(connectionCreator.getNetworkHandler());
            notifyConnection("local" , "local");
            WaitingScene waitingScene = new WaitingScene("Starting a local Single-player game...");
            Platform.runLater(() -> TransitionHandler.setWaitingScene(waitingScene));
            Platform.runLater(TransitionHandler::toWaitingScene);
        });
    }

    public Pane getRoot() {
        return root;
    }
}

