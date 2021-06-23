package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.networking.message.ChooseLeaderMessage;
import it.polimi.ingsw.observer.NetworkHandlerObservable;
import it.polimi.ingsw.view.GUIpackage.popup.AlertPopup;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Scene that displays the distributed cards and asks for discard
 */
public class DiscardLeadersScene extends NetworkHandlerObservable {
    private Pane root;
    private final ImageView readyButton;
    private final ArrayList<CheckBox> checkList;
    private final List<Integer> indexes = new ArrayList<>();


    public DiscardLeadersScene(List<LeaderCard> leaders) {

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/discardLeadersScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<ImageView> cardList = new ArrayList<>();
        cardList.add((ImageView) root.lookup("#firstCard"));
        cardList.add((ImageView) root.lookup("#secondCard"));
        cardList.add((ImageView) root.lookup("#thirdCard"));
        cardList.add((ImageView) root.lookup("#fourthCard"));

        checkList = new ArrayList<>();
        checkList.add((CheckBox) root.lookup("#check01"));
        checkList.add((CheckBox) root.lookup("#check02"));
        checkList.add((CheckBox) root.lookup("#check03"));
        checkList.add((CheckBox) root.lookup("#check04"));

        for (int i= 0; i<leaders.size(); i++)
            cardList.get(i).setImage(new Image("/leaderCards/"+leaders.get(i).getId()+".png"));

        readyButton = (ImageView) root.lookup("#readyButton");
        DropShadow shadow = new DropShadow();
        readyButton.setOnMouseEntered(event -> readyButton.setEffect(shadow));
        readyButton.setOnMouseExited(event -> readyButton.setEffect(null));

        readyButton.setOnMouseClicked(event -> {
            int num = 0;
            for(int j=0; j< checkList.size(); j++) {
                if (checkList.get(j).isSelected()) {
                    num++;
                    indexes.add(j+1);
                }
            }

            if (num == 2)
                notifyMessage(new ChooseLeaderMessage(indexes));
            else
                new AlertPopup().displayStringMessages("Select 2 cards!");
        });

    }

    public Pane getRoot() {
        return root;
    }

}
