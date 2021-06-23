package it.polimi.ingsw.view.GUIpackage.popup;

import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.networking.message.ActivateLeaderMessage;
import it.polimi.ingsw.networking.message.DiscardLeaderMessage;
import it.polimi.ingsw.view.GUIpackage.SceneObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * Popup that handles all the possibles actions for the relative leader card
 */
public class LeaderPopup extends SceneObservable implements Popup{
    private Pane root;
    private final ImageView activateButton;
    private final ImageView discardButton;
    private final int indexInHand;

    public LeaderPopup(LeaderCard leaderCard,int indexInHand) {
        this.indexInHand = indexInHand+1;
        try {
            root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/leaderActionPopup.fxml")));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        Pane leader = (Pane) root.lookup("#leader");
        activateButton = (ImageView) root.lookup("#activateButton");
        discardButton = (ImageView) root.lookup("#discardButton");

        addImage(leader,"/leaderCards/"+leaderCard.getId()+".png");
    }

    @Override
    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);

        DropShadow shadow = new DropShadow();

        activateButton.setOnMouseEntered(event -> activateButton.setEffect(shadow));
        activateButton.setOnMouseExited(event -> activateButton.setEffect(null));

        discardButton.setOnMouseEntered(event -> discardButton.setEffect(shadow));
        discardButton.setOnMouseExited(event -> discardButton.setEffect(null));

        activateButton.setOnMouseClicked(event -> {
            notifyNewMessageFromClient(new ActivateLeaderMessage(indexInHand));
            stage.close();
        });

        discardButton.setOnMouseClicked(event -> {
            notifyNewMessageFromClient(new DiscardLeaderMessage(indexInHand));
            stage.close();
        });

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void displayStringMessages(String s) {
        new AlertPopup().displayStringMessages(s);
    }
}
