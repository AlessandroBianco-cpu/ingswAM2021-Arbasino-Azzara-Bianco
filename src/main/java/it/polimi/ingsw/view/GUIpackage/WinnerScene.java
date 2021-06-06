package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.observer.UiObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
/**
 * Scene that displays the winner of the match and credits
 */
public class WinnerScene extends UiObservable {
    private Pane root;
    private Text winnerText;

    public WinnerScene(String winner) {
        try {
            root = FXMLLoader.load(getClass().getResource("/winnerScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        winnerText = (Text) root.lookup("#winnerText");
        winnerText.setText(winner);
    }

    public Pane getRoot() {
        return root;
    }
}
