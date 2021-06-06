package it.polimi.ingsw.view.GUIpackage;


import it.polimi.ingsw.observer.UiObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Scene that asks for ip and port needed to connect to the server
 */
public class ConnectionScene extends UiObservable {
    private Pane root;
    private final ImageView readyButton;

    public ConnectionScene() {
        try {
            root = FXMLLoader.load(getClass().getResource("/connectionScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextField ipText = (TextField) root.lookup("#IPtext");
        TextField portText = (TextField) root.lookup("#PORTtext");
        readyButton = (ImageView) root.lookup("#readyButton");
        ipText.setText(null);
        portText.setText(null);

        DropShadow shadow = new DropShadow();
        readyButton.setOnMouseEntered(event -> readyButton.setEffect(shadow));
        readyButton.setOnMouseExited(event -> readyButton.setEffect(null));

        readyButton.setOnMouseClicked(event -> {
            if (ipText.getText() != null && portText.getText() != null) {
                notifyConnection(ipText.getText(), portText.getText());
            } else
                notifyConnection("127.0.0.1", "12345");
        });
    }

    public Pane getRoot() {
        return root;
    }
}

