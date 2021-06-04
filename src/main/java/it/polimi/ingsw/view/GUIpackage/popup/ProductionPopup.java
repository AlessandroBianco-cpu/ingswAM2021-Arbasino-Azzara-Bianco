package it.polimi.ingsw.view.GUIpackage.popup;

import it.polimi.ingsw.client.LightModel.LeaderCardsInHandLight;
import it.polimi.ingsw.client.LightModel.ProductionZoneLight;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.networking.message.ActivateProductionMessage;
import it.polimi.ingsw.view.GUIpackage.SceneObservable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.ResourceType.*;

public class ProductionPopup extends SceneObservable implements Popup {

    private Pane root;
    private List<Pane> slot1;
    private List<Pane> slot2;
    private ImageView startButton;
    private List<ChoiceBox> basePower;
    private List<Pane> slot3;
    private List<Pane> leaderCards;
    private List<CheckBox> devSlots;
    private boolean noExtraDev = true;

    public ProductionPopup(ProductionZoneLight zone, LeaderCardsInHandLight cards) {

        try {
            root = FXMLLoader.load(getClass().getResource("/productionPopup.fxml"));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        slot1 = new ArrayList<>();
        slot2 = new ArrayList<>();
        slot3 = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            int j = i+1;
            slot1.add((Pane) root.lookup("#slot1card"+j));
            slot2.add((Pane) root.lookup("#slot2card"+j));
            slot3.add((Pane) root.lookup("#slot3card"+j));
        }

        startButton = (ImageView) root.lookup("#startButton");
        devSlots = new ArrayList<>();
        for(int i = 0; i< 4; i++)
            devSlots.add((CheckBox) root.lookup("#devSlot"+i));

        devSlots.add((CheckBox) root.lookup("#checkLeader1"));
        devSlots.add((CheckBox) root.lookup("#checkLeader2"));
        devSlots.get(4).setDisable(true);
        devSlots.get(5).setDisable(true);

        leaderCards = new ArrayList<>();
        leaderCards.add((Pane) root.lookup("#leaderCard1"));
        leaderCards.add((Pane) root.lookup("#leaderCard2"));

        for(int i= 0; i<cards.getActiveCards().size(); i++) {
            if (cards.getActiveCards().get(i).isExtraDevCard()) {
                addImage(leaderCards.get(i), "/leaderCards/" + cards.getActiveCards().get(i).getId() + ".png");
                devSlots.get(i+4).setDisable(false);
            }
        }

        basePower = new ArrayList<>();
        basePower.add((ChoiceBox) root.lookup("#baseInput1"));
        basePower.add((ChoiceBox) root.lookup("#baseInput2"));
        basePower.add((ChoiceBox) root.lookup("#baseOutput"));
        basePower.add((ChoiceBox) root.lookup("#leaderOut1"));
        basePower.add((ChoiceBox) root.lookup("#leaderOut2"));

        basePower.get(0).setValue("input#1");
        basePower.get(1).setValue("input#2");
        basePower.get(2).setValue("output");
        basePower.get(3).setValue("output");
        basePower.get(4).setValue("output");


        for(ChoiceBox choiceBox : basePower) {
            choiceBox.getItems().add("COIN");
            choiceBox.getItems().add("SHIELD");
            choiceBox.getItems().add("STONE");
            choiceBox.getItems().add("SERVANT");
            choiceBox.setDisable(true);
        }

        for(ChoiceBox choiceBox : basePower) {
            choiceBox.setStyle("-fx-font: normal 14 'Avenir Book'");
        }

        for(int i = 0; i<zone.getFirstSlot().size();i++)
            addImage(slot1.get(i),"/devCards/"+zone.getFirstSlot().get(i).getId()+".png");

        for(int i = 0; i<zone.getSecondSlot().size();i++)
            addImage(slot2.get(i),"/devCards/"+zone.getSecondSlot().get(i).getId()+".png");

        for(int i = 0; i<zone.getThirdSlot().size();i++)
            addImage(slot3.get(i),"/devCards/"+zone.getThirdSlot().get(i).getId()+".png");
    }

    @Override
    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);

        DropShadow shadow = new DropShadow();
        startButton.setOnMouseEntered(event -> startButton.setEffect(shadow));
        startButton.setOnMouseExited(event -> startButton.setEffect(null));

        devSlots.get(0).setOnMouseClicked(event -> {
            if (basePower.get(0).isDisable()) {
                basePower.get(0).setDisable(false);
                basePower.get(1).setDisable(false);
                basePower.get(2).setDisable(false);
            }
            else {
                basePower.get(0).setDisable(true);
                basePower.get(1).setDisable(true);
                basePower.get(2).setDisable(true);
            }
        });

        devSlots.get(4).setOnMouseClicked(event -> {
            if (basePower.get(3).isDisable())
                basePower.get(3).setDisable(false);
            else
                basePower.get(3).setDisable(true);
        });

        devSlots.get(5).setOnMouseClicked(event ->{
            if (basePower.get(4).isDisable())
                basePower.get(4).setDisable(false);
            else
                basePower.get(4).setDisable(true);
        });

        startButton.setOnMouseClicked(event -> {
            if (parseCheckingList().size() == 0)
                new AlertPopup().displayStringMessages("You have to choose at least one slot!");
            else {
                notifyNewMessageFromClient(new ActivateProductionMessage (parseCheckingList(),parseTextField(basePower.get(0)),parseTextField(basePower.get(1)),parseTextField(basePower.get(2)), analyzeLeaderChoose()));
                stage.close();
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void displayStringMessages(String s) {

    }

    private List<ResourceType> analyzeLeaderChoose() {
        List<ResourceType> list = new ArrayList<>();
        if (devSlots.get(4).isSelected())
            list.add(parseTextField(basePower.get(3)));
        if (devSlots.get(5).isSelected())
            list.add(parseTextField(basePower.get(4)));
        return list;
    }

    private List<Integer> parseCheckingList() {
        List<Integer> list = new ArrayList<>();
        for(int j=0; j< devSlots.size(); j++) {
            if (devSlots.get(j).isSelected())
                list.add(j);
        }
      return list;
    }

    private ResourceType parseTextField (ChoiceBox choiceBox) {
        if( !(choiceBox.isDisable())) {
            String str = (String) choiceBox.getValue();
            switch (str) {
                case "SHIELD":
                    return SHIELD;
                case "SERVANT":
                    return SERVANT;
                case "COIN":
                    return COIN;
                case "STONE":
                    return STONE;
            }
        }
        return NOTHING;
    }


}
