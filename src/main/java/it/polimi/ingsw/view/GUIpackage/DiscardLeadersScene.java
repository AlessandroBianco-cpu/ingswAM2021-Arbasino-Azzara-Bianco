package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.Cards.LeaderCard;
import it.polimi.ingsw.networking.message.ChooseLeaderMessage;
import it.polimi.ingsw.observer.NetworkHandlerObservable;
import it.polimi.ingsw.view.GUIpackage.popup.AlertPopup;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Scene that displays the distributed leader cards and asks for discard
 */
public class DiscardLeadersScene extends NetworkHandlerObservable {
    private Pane root;
    private final ImageView readyButton;
    private final List<Integer> indexes;


    public DiscardLeadersScene(List<LeaderCard> leaders) {

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/discardLeadersScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Pane> cardList = new ArrayList<>();
        cardList.add((Pane) root.lookup("#firstCard"));
        cardList.add((Pane) root.lookup("#secondCard"));
        cardList.add((Pane) root.lookup("#thirdCard"));
        cardList.add((Pane) root.lookup("#fourthCard"));


        for (int i= 0; i<leaders.size(); i++)
            addImage(cardList.get(i),"/graphics/leaderCards/" +leaders.get(i).getId()+".png");

        readyButton = (ImageView) root.lookup("#readyButton");
        DropShadow shadow = new DropShadow();
        readyButton.setOnMouseEntered(event -> readyButton.setEffect(shadow));
        readyButton.setOnMouseExited(event -> readyButton.setEffect(null));

        indexes = new ArrayList<>();
        for(Pane pane : cardList)
            pane.setOnMouseClicked(event -> {
                if ( ! (indexes.contains(cardList.indexOf(pane)+1)) ) {
                    addImage(pane,"/graphics/leaderCards/retroLeader.png");
                    indexes.add(cardList.indexOf(pane) + 1);
                }
                else {
                    addImage(pane,"/graphics/leaderCards/" +leaders.get(cardList.indexOf(pane)).getId()+".png");
                    Integer flip = cardList.indexOf(pane) + 1;
                    indexes.remove(flip);
                }
            });

        readyButton.setOnMouseClicked(event -> {
            if (indexes.size() == 2)
                notifyMessage(new ChooseLeaderMessage(indexes));
            else
                new AlertPopup().displayStringMessages("Select 2 cards!");
        });

    }

    /**
     * Adds an ImageView to a Pane
     * @param pane where to add the image
     * @param image path
     */
    private void addImage(Pane pane, String image) {
        ImageView view = new ImageView();
        view.setImage(new Image(image));
        pane.getChildren().clear();
        pane.getChildren().add(view);
        view.fitWidthProperty().bind(pane.widthProperty());
        view.fitHeightProperty().bind(pane.heightProperty());

    }
    public Pane getRoot() {
        return root;
    }

}
