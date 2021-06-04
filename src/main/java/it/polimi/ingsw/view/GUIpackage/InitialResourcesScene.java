package it.polimi.ingsw.view.GUIpackage;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.ChooseResourcesMessage;
import it.polimi.ingsw.observer.UiObservable;
import it.polimi.ingsw.view.GUIpackage.popup.AlertPopup;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.ResourceType.*;

public class InitialResourcesScene extends UiObservable {
    private Pane root;

    private ImageView readyButton;
    private List<ImageView> images;
    private Label messageLabel;
    private List<Pane> depots;
    private ResourceType toAdd = NOTHING;
    private int justAdded = 0;

    public InitialResourcesScene(int numRes) {
        ChooseResourcesMessage resMessage = new ChooseResourcesMessage(numRes);

        try {
            root = FXMLLoader.load(getClass().getResource("/initialResourcesScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        images = new ArrayList<>();
        depots = new ArrayList<>();

        messageLabel = (Label) root.lookup("#messageLabel");
        messageLabel.setText("Choose "+numRes+" resources to add in your warehouse");
        readyButton = (ImageView) root.lookup("#readyButton");
        images.add( (ImageView) root.lookup("#coin") );
        images.add( (ImageView) root.lookup("#servant") );
        images.add( (ImageView) root.lookup("#stone") );
        images.add( (ImageView) root.lookup("#shield") );

        DropShadow shadow = new DropShadow();
        readyButton.setOnMouseEntered(event -> readyButton.setEffect(shadow));
        readyButton.setOnMouseExited(event -> readyButton.setEffect(null));

        for(int i = 0; i<6; i++) {
            int j = i + 1;
            depots.add((Pane) root.lookup("#" + j));
        }

        for(ImageView im : images) {
            im.setOnMouseClicked(event -> {
                toAdd = parseResourceType(im.getId());
            });
        }

        for (Pane pane : depots)
            pane.setOnMouseClicked(event -> {
                if(justAdded == numRes)
                    new AlertPopup().displayStringMessages("You have already added the right number of resources");
                else if ( !(toAdd.equals(NOTHING)) ){
                    addImage(pane,toImage(toAdd));
                    resMessage.addResource(toAdd,shelfToAddByPaneID(pane.getId()));
                    resetAndIncrease();
                }
            });

        readyButton.setOnMouseClicked(event -> {
            if (justAdded == numRes)
                notifyMessage(resMessage);
            else
                new AlertPopup().displayStringMessages("You have to choose "+numRes+ " resources to add in your warehouse");
        });
    }

    private String toImage (ResourceType res) {
        switch (res) {
            case SERVANT:
                return "/punchBoard/servant.png";
            case COIN:
                return "/punchBoard/coin.png";
            case STONE:
                return "/punchBoard/stone.png";
            case SHIELD:
                return "/punchBoard/shield.png";
            default:
                return  null;
        }
    }

    /**
     * This method is a parser for the resource type which user choose
     * @param s is the imageView id code
     * @return resource type
     */
    private ResourceType parseResourceType (String s) {
        switch (s) {
            case "coin" :
                return COIN;
            case "shield" :
                return SHIELD;
            case "servant" :
                return SERVANT;
            case "stone" :
                return STONE;
            default:
                return null;
        }
    }

    private void resetAndIncrease(){
        justAdded++;
        toAdd = NOTHING;
    }

    private int shelfToAddByPaneID(String id) {
        int depot = Integer.parseInt(id);

        if(depot == 1)
            return 1;
        else if(depot < 4)
            return 2;
        else
            return 3;
    }

    /**
     * Adds an ImageView to a Pane
     * @param pane where to add the image
     * @param image path
     * @return the new image
     */
    private ImageView addImage(Pane pane, String image) {
        ImageView view = new ImageView();
        view.setImage(new Image(image));

        pane.getChildren().add(view);
        view.fitWidthProperty().bind(pane.widthProperty());
        view.fitHeightProperty().bind(pane.heightProperty());

        return view;
    }

    public Pane getRoot() {
        return root;
    }

}
