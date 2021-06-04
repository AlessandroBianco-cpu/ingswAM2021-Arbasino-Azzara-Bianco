package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.networking.message.SettingNicknameMessage;
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
    private ImageView loginButton;

    public NicknameScene(String message) {
        try {
            root = FXMLLoader.load(getClass().getResource("/nicknameScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        loginButton = (ImageView) root.lookup("#loginButton");
        Text text = (Text) root.lookup("#nicknameText");
        TextField nicknameTextField = (TextField) root.lookup("#nicknameBox");
        nicknameTextField.setText(null);
        text.setText(message);

        DropShadow shadow = new DropShadow();
        loginButton.setOnMouseEntered(event -> loginButton.setEffect(shadow));
        loginButton.setOnMouseExited(event -> loginButton.setEffect(null));

        loginButton.setOnMouseClicked(event -> {
            String nickname = nicknameTextField.getText();

            if(nickname != null) {
                notifyMessage(new SettingNicknameMessage(nickname));
            }
            else
                new AlertPopup().displayStringMessages("Please enter a valid nickname.");
        });
    }

    public Pane getRoot() {
        return root;
    }

}
