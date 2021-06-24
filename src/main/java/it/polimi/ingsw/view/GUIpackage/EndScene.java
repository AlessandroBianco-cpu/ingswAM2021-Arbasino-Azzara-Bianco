package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.observer.NetworkHandlerObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

/**
 * This scene is used after a closing server-side
 */
public class EndScene extends NetworkHandlerObservable {

    private Pane root;

    public EndScene(String message) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/endScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Text textBox = (Text) root.lookup("#textBox");
        textBox.setText(message);
    }

    public Pane getRoot() { return root; }
}
