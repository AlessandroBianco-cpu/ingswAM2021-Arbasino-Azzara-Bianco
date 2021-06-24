package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.observer.NetworkHandlerObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

/**
 * Scene that print the winner player and credits
 */
public class WinnerScene extends NetworkHandlerObservable {
    private Pane root;

    public WinnerScene(String winner) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/winnerScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Text winnerText = (Text) root.lookup("#winnerText");
        winnerText.setText(winner);
    }

    public Pane getRoot() {
        return root;
    }
}
