package it.polimi.ingsw.view.GUIpackage.popup;


import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Popup used to display alert messages
 */
public class AlertPopup implements Popup{
    private Pane root;
    private Label messageLabel;
    private ImageView closeButton;

    public AlertPopup() {
        try {
            root= FXMLLoader.load(getClass().getResource("/alertPopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        messageLabel = (Label)root.lookup("#messageLabel");
        closeButton = (ImageView) root.lookup("#closeButton");
    }

    @Override
    public void display() {

    }

    @Override
    public void displayStringMessages(String s) {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        messageLabel.setText(s);
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);

        DropShadow shadow = new DropShadow();

        closeButton.setOnMouseEntered(event -> closeButton.setEffect(shadow));
        closeButton.setOnMouseExited(event -> closeButton.setEffect(null));

        closeButton.setOnMouseClicked(event -> stage.close());

        stage.setScene(scene);
        stage.show();
    }

}
