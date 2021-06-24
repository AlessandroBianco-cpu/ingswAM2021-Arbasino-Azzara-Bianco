package it.polimi.ingsw.view.GUIpackage.popup;

import it.polimi.ingsw.client.LightModel.PlayerLight;
import it.polimi.ingsw.model.Cards.LeaderCard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static it.polimi.ingsw.model.ResourceType.NOTHING;

/**
 * Popup that shows all the information for the selected opponent
 */
public class ShowOpponentPopup implements Popup {

    private Pane root;
    private final String title;

    public ShowOpponentPopup(PlayerLight opponent) {
        title = opponent.getNickname()+"'s PlayerBoard";

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/showOpponentPopup.fxml")));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        List<Label> strongboxLabel = new ArrayList<>();
        strongboxLabel.add((Label) root.lookup("#qtaCoin"));
        strongboxLabel.add((Label) root.lookup("#qtaServant"));
        strongboxLabel.add((Label) root.lookup("#qtaShield"));
        strongboxLabel.add((Label) root.lookup("#qtaStone"));
        Pane inkwell = (Pane) root.lookup("#inkwell");

        List<Pane> depots = new ArrayList<>();
        depots.add((Pane) root.lookup("#firstDepot"));
        depots.add((Pane) root.lookup("#secondDepot1"));
        depots.add((Pane) root.lookup("#secondDepot2"));
        depots.add((Pane) root.lookup("#thirdDepot1"));
        depots.add((Pane) root.lookup("#thirdDepot2"));
        depots.add((Pane) root.lookup("#thirdDepot3"));

        List<Pane> slot1 = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            int j = i+1;
            slot1.add((Pane) root.lookup("#slot1card"+j));
        }

        List<Pane> slot2 = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            int j = i+1;
            slot2.add((Pane) root.lookup("#slot2card"+j));
        }

        List<Pane> slot3 = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            int j = i+1;
            slot3.add((Pane) root.lookup("#slot3card"+j));
        }

        List<Pane> popeSpaces = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            int j = i+1;
            popeSpaces.add((Pane) root.lookup("#popeSpace"+j));
        }

        List<Pane> extraDepots = new ArrayList<>();
        extraDepots.add((Pane) root.lookup("#firstExtra1"));
        extraDepots.add((Pane) root.lookup("#firstExtra2"));
        extraDepots.add((Pane) root.lookup("#secondExtra1"));
        extraDepots.add((Pane) root.lookup("#secondExtra2"));

        List<Pane> leaders = new ArrayList<>();
        leaders.add((Pane) root.lookup("#leaderCard1"));
        leaders.add((Pane) root.lookup("#leaderCard2"));

        List<Pane> faithTrack = new ArrayList<>();
        for(int i = 0; i<25; i++)
            faithTrack.add((Pane) root.lookup("#slot"+i));

        addImage(faithTrack.get(opponent.getFaithTrack().getPosition()), "/graphics/punchBoard/blackCross.png");

        if(opponent.hasInkwell())
            addImage(inkwell, "/graphics/punchBoard/inkwell.png");



        int extraDepotIndex = 0;
        List<LeaderCard> activeCards = opponent.getLeaderCardsInHand().getActiveCards();
        for (int i = 0; i < opponent.getLeaderCardsInHand().getSizeOfTotalLeadersInHand(); i++) {
            if(i< activeCards.size()){
                addImage(leaders.get(i), "/graphics/leaderCards/" + activeCards.get(i).getId() + ".png");
                if (activeCards.get(i).isExtraDepotCard()) {
                    for (int numOfResources = 0; numOfResources < opponent.getWarehouse().getExtraDepotsQuantity()[extraDepotIndex]; numOfResources++) {
                        addImage(extraDepots.get(2 * i + numOfResources), opponent.getWarehouse().getExtraDepotsTypes()[extraDepotIndex].toImage());
                    }
                    extraDepotIndex++;
                }
            }else
                addImage(leaders.get(i), "/graphics/leaderCards/retroLeader.png");
        }

        for(int i=0; i<strongboxLabel.size();i++)
            setLabelIntText(strongboxLabel.get(i),opponent.getStrongbox().getResources()[i]);

        for(int i =0; i< opponent.getWarehouse().getDepots().length; i++)
            if(!(opponent.getWarehouse().getDepots()[i].getResource().equals(NOTHING)))
                addImage(depots.get(i),opponent.getWarehouse().getDepots()[i].toImage());

        for(int i = 0; i<opponent.getProductionZone().getFirstSlot().size(); i++) {
            addImage(slot1.get(i), "/graphics/devCards/" +opponent.getProductionZone().getFirstSlot().get(i).getId()+".png");
        }

        for(int i = 0; i<opponent.getProductionZone().getSecondSlot().size(); i++) {
            addImage(slot2.get(i), "/graphics/devCards/" +opponent.getProductionZone().getSecondSlot().get(i).getId()+".png");
        }

        for(int i = 0; i<opponent.getProductionZone().getThirdSlot().size(); i++) {
            addImage(slot3.get(i), "/graphics/devCards/" +opponent.getProductionZone().getThirdSlot().get(i).getId()+".png");
        }

        if(opponent.getFaithTrack().isFirstPopeFavorAchieved())
            addImage(popeSpaces.get(0), "/graphics/punchBoard/yellow_front_tile.png");
        else
            addImage(popeSpaces.get(0), "/graphics/punchBoard/yellow_back_tile.png");

        if(opponent.getFaithTrack().isSecondPopeFavorAchieved())
            addImage(popeSpaces.get(1), "/graphics/punchBoard/orange_front_tile.png");
        else
            addImage(popeSpaces.get(1), "/graphics/punchBoard/orange_back_tile.png");

        if(opponent.getFaithTrack().isThirdPopeFavorAchieved())
            addImage(popeSpaces.get(2), "/graphics/punchBoard/red_front_tile.png");
        else
            addImage(popeSpaces.get(2), "/graphics/punchBoard/red_back_tile.png");

    }


    @Override
    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void displayStringMessages(String s) {

    }

    /**
     * Adds an ImageView to a Pane
     * @param pane where to add the image
     * @param image path
     */
    private void addImage(Pane pane, String image) {
        ImageView view = new ImageView();
        view.setImage(new Image(image));

        pane.getChildren().add(view);
        view.fitWidthProperty().bind(pane.widthProperty());
        view.fitHeightProperty().bind(pane.heightProperty());

    }

    private void setLabelIntText(Label label, int toSet) {
        if(toSet > 0)
            label.setText(""+toSet);
        else
            label.setText(""+0);
    }

}
