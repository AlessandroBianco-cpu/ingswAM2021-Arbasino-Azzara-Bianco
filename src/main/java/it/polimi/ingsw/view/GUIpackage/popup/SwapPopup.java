package it.polimi.ingsw.view.GUIpackage.popup;

import it.polimi.ingsw.client.LightModel.LeaderCardsInHandLight;
import it.polimi.ingsw.client.LightModel.WarehouseLight;
import it.polimi.ingsw.networking.message.MoveFromExtraDepotMessage;
import it.polimi.ingsw.networking.message.MoveToExtraDepotMessage;
import it.polimi.ingsw.networking.message.WarehouseSwapMessage;
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

import static it.polimi.ingsw.model.ResourceType.NOTHING;

/**
 * Popup that manages all the swapping actions
 */
public class SwapPopup extends SceneObservable implements Popup {

    private Pane root;
    private ImageView swapButton;
    private List<CheckBox> checkList;
    private ChoiceBox choiceBox;
    private ChoiceBox quantityBox;
    private List<Pane> depots;
    private List<Pane> extraLeaders;
    private List<Pane> extraDepots;
    private boolean noExtra = true;
    private int chosenExtra = -1;
    private boolean isClosable;

    public SwapPopup(WarehouseLight warehouse, LeaderCardsInHandLight leaders, boolean isClosable) {
        this.isClosable = isClosable;
        try {
            root= FXMLLoader.load(getClass().getResource("/swapPopup.fxml"));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        quantityBox = ((ChoiceBox) root.lookup("#quantityBox"));
        swapButton = (ImageView) root.lookup("#swapButton");
        choiceBox = (ChoiceBox) root.lookup("#choiceBox");
        choiceBox.setValue("warehouse->extra");
        choiceBox.getItems().add("warehouse->extra");
        choiceBox.getItems().add("extra->warehouse");
        quantityBox.setValue("1");
        quantityBox.getItems().add("1");
        quantityBox.getItems().add("2");

        depots = new ArrayList<>();
        for(int i=0; i<6; i++) {
            int j = i+1;
            depots.add((Pane) root.lookup("#"+j));
        }

        for(int i =0; i < warehouse.getDepots().length; i++)
            if(!(warehouse.getDepots()[i].getResource().equals(NOTHING)))
                addImage(depots.get(i),warehouse.getDepots()[i].toImage());

        extraLeaders = new ArrayList<>();
        extraLeaders.add((Pane) root.lookup("#leader1"));
        extraLeaders.add((Pane) root.lookup("#leader2"));
        extraDepots = new ArrayList<>();
        extraDepots.add((Pane) root.lookup("#firstExtra1"));
        extraDepots.add((Pane) root.lookup("#firstExtra2"));
        extraDepots.add((Pane) root.lookup("#secondExtra1"));
        extraDepots.add((Pane) root.lookup("#secondExtra2"));

        checkList = new ArrayList<>();
        checkList.add((CheckBox) root.lookup("#depot1"));
        checkList.add((CheckBox) root.lookup("#depot2"));
        checkList.add((CheckBox) root.lookup("#depot3"));

        for(int i = 0; i < leaders.getActiveCards().size();i++){
            if(leaders.getActiveCards().get(i).isExtraDepotCard()) {
                addImage(extraLeaders.get(i),"/leaderCards/"+leaders.getActiveCards().get(i).getId() +".png"); //leader cards images
                noExtra = false;
                for (int j = 0; j < warehouse.getExtraDepotsQuantity()[i]; j++) //leader cards resources on cards
                    addImage(extraDepots.get(2 * i + j), warehouse.getExtraDepotsTypes()[i].toImage());
            }
        }

        choiceBox.setStyle("-fx-font: normal 14 'Avenir Book'");
        quantityBox.setStyle("-fx-font: normal 14 'Avenir Book'");

        if(noExtra) {
            choiceBox.setDisable(true);
            quantityBox.setDisable(true);
        }

    }

    @Override
    public void display() {
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);

        DropShadow shadow = new DropShadow();

        swapButton.setOnMouseEntered(event -> swapButton.setEffect(shadow));
        swapButton.setOnMouseExited(event -> swapButton.setEffect(null));

        for(Pane pane : extraLeaders)
            pane.setOnMouseClicked(event -> setChosenExtra(pane));

        swapButton.setOnMouseClicked(event -> {
            if(parseCheckingList().size() == 2) {
                notifyNewMessageFromClient(new WarehouseSwapMessage(parseCheckingList().get(0), parseCheckingList().get(1)));
                stage.close();
            }
            else if (parseCheckingList().size() == 1) {
                if (!noExtra) {
                    String str = (String) choiceBox.getValue();
                    switch (str) {
                        case "warehouse->extra":
                            notifyNewMessageFromClient(new MoveToExtraDepotMessage(parseCheckingList().get(0), chosenExtra, getChosenQuantity()));
                            break;
                        case "extra->warehouse":
                            notifyNewMessageFromClient(new MoveFromExtraDepotMessage(chosenExtra, parseCheckingList().get(0), getChosenQuantity()));
                            break;
                    }
                    stage.close();
                }
                else
                    new AlertPopup().displayStringMessages("You haven't got extraDepot cards!!");

            }
            else
                new AlertPopup().displayStringMessages("You have selected too much depots!");
        });

        stage.setOnCloseRequest(event -> {
            if(isClosable)
                stage.close();
            else
                event.consume();
        });

        stage.setScene(scene);
        stage.show();
    }


    private int getChosenQuantity() {
        String str = (String) quantityBox.getValue();
        switch (str) {
            case "1":
                return 1;
            case "2":
                return 2;
            default:
                return 0;
        }
    }

    private List<Integer> parseCheckingList() {
        List<Integer> checkSelected = new ArrayList<>();
        for(int j=0; j< checkList.size(); j++) {
            if (checkList.get(j).isSelected()) {
                checkSelected.add(j+1);
            }
        }
        return checkSelected;
    }


    private void setChosenExtra(Pane pane) {
        switch (pane.getId()) {
            case "leader1":
                chosenExtra = 1;
                break;
            case "leader2":
                chosenExtra = 2;
                break;
        }
    }

    @Override
    public void displayStringMessages(String s) {

    }
}
