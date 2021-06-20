package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.observer.UiObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * This scene is used after a brutal closing server-side
 */
public class EndScene extends UiObservable {

    private Pane root;
    private final Text textBox;

    public EndScene(String message) {
        try {
            root = FXMLLoader.load(getClass().getResource("/endScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        textBox = (Text) root.lookup("#textBox");
        textBox.setText(message);
    }

    public Pane getRoot() { return root; }
}
