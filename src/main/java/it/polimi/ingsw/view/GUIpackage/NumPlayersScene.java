package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.networking.message.NumOfPlayerMessage;
import it.polimi.ingsw.observer.NetworkHandlerObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Objects;

/**
 * Scene used to get the number of players for the match
 */
@SuppressWarnings("unchecked")
public class NumPlayersScene extends NetworkHandlerObservable {
    private Pane root;
    private int number;
    private final ImageView readyButton;
    private final ChoiceBox<String> choiceBox;

    public NumPlayersScene() {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/numPlayerScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        readyButton = (ImageView) root.lookup("#readyButton");
        choiceBox = (ChoiceBox<String>) root.lookup("#choiceBox");

        DropShadow shadow = new DropShadow();
        readyButton.setOnMouseEntered(event -> readyButton.setEffect(shadow));
        readyButton.setOnMouseExited(event -> readyButton.setEffect(null));

        choiceBox.setStyle("-fx-font: normal 14 'Avenir Book'");

        choiceBox.setValue("Single Player");
        choiceBox.getItems().add("Single Player");
        choiceBox.getItems().add("2 Players");
        choiceBox.getItems().add("3 Players");
        choiceBox.getItems().add("4 Players");

        choiceBox.setStyle("-fx-font: normal 14 'Avenir Book'");

        readyButton.setOnMouseClicked(event -> {
            String str = choiceBox.getValue();
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
