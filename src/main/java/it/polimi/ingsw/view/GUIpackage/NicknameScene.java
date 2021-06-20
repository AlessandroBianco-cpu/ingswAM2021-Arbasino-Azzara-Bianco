package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.networking.message.LoginSettingMessage;
import it.polimi.ingsw.observer.UiObservable;
import it.polimi.ingsw.view.GUIpackage.popup.AlertPopup;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Scene used to get user's nickname
 */
public class NicknameScene extends UiObservable {
    private Pane root;
    private final ImageView joinButton;
    private final ImageView createButton;

    public NicknameScene(String message) {
        try {
            root = FXMLLoader.load(getClass().getResource("/nicknameScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        joinButton = (ImageView) root.lookup("#joinButton");
        createButton = (ImageView) root.lookup("#createButton");
        Text text = (Text) root.lookup("#nicknameText");
        TextField nicknameTextField = (TextField) root.lookup("#nicknameBox");
        nicknameTextField.setText(null);
        text.setText(message);

        DropShadow shadow = new DropShadow();
        joinButton.setOnMouseEntered(event -> joinButton.setEffect(shadow));
        joinButton.setOnMouseExited(event -> joinButton.setEffect(null));
        createButton.setOnMouseEntered(event -> createButton.setEffect(shadow));
        createButton.setOnMouseExited(event -> createButton.setEffect(null));

        joinButton.setOnMouseClicked(event -> {
            String nickname = nicknameTextField.getText();
            if(nickname != null) {
                notifyMessage(new LoginSettingMessage(nickname,true));
            }
            else
                new AlertPopup().displayStringMessages("Please enter a valid nickname.");
        });

        createButton.setOnMouseClicked(event -> {
            String nickname = nicknameTextField.getText();
            if(nickname != null) {
                notifyMessage(new LoginSettingMessage(nickname,false));
            }
            else
                new AlertPopup().displayStringMessages("Please enter a valid nickname.");
        });
    }

    public Pane getRoot() {
        return root;
    }

}