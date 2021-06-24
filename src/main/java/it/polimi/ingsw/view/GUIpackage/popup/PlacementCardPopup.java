package it.polimi.ingsw.view.GUIpackage.popup;

import it.polimi.ingsw.client.LightModel.ProductionZoneLight;
import it.polimi.ingsw.model.Cards.DevCard;
import it.polimi.ingsw.networking.message.InsertDevCardInDevSlotMessage;
import it.polimi.ingsw.view.GUIpackage.SceneObservable;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Popup that handles the placement of a bought development cards
 */
public class PlacementCardPopup extends SceneObservable implements Popup {

    private Pane root;
    private final List<Pane> slot1;
    private final List<Pane> slot2;
    private final List<Pane> slot3;

    public PlacementCardPopup(DevCard cardToPlace, ProductionZoneLight zone) {

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/placementCardPopup.fxml")));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        Pane boughtCard = (Pane) root.lookup("#boughtCard");

        slot1 = new ArrayList<>();
        slot2 = new ArrayList<>();
        slot3 = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            int j = i+1;
            slot1.add((Pane) root.lookup("#slot1card"+j));
            slot2.add((Pane) root.lookup("#slot2card"+j));
            slot3.add((Pane) root.lookup("#slot3card"+j));
        }


        for(int i = 0; i<zone.getFirstSlot().size();i++)
            addImage(slot1.get(i), "/graphics/devCards/" +zone.getFirstSlot().get(i).getId()+".png");

        for(int i = 0; i<zone.getSecondSlot().size();i++)
            addImage(slot2.get(i), "/graphics/devCards/" +zone.getSecondSlot().get(i).getId()+".png");

        for(int i = 0; i<zone.getThirdSlot().size();i++)
            addImage(slot3.get(i), "/graphics/devCards/" +zone.getThirdSlot().get(i).getId()+".png");

        addImage(boughtCard, "/graphics/devCards/" +cardToPlace.getId()+".png");
    }

    @Override
    public void displayStringMessages(String s) {

    }

    @Override
    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);

        for(Pane pane : slot1) {
            pane.setOnMouseClicked(event -> {
                notifyNewMessageFromClient(new InsertDevCardInDevSlotMessage(1));
                stage.close();
            });
        }

        for(Pane pane : slot2) {
            pane.setOnMouseClicked(event -> {
                notifyNewMessageFromClient(new InsertDevCardInDevSlotMessage(2));
                stage.close();
            });
        }

        for(Pane pane : slot3) {
            pane.setOnMouseClicked(event -> {
                notifyNewMessageFromClient(new InsertDevCardInDevSlotMessage(3));
                stage.close();
            });
        }

        stage.setOnCloseRequest(Event::consume);
        stage.setScene(scene);
        stage.show();
    }

}
