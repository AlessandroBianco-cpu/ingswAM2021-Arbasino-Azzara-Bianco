package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.networking.message.NumOfPlayerMessage;
import it.polimi.ingsw.observer.UiObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;


/**
 * Scene used to get the number of players for the match
 */
public class NumPlayersScene extends UiObservable {
    private Pane root;
    private int number;
    private ImageView readyButton;
    private ChoiceBox choiceBox;

    public NumPlayersScene() {
        try {
            root = FXMLLoader.load(getClass().getResource("/numPlayerScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        readyButton = (ImageView) root.lookup("#readyButton");
        choiceBox = (ChoiceBox) root.lookup("#choiceBox");

        DropShadow shadow = new DropShadow();
        readyButton.setOnMouseEntered(event -> readyButton.setEffect(shadow));
        readyButton.setOnMouseExited(event -> readyButton.setEffect(null));

        choiceBox.setStyle("-fx-font-style: 'Avenir Book'");
        choiceBox.setStyle("-fx-font-size: 14;");
        choiceBox.setValue("Single Player");

        choiceBox.getItems().add("Single Player");
        choiceBox.getItems().add("2 Players");
        choiceBox.getItems().add("3 Players");
        choiceBox.getItems().add("4 Players");

        choiceBox.setStyle("-fx-font: normal 14 'Avenir Book'");

        readyButton.setOnMouseClicked(event -> {
            String str = (String) choiceBox.getValue();
            switch (str) {
                case "2 Players":
                    number = 2;
                    break;
                case "3 Players":
                    number = 3;
                    break;
                case "4 Players":
                    number = 4;
                    break;
                default:
                    number = 1;
            }

            notifyMessage(new NumOfPlayerMessage(number));
        });
    }

    public Pane getRoot() {
        return root;
    }
}
