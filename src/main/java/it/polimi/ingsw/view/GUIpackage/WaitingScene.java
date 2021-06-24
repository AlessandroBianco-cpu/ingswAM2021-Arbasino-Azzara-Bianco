package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.observer.NetworkHandlerObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

/**
 * Scene used to wait for other players' choices
 */
public class WaitingScene extends NetworkHandlerObservable {
    private Pane root;

    public WaitingScene(String toPrint) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/waitingScene.fxml")));

        } catch (IOException e){
            e.printStackTrace();
        }

        Text messageText = (Text) root.lookup("#messageText");
        messageText.setText(toPrint);
    }

    public Pane getRoot() {
        return root;
    }

}